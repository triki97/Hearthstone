package Minions;

import GameLogic.Deck;
import GameLogic.Minions;
import GameLogic.Player;

public class LeonardAdlemanMinion extends Minions{
	public LeonardAdlemanMinion(){
		//28
		minionHealth = 4;
		minionAttack = 3;
		maxHealth = 4;
		minionHasAttacked = true;
		minionName = "Leonard Adleman";
	}
	
	@Override
	public void battleCry(Player player){
		Deck deck = player.game.player1.deck;
		player.game.player1.deck = player.game.player2.deck;
		player.game.player2.deck = deck;
	}
}
