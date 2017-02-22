using UnityEngine;
using System.Collections;

public class Minion : Character {

	// Use this for initialization
	public void Start () {
		base.Start();
	}

	// Update is called once per frame
	public void Update () {
		base.Update();
	}

	public void UpdateMinion(Minion m) {
		name = m.name;
		health = m.health;
		maxHealth = m.maxHealth;
		attack = m.attack;
		hasAttacked = m.hasAttacked;
		sprite = m.sprite;
	}
}
