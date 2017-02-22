using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Collections;
using SimpleJSON;

public class MainScreen : MonoBehaviour {
	private SocketConnector connector;
	public Button readyButton;

	public Hero[] heroes;
	public int selectedHero = -1;

	// Use this for initialization
	void Start () {
		connector = SocketConnector.instance;

		if(PlayerPrefs.GetInt("isGuest") == 1){
			CanvasGroup canvasGroup = GameObject.FindGameObjectWithTag("Interactable").GetComponent<CanvasGroup>();
			canvasGroup.alpha = 0.6f;
			canvasGroup.interactable = false;
			canvasGroup.blocksRaycasts = false;
		}
	}
	
	// Update is called once per frame
	void Update () {
		readyButton.interactable = (PlayerPrefs.GetInt("isGuest") == 1) || selectedHero != -1;
	}

	public void HeroHover(Hero hero) {
		hero.hasAttacked = false;
	}

	public void HeroHoverExit(Hero hero){
		hero.hasAttacked = true;
	}

	public void HeroSelected(Hero hero){
		int i = 0;
		int heroIndex = 0;
		foreach(Hero h in heroes){
			if(h == hero){
				heroIndex = i;
			}
			h.selected = false;
			++i;
		}

		hero.selected = true;
		selectedHero = heroIndex;
	}

	public void ReadyClicked () {
		string readyStr = "GameReady";
		if(selectedHero != -1) readyStr += " " + selectedHero;
		else readyStr += " 0"; // Defaulted to Deck 0

		Debug.Log(readyStr);

		CanvasGroup canvasGroup = GameObject.FindGameObjectWithTag("Interactable").GetComponent<CanvasGroup>();
		canvasGroup.alpha = 0.6f;
		canvasGroup.interactable = false;
		canvasGroup.blocksRaycasts = false;
		readyButton.enabled = false;
		readyButton.interactable = false;

		connector.socket.SendString(readyStr);

		SocketConnector.ResponseDelegate callback = gameReady;
		StartCoroutine(connector.GetResponse(callback));
	}

	public void gameReady (JSONNode response) {
		int playerNumber = response["player"].AsInt;
		PlayerPrefs.SetInt("player", playerNumber);
		PlayerPrefs.SetInt("deck", selectedHero == 1 ? 1 : 0);

		// Initialize Game
		SceneManager.LoadScene(2); // Game Board
	}
		
}
