package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class CS201StudentMinion extends Minions{
	public CS201StudentMinion(){
		//
		minionHealth = 10;
		minionAttack = 10;
		maxHealth = 10;
		minionHasAttacked = true;
		minionName = "CS201 Student";
	}
	
	public void battleCry(Player player){
		this.minionHealth = this.minionHealth - 5;
	}
}
