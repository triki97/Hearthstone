package Spells;

import GameLogic.Card;

public class PublishAPaperSpell extends Card{
	public PublishAPaperSpell() {
		super();
		cardID = 21;
		cardName = "Publish A Paper";
		manaCost = 2;
		description = "Gain an empty mana crystal.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if (owner.manaCapacity<10) owner.manaCapacity = owner.manaCapacity+1;
	}
}
