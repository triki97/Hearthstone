package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class StressedOutStudentMinion extends Minions{
	public StressedOutStudentMinion(){
		//17
		minionHealth = 2;
		minionAttack = 4;
		maxHealth = 2;
		minionHasAttacked = false;
		minionName = "Stressed Out Student";
	}
	
	public void battleCry(Player player){
		if(player.equals(player.game.player1)) {
			if (player.game.player2.manaCapacity < 10) {
				player.game.player2.manaCapacity = 	player.game.player2.manaCapacity + 1;
			}
		}
		else if (player.equals(player.game.player2)) {
			if (player.game.player1.manaCapacity < 10) {
				player.game.player1.manaCapacity = 	player.game.player1.manaCapacity + 1;
			}
		}
	}
}
