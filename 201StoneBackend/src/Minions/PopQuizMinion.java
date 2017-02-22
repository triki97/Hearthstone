package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class PopQuizMinion extends Minions{
	public PopQuizMinion(){
		//24
		minionHealth = 5;
		minionAttack = 5;
		maxHealth = 5;
		minionHasAttacked = true;
		minionName = "Pop Quiz";
	}	
	
	public void battleCry(Player player){
		player.drawCard();
	}
}
