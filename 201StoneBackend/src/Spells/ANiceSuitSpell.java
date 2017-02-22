package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class ANiceSuitSpell extends Card{
	public ANiceSuitSpell() {
		super();
		cardID = 33;
		cardName = "A Nice Suit";
		manaCost = 4;
		description = "Restore all friendly minions to full health.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[0][i] != null) {
					Minions affectedMinion = owner.game.boardState[0][i];
					affectedMinion.minionHealth = affectedMinion.maxHealth;
				}
			}
		}
		else if (owner.equals(owner.game.player2)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[1][i] != null) {
					Minions affectedMinion = owner.game.boardState[1][i];
					affectedMinion.minionHealth = affectedMinion.maxHealth;
				}
			}
		}
	}
}
