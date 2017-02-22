using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class CardHand : MonoBehaviour {

	public int maxHandSize = 10;

	public List<Card> cardData;

	[HideInInspector]
	public Transform[] cards;
	public Transform cardPrefab;

	// Use this for initialization
	void Start () {
		cards = new Transform[maxHandSize];
		for(int k = 0; k < cards.Length; ++k){
			Transform transform = Instantiate(cardPrefab, GetComponent<Transform>()) as Transform;
			transform.localPosition = new Vector3(k * (270 * 0.1f), 0);
			cards[k] = transform;
		}

		/*
		cardData = new List<Card>();
		for(int k = 0; k < 10; ++k){
			Card c = gameObject.AddComponent<Card>();
			c.attack = k;
			c.manaCost = k;

			Minion m = c.gameObject.AddComponent<Minion>();
			m.attack = 10;
			m.health = 10;
			m.hasAttacked = false;
			m.sprite = c.sprite;

			c.minion = m;	
			cardData.Add(c);
		}
		*/

		UpdateCards();	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public void UpdateCards() {
		for(int k = 0; k < maxHandSize; ++k){
			if(k < cardData.Count){
				cards[k].GetComponent<CanvasGroup>().alpha = 1f;
				cards[k].GetComponent<CanvasGroup>().blocksRaycasts = true;

				Card card = cards[k].GetComponent<Card>();
				card.UpdateCard(cardData[k]);
			}
			else {
				cards[k].GetComponent<CanvasGroup>().alpha = 0f;
				cards[k].GetComponent<CanvasGroup>().blocksRaycasts = false;
			}
		}
	}

	public int GetCardIndex(Card cardToGet){
		int cardIndex = 0;
		foreach(Transform transform in cards) {
			Card card = transform.GetComponent<Card>();
			if(cardToGet == card) break;
			++cardIndex;
		}

		return cardIndex;
	}

	public void RemoveCard(Card toRemove) {
		int cardIndex = GetCardIndex(toRemove);
		cardData.RemoveAt(cardIndex);
		UpdateCards();
	}

	public void AddCard(Card toAdd) {
		cardData.Add(toAdd);
		UpdateCards();
	}
}
