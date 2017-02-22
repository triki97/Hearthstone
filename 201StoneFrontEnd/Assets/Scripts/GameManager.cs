using UnityEngine;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using SimpleJSON;

public class GameManager : MonoBehaviour {
	public static GameManager instance;

	public int playerNumber;
	public int deckNumber;

	public GameBoard gameBoard;
	public ManaCapacity manaCapacity;
	public CardHand cardHand;

	[HideInInspector]
	public SocketConnector connector;
	public SocketConnector.ResponseDelegate dataCallback;


	bool gameDone = false;
	public EndGamePanel endGamePanel;

	void Awake() {
		if(instance != null) {
			Debug.LogError("More than one GameManager in Scene");
		}
		instance = this;
	}

	void Start () {
		playerNumber = PlayerPrefs.GetInt("player");
		deckNumber = PlayerPrefs.GetInt("deck");

		connector = SocketConnector.instance;
		dataCallback = ParseGameResponse;
		ReceiveGameResponse();
	}

	// Update is called once per frame
	void Update () {

	}

	public void EndTurn() {
		SendString("EndTurn");
	}

	public void SendString(string message) {
		Debug.Log("SENDING TO SERVER: " + message);
		connector.socket.SendString(message);
	}

	public void ReceiveGameResponse() {
		StartCoroutine(connector.GetResponse(dataCallback));
	}

	void ParseGameResponse(JSONNode response) {
		// Edge Case: User disconnects
		if(!gameDone && response["success"] != null && !response["success"].AsBool){
			StartCoroutine(handleWin(0));
			return;
		}

		string playerKey = "player" + playerNumber;
		string opponentKey = "player" + (playerNumber == 1 ? 2 : 1);

		// Parse Cards
		cardHand.cardData = ParseCards(response[playerKey]["cardsInHand"].AsArray);;
		cardHand.UpdateCards();

		// Parse Mana Capacity
		manaCapacity.CurrentMana = response[playerKey]["manaCapacity"].AsInt;
		manaCapacity.UsedMana = manaCapacity.CurrentMana - response[playerKey]["availableMana"].AsInt; 

		// Parse Minion Board (Player Side)
		gameBoard.UpdateBoard(0, ParseMinions(response[playerKey+"minions"].AsArray));

		// Parse Minion Board (Opponent Side)
		gameBoard.UpdateBoard(1, ParseMinions(response[opponentKey+"minions"].AsArray));

		// Make all Opponent Minions green glowable
		foreach(Minion minion in gameBoard.Minions[1]){
			minion.hasAttacked = false;
		}

		// Make Player Minions green glowable
		gameBoard.SetInteractable(0, true);

		// Change the Interactability of the Board based on Player's Turn
		bool interactable = response["currentPlayer"].AsInt == playerNumber;
		GameObject uiGroup = GameObject.FindGameObjectWithTag("Interactable");
		uiGroup.GetComponent<CanvasGroup>().interactable = interactable;
		uiGroup.GetComponent<CanvasGroup>().blocksRaycasts = interactable;

		// Update Player Health
		gameBoard.heroes[0].health = response[playerKey]["health"].AsInt;
		gameBoard.heroes[1].health = response[opponentKey]["health"].AsInt;

		// Check if Players die
		if(gameBoard.heroes[0].health <= 0 && gameBoard.heroes[1].health <= 0) {
			StartCoroutine(handleDraw());
		}
		else if(gameBoard.heroes[0].health <= 0) {
			StartCoroutine(handleWin(1));
		}
		else if(gameBoard.heroes[1].health <= 0) {
			StartCoroutine(handleWin(0));
		}

		// Update Hero Sprite
		String playerSprite = response[playerKey]["deckNumber"].AsInt == 0 ? "Student" : "Professor";
		String opponentSprite = response[opponentKey]["deckNumber"].AsInt == 0 ? "Student" : "Professor";
		gameBoard.heroes[0].sprite = GetSpriteFromLocalPath(playerSprite);
		gameBoard.heroes[1].sprite = GetSpriteFromLocalPath(opponentSprite);

		// Wait for the next response
		ReceiveGameResponse();
	}

	public IEnumerator handleWin(int winningPlayer) {
		int losingPlayer = winningPlayer ^ 1;

		GameObject uiGroup = GameObject.FindGameObjectWithTag("Interactable");
		uiGroup.GetComponent<CanvasGroup>().interactable = false;
		uiGroup.GetComponent<CanvasGroup>().blocksRaycasts = false;

		GameObject.FindGameObjectWithTag("MainCamera").GetComponent<AudioSource>().clip = null;

		gameDone = true;

		StartCoroutine(gameBoard.handleDeath(losingPlayer));
		yield return new WaitForSeconds(5.0f);

		endGamePanel.ShowEnd(winningPlayer);
	}

	public IEnumerator handleDraw() {
		GameObject uiGroup = GameObject.FindGameObjectWithTag("Interactable");
		uiGroup.GetComponent<CanvasGroup>().interactable = false;
		uiGroup.GetComponent<CanvasGroup>().blocksRaycasts = false;

		GameObject.FindGameObjectWithTag("MainCamera").GetComponent<AudioSource>().clip = null;

		gameDone = true;

		StartCoroutine(gameBoard.handleDeath(0));
		StartCoroutine(gameBoard.handleDeath(1));
		yield return new WaitForSeconds(5.0f);

		endGamePanel.ShowEnd(2);
	}

	public List<Card> ParseCards(JSONArray cards) {
		List<Card> toReturn = new List<Card>();

		foreach(JSONNode jsonCard in cards) {
			Card card = gameObject.AddComponent<Card>();
			card.manaCost = jsonCard["manaCost"].AsInt;
			card.name = jsonCard["cardName"];
			card.description = jsonCard["description"];
			card.attack = jsonCard["attack"].AsInt;
			card.health = jsonCard["health"].AsInt;

			// Get Card Sprite
			String imgPath = "CardImages/" + (String)card.name.ToLower().Replace(" ","");
			card.sprite = GetSpriteFromLocalPath(imgPath);

			toReturn.Add(card);
		}

		return toReturn;
	}

	public List<Minion> ParseMinions(JSONArray minions) {
		List<Minion> toReturn = new List<Minion>();

		foreach(JSONNode jsonMinion in minions){
			Minion minion = gameObject.AddComponent<Minion>();
			minion.name = jsonMinion["minionName"];
			minion.health = jsonMinion["minionHealth"].AsInt;
			minion.attack = jsonMinion["minionAttack"].AsInt;
			minion.hasAttacked = jsonMinion["minionHasAttacked"].AsBool;
			minion.maxHealth = jsonMinion["maxHealth"].AsInt;

			// Get Minion Sprite
			String imgPath = "CardImages/" + (String)minion.name.ToLower().Replace(" ","");
			minion.sprite = GetSpriteFromLocalPath(imgPath);

			toReturn.Add(minion);
		}

		return toReturn;
	}

	public static Sprite GetSpriteFromLocalPath(String path){
		Texture2D texture = Resources.Load<Texture2D>(path);
		if(texture == null){
			Debug.Log("MISSING IMAGE: " + path.ToString());
			texture = Resources.Load<Texture2D>("CardImages/aaroncote");
		}
		return Sprite.Create(texture, new Rect(0,0,texture.width,texture.height), new Vector2(0.5f, 0.5f));
	}
}
