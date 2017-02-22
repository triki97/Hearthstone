package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class SatyRaghavacharyMinion extends Minions{
	public SatyRaghavacharyMinion(){
		minionHealth = 2;
		minionAttack = 2;
		maxHealth = 2;
		minionHasAttacked = true;
		minionName = "Saty Raghavachary";
	}
	
	@Override
	public void battleCry(Player player){
		player.summon(38);
	}
}
