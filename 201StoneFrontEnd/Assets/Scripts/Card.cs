using UnityEngine;
using UnityEngine.UI;
using System.Collections;

[System.Serializable]
public class Card : MonoBehaviour {
	
	public string name;
	public int manaCost;
	public int health;
	public int attack;
	public string description;

	public Minion minion;

	public Image image;
	public Sprite sprite;

	public Text titleText;
	public Text manaText;
	public Text healthText;
	public Text attackText;
	public Text descriptionText;

	public CanvasGroup canvasGroup;


	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		if(titleText != null) titleText.text = name;
		if(manaText != null) manaText.text = manaCost.ToString();
		if(healthText != null) healthText.text = health.ToString();
		if(attackText != null) attackText.text = attack.ToString();
		if(descriptionText != null) descriptionText.text = description;
		if(image != null) image.overrideSprite = sprite;
	}

	public void UpdateCard(Card c) {
		name = c.name;
		manaCost = c.manaCost;
		health = c.health;
		attack = c.attack;
		sprite = c.sprite;
		description = c.description;
		minion = c.minion;
	}

	public void MouseEnter () {
		// If user is already selecting a card
		if(CardDragHandler.draggedObject != null) return;

		canvasGroup.alpha = 0.5f;

		Card card = GameObject.FindGameObjectWithTag("CardDisplay").GetComponent<Card>();
		card.canvasGroup.alpha = 1f;
		card.UpdateCard(this);
	}

	public void MouseExit() {
		canvasGroup.alpha = 1f;

		Card card = GameObject.FindGameObjectWithTag("CardDisplay").GetComponent<Card>();
		card.canvasGroup.alpha = 0f;
	}
}
