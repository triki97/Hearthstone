package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class MomsCookingSpell extends Card{
	public MomsCookingSpell() {
		super();
		cardID = 5;
		cardName = "Mom's Cooking";
		manaCost = 2;
		description = "Deal 1 damage to all minions.";
		isTargetable = false;
	}
	@Override
	public void perform(){
			for (int i = 0; i < 2; i++) {
				for(int j = 0; j < 7; j++) {
					if(owner.game.boardState[i][j] != null) {
						Minions affectedMinion = owner.game.boardState[i][j];
						affectedMinion.minionHealth = affectedMinion.minionHealth - 1;
					}
				}
			
		}
	}
}
