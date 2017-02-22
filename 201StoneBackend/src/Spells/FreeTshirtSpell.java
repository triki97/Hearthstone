package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class FreeTshirtSpell extends Card{
	public FreeTshirtSpell() {
		super();
		cardID = 14;
		cardName = "Free T-Shirt";
		manaCost = 4;
		description = "Give all friendly minions +3 health.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[0][i] != null) {
					Minions affectedMinion = owner.game.boardState[0][i];
					affectedMinion.minionHealth = affectedMinion.minionHealth + 3;
				}
			}
		}
		else if (owner.equals(owner.game.player2)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[1][i] != null) {
					Minions affectedMinion = owner.game.boardState[1][i];
					affectedMinion.minionHealth = affectedMinion.minionHealth + 3;
				}
			}
		}
	}
}
