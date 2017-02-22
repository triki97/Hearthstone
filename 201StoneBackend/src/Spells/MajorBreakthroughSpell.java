package Spells;

import GameLogic.Card;

public class MajorBreakthroughSpell extends Card{
	public MajorBreakthroughSpell() {
		super();
		cardID = 20;
		cardName = "Major Breakthrough";
		manaCost = 2;
		description = "Draw 2 cards.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		owner.drawCard();
		owner.drawCard();
	}
}
