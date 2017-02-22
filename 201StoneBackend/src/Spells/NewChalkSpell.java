package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class NewChalkSpell extends Card{
	public NewChalkSpell() {
		super();
		cardID = 24;
		cardName = "New Chalk";
		manaCost = 2;
		description = "Restore 5 health to your hero.";
		isTargetable = false;
	}
	@Override
	public void perform(){
//		if(owner.equals(owner.game.player1)) {
//			for (int i = 0; i < 7; i++) {
//				if(owner.game.boardState[0][i] != null) {
//					Minions affectedMinion = owner.game.boardState[0][i];
//					affectedMinion.minionHealth = affectedMinion.minionHealth+3;
//					if(affectedMinion.minionHealth > affectedMinion.maxHealth) {
//						affectedMinion.minionHealth = affectedMinion.maxHealth;
//					}
//				}
//			}
//		}
//		else if (owner.equals(owner.game.player2)) {
//			for (int i = 0; i < 7; i++) {
//				if(owner.game.boardState[1][i] != null) {
//					Minions affectedMinion = owner.game.boardState[1][i];
//					affectedMinion.minionHealth = affectedMinion.minionHealth+3;
//					if(affectedMinion.minionHealth > affectedMinion.maxHealth) {
//						affectedMinion.minionHealth = affectedMinion.maxHealth;
//					}
//				}
//			}
//		}
		owner.hero.minionHealth+=5;
		if (owner.hero.minionHealth>30) owner.hero.minionHealth=30;
	}
}
