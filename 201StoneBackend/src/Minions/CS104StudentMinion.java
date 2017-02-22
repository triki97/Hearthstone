package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class CS104StudentMinion extends Minions {
	public CS104StudentMinion(){
		//2
		minionHealth = 3;
		minionAttack = 4;
		maxHealth = 3;
		minionHasAttacked = true;
		minionName = "CS104Student";
	}
	
	
	public void battleCry(Player player){
		player.hero.minionHealth-=3;
	}
}
