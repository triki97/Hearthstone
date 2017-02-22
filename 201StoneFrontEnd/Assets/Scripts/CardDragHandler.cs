using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;
using System.Collections;

public class CardDragHandler : MonoBehaviour, IBeginDragHandler, IDragHandler, IEndDragHandler {

	public static GameObject draggedObject;
	Vector3 startPosition;
	bool canDrag;

	#region IBeginDragHandler implementation

	public void OnBeginDrag (PointerEventData eventData){
		if(!canDrag) return;

		draggedObject = gameObject;
		startPosition = transform.localPosition;
		GetComponent<CanvasGroup>().blocksRaycasts = false; // Block Raycasts = false for the Card
		GameManager.instance.gameBoard.SetInteractable(0, false); // Set all Minions as uninteractable for player side
	}

	#endregion

	#region IDragHandler implementation

	public void OnDrag (PointerEventData eventData)
	{
		if(!canDrag) return;

		transform.position = new Vector3(eventData.position.x, eventData.position.y);
	}

	#endregion

	#region IEndDragHandler implementation

	public void OnEndDrag (PointerEventData eventData)
	{
		if(!canDrag) return;


		draggedObject = null;
		GameManager.instance.gameBoard.SetInteractable(0, true); // Make them interactable again
		transform.localPosition = startPosition;

		// If dropped on the Dropzone, don't reset the card position
		RectTransform dropzone = GameObject.FindGameObjectWithTag("CardDropzone").GetComponent<RectTransform>();
		Vector2 dropzoneOrigin = new Vector2(dropzone.position.x - dropzone.sizeDelta.x/2, dropzone.position.y - dropzone.sizeDelta.y/2);
		Rect rect = new Rect(dropzoneOrigin, dropzone.sizeDelta);

		if(!rect.Contains(new Vector3(eventData.position.x, eventData.position.y))){
			GetComponent<CanvasGroup>().blocksRaycasts = true;
		}
	}

	#endregion

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		GameManager GM = GameManager.instance;
		Card card = gameObject.GetComponent<Card>();
		canDrag = !GM.gameBoard.isFull() && (GM.manaCapacity.AvailableMana() >= card.manaCost);
	}
}
