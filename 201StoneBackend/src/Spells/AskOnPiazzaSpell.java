package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class AskOnPiazzaSpell extends Card{
	public AskOnPiazzaSpell() {
		super();
		cardID = 3;
		cardName = "Ask on Piazza";
		manaCost = 3;
		description = "Destroy a random enemy minion.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		int numMinions = 0;
		if(owner.equals(owner.game.player1)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[1][i] != null) {
					numMinions++;
				}
			}
			if(numMinions != 0) {
				int minionToRemove = (int) (Math.random() * numMinions);
				owner.game.boardState[1][minionToRemove].minionHealth = 0;
			}
		}
		else if (owner.equals(owner.game.player2)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[0][i] != null) {
					numMinions++;
				}
			}
			if(numMinions != 0) {
				int minionToRemove = (int) (Math.random() * numMinions);
				owner.game.boardState[0][minionToRemove].minionHealth = 0;
			}
		}
		

	}
}
