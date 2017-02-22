package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class JeffreyMillerPHDMinion extends Minions{
	public JeffreyMillerPHDMinion(){
		//32
		minionHealth = 1;
		minionAttack = 1;
		maxHealth = 1;
		minionHasAttacked = true;
		minionName = "Jeffrey Miller PHD";
	}
	
	@Override
	public void battleCry(Player player){
		for(int i = 0; i < 2; i++) {
			for (int j = 0; j < 7; j++) {
				if(player.game.boardState[i][j] != null) {
					if (!player.game.boardState[i][j].getName().equals("Jeffrey Miller PHD"))
						player.game.boardState[i][j].minionHealth = 0;
				}
			}
		}
		player.game.player1.hero.minionHealth = 1;
		player.game.player2.hero.minionHealth = 1;
	}
}
