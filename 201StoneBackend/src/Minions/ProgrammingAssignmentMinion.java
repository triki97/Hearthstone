package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class ProgrammingAssignmentMinion extends Minions {
	public ProgrammingAssignmentMinion(){
		//19
		minionHealth = 2;
		minionAttack = 2;
		maxHealth = 2;
		minionHasAttacked = true;
		minionName = "Programming Assignment";
	}
	
	public void battleCry(Player player){
		if (player.game.currentPlayer == 1){
			for (int i = 0 ; i < 7; i++){
				if (player.game.boardState[1][i]!=null){
					player.game.boardState[1][i].minionHealth-=2;
				}
			}
		}
		else{
			for (int i = 0 ; i < 7; i++){
				if (player.game.boardState[0][i]!=null){
					player.game.boardState[0][i].minionHealth-=2;
				}
			}
		}	
	}
}
