package Spells;

import GameLogic.Card;

public class OfficeHoursSpell extends Card{
	public OfficeHoursSpell() {
		super();
		cardID = 12;
		cardName = "Office Hours";
		manaCost = 1;
		description = "Deal 3 damage to the enemy hero.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			owner.game.player2.hero.minionHealth = owner.game.player2.hero.minionHealth-3;
		}
		else if (owner.equals(owner.game.player2)) {
			owner.game.player1.hero.minionHealth = owner.game.player1.hero.minionHealth-3;
		}
	}
}
