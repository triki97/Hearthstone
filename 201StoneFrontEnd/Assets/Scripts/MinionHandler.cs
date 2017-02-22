using UnityEngine;
using System.Collections;
using UnityEngine.EventSystems;

public class MinionHandler : MonoBehaviour, IPointerClickHandler {

	public static GameObject clickedMinion;
	private static GameBoard gameBoard;

	// Use this for initialization
	void Start () {
		gameBoard = GameManager.instance.gameBoard;
	}

	// Update is called once per frame
	void Update () {

	}

	#region IPointerClickHandler implementation

	public void OnPointerClick (PointerEventData eventData)
	{
		// First Click on your own minion
		if(clickedMinion == null){
			if(gameObject.GetComponent<Minion>().hasAttacked) return;

			clickedMinion = gameObject;
			clickedMinion.GetComponent<Minion>().selected = true;

			// Make all minions on other side interactable and minions on player side not interactable (except minion clicked)
			gameBoard.SetInteractable(0, false);
			gameBoard.SetInteractable(1, true);

			// Remake minion clicked, interactable
			clickedMinion.GetComponent<CanvasGroup>().blocksRaycasts = true;
			clickedMinion.GetComponent<CanvasGroup>().interactable = true;

			// Set Enemy Hero interactable
			gameBoard.SetHeroInteractable(1, true);
		}
		// Second click on an opponent minion
		else {
			if(clickedMinion.Equals(gameObject)){
				UnselectMinion();
				return;
			}

			GameObject minionToAttack = gameObject;

			GameManager GM = GameManager.instance;

			// Get Minion Indices and send it to the Server
			int playerMinionIndex = GM.gameBoard.GetMinionIndex(0, clickedMinion.GetComponent<Minion>());
			int opponentMinionIndex = -1;
			if(minionToAttack.GetComponent<Hero>() == null) {
				opponentMinionIndex = GM.gameBoard.GetMinionIndex(1, minionToAttack.GetComponent<Minion>());
			};

			GM.SendString("Minion " + playerMinionIndex + " " + opponentMinionIndex);

			// Client-side: unselect minion
			UnselectMinion();
		}
		
	}

	#endregion

	public static void UnselectMinion() {
		clickedMinion.GetComponent<Minion>().selected = false;
		clickedMinion = null;

		// Make all minions on player side interactable and minions on opponent side not interactable
		gameBoard.SetInteractable(1, false);
		gameBoard.SetInteractable(0, true);

		// Set Enemy Hero not interactable
		gameBoard.SetHeroInteractable(1, false);
	}
}
