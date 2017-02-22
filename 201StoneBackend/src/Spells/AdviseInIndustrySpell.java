package Spells;

import GameLogic.Card;

public class AdviseInIndustrySpell extends Card{
	public AdviseInIndustrySpell() {
		super();
		cardID = 34;
		cardName = "Advise In Industry";
		manaCost = 8;
		description = "Restore 15 health to your hero.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		if(owner.equals(owner.game.player1)) {
			owner.game.player1.hero.minionHealth += 15;
			if(owner.game.player1.hero.minionHealth > 30) {
				owner.game.player1.hero.minionHealth = 30;
			}
		}
		else if (owner.equals(owner.game.player2)) {
			owner.game.player2.hero.minionHealth = owner.game.player2.hero.minionHealth + 15;
			if(owner.game.player2.hero.minionHealth > 30) {
				owner.game.player2.hero.minionHealth = 30;
			}
		}
		owner.game.ConvertGameToJSON();
	}
}
