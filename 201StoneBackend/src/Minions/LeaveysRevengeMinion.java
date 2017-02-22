package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class LeaveysRevengeMinion extends Minions{
	public LeaveysRevengeMinion(){
		//6
		minionHealth = 4;
		minionAttack = 4;
		maxHealth = 4;
		minionHasAttacked = true;
		minionName = "Leaveys Revenge";
	}	
	
	public void battleCry(Player player){
		player.drawCard();
	}
}
