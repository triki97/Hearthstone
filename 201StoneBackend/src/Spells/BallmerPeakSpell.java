package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class BallmerPeakSpell extends Card{
	public BallmerPeakSpell() {
		super();
		cardID = 4;
		cardName = "Ballmer Peak";
		manaCost = 8;
		description = "Give all friendly minions +3/+3.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[0][i] != null) {
					Minions affectedMinion = owner.game.boardState[0][i];
					affectedMinion.minionAttack = affectedMinion.minionAttack+3;
					affectedMinion.minionHealth = affectedMinion.minionHealth+3;
				}
			}
		}
		else if (owner.equals(owner.game.player2)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[1][i] != null) {
					Minions affectedMinion = owner.game.boardState[1][i];
					affectedMinion.minionAttack = affectedMinion.minionAttack+3;
					affectedMinion.minionHealth = affectedMinion.minionHealth+3;
				}
			}
		}
		
	}
}
