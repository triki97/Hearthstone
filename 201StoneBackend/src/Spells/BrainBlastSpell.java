package Spells;

import GameLogic.Card;

public class BrainBlastSpell extends Card{
	public BrainBlastSpell() {
		super();
		cardID = 11;
		cardName = "Brain Blast";
		manaCost = 3;
		description = "Draw 2 cards.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		owner.drawCard();
		owner.drawCard();
	}
}
