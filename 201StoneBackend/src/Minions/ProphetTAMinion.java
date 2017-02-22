package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class ProphetTAMinion extends Minions{
	public ProphetTAMinion(){
		//13
		minionHealth = 1;
		minionAttack = 1;
		maxHealth = 1;
		minionHasAttacked = true;
		minionName = "Prophet TA";
	}
	
	public void battleCry(Player player){
		if(player.equals(player.game.player1)) {
			player.game.player2.hero.minionHealth = player.game.player2.hero.minionHealth - 10;
		}
		else if (player.equals(player.game.player2)) {
			player.game.player1.hero.minionHealth = player.game.player1.hero.minionHealth - 10;
		}
	}
}
