package Minions;

import GameLogic.Minions;
import GameLogic.Player;

public class MarkRedekoppMinion extends Minions{
	public MarkRedekoppMinion(){
		//28
		minionHealth = 3;
		minionAttack = 4;
		maxHealth = 3;
		minionHasAttacked = true;
		minionName = "Mark Redekopp";
	}
	
	@Override
	public void battleCry(Player player){
		if (player.game.currentPlayer==1){
			player.drawCardFromDeck(player.game.player2.deck);
			player.drawCardFromDeck(player.game.player2.deck);
		}
		if (player.game.currentPlayer==2){
			player.drawCardFromDeck(player.game.player1.deck);
			player.drawCardFromDeck(player.game.player1.deck);
		}
	}
}
