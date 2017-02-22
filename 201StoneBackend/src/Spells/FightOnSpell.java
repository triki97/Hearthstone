package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class FightOnSpell extends Card{
	public FightOnSpell() {
		super();
		cardID = 8;
		cardName = "Fight On";
		manaCost = 4;
		description = "Give all friendly minions plus +1 attack.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[0][i] != null) {
					Minions affectedMinion = owner.game.boardState[0][i];
					affectedMinion.minionAttack = affectedMinion.minionAttack+1;

				}
			}
		}
		else if (owner.equals(owner.game.player2)) {
			for (int i = 0; i < 7; i++) {
				if(owner.game.boardState[1][i] != null) {
					Minions affectedMinion = owner.game.boardState[1][i];
					affectedMinion.minionAttack = affectedMinion.minionAttack+1;
				}
			}
		}
	}
}
