using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;
using System.Collections;

public class CardDropzone : MonoBehaviour, IDropHandler {
	
	#region IDropHandler implementation
	public void OnDrop (PointerEventData eventData) {
		GameObject obj = CardDragHandler.draggedObject;
		if(obj == null) return;

		Card card = obj.GetComponent<Card>();
		// Send to Server to Summon Minion
		GameManager GM = GameManager.instance;
		GM.SendString("Card " + GM.cardHand.GetCardIndex(card));

		// If there are any minions to unselect
		if(MinionHandler.clickedMinion != null){
			MinionHandler.UnselectMinion();
		}

		if(card.name == "Aaron Cote") {
			GameObject.FindGameObjectWithTag("AudioSource").GetComponent<AudioSource>().Play();
			StartCoroutine(Pause(1.5f));
		}

		// Change Mana capacity based on cards
		GM.manaCapacity.UsedMana += card.manaCost;

		// Destroy the card from the hand
		GM.cardHand.RemoveCard(card);

		MouseExit();
	}
	#endregion

	public void MouseEnter () {
		if(CardDragHandler.draggedObject != null){
			GetComponent<CanvasGroup>().alpha = 1f;
		}
	}

	public void MouseExit () {
		GetComponent<CanvasGroup>().alpha = 0f;
	}

	public IEnumerator Pause(float time){
		GameObject.FindGameObjectWithTag("MainCamera").GetComponent<AudioSource>().Pause();
		yield return new WaitForSeconds(time);
		GameObject.FindGameObjectWithTag("MainCamera").GetComponent<AudioSource>().Play();
	}

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}
