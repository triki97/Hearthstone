using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class Character : MonoBehaviour {
	public string name;
	public int health;
	public int maxHealth;
	public int attack;
	public bool hasAttacked;
	public bool selected;

	public Image image;
	public Sprite sprite;

	public Outline glow;

	public Text healthText;
	public Text attackText;

	public Color enabledColor;
	public Color selectedColor;

	// Use this for initialization
	public void Start () {
	}
	
	// Update is called once per frame
	public void Update () {
		CanvasGroup canvasGroup = GetComponent<CanvasGroup>();

		if(healthText != null){
			healthText.text = health.ToString();
			if(health < maxHealth) healthText.color = Color.red;
			else if(health > maxHealth) healthText.color = Color.green;
			else healthText.color = Color.white;
		}
		if(attackText != null) attackText.text = attack.ToString();

		if(image != null) image.overrideSprite = sprite;

		if(glow != null){
			Color glowColor = glow.effectColor;
			glowColor = selected ? selectedColor : enabledColor; // Set Color based on if selected or enabled
			glowColor.a = (hasAttacked || !canvasGroup.blocksRaycasts) && !selected ? 0 : 0.3f;
			glow.effectColor = glowColor;
		}
			
	}

	public void Attack(Character c) {
		health -= c.attack;
		c.health -= attack;
		hasAttacked = true;
	}
}
