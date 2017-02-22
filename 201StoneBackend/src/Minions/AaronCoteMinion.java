package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class AaronCoteMinion extends Minions {
	//26
	public AaronCoteMinion(){
		minionHealth = 5;
		minionAttack = 4;
		maxHealth = 5;
		minionHasAttacked = true;
		minionName = "Aaron Cote";
	}
	
	public void battleCry(Player player){
		player.hero.minionHealth = 30;
	}
}
