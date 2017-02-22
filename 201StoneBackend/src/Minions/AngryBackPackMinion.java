package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class AngryBackPackMinion extends Minions{
	public AngryBackPackMinion(){
		//10
		minionHealth = 5;
		minionAttack = 5;
		maxHealth = 5;
		minionHasAttacked = true;
		minionName = "Angry Backpack";
	}
	
	public void battleCry(Player player){
		int cardToRemove = (int) (player.cardsInHand.size() * Math.random());
		player.cardsInHand.remove(cardToRemove);
	}
}
