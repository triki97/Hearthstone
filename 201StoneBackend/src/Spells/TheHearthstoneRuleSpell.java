package Spells;

import GameLogic.Card;

public class TheHearthstoneRuleSpell extends Card{
	public TheHearthstoneRuleSpell() {
		super();
		cardID = 15;
		cardName = "The Hearthstone Rule";
		manaCost = 4;
		description = "Deal 5 damage to both heros.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		owner.game.player1.hero.minionHealth-=5;
		owner.game.player2.hero.minionHealth-=5;
	}
}
