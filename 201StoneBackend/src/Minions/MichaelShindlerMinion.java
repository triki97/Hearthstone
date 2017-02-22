package Minions;

import GameLogic.Game;
import GameLogic.Minions;
import GameLogic.Player;

public class MichaelShindlerMinion extends Minions{
	public MichaelShindlerMinion(){
		//27
		minionHealth = 5;
		minionAttack = 5;
		maxHealth = 5;
		minionHasAttacked = true;
		minionName = "Michael Shindler";
	}
	
	@Override
	public void battleCry(Player player){
		Game game = player.game;
		for (int i = 0; i<2; i++){
			for (int j = 0; j<7; j++){
				if (game.boardState[i][j]!=null){
					if (game.boardState[i][j].getName().equals("Aaron Cote")){
						game.boardState[i][j].maxHealth+=5;
						game.boardState[i][j].minionHealth+=5;
						game.boardState[i][j].minionAttack+=5;
					}
				}
			}
		}
	}
}
