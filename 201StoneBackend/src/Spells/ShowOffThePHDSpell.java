package Spells;

import GameLogic.Card;
import GameLogic.Minions;

public class ShowOffThePHDSpell extends Card{
	public ShowOffThePHDSpell() {
		super();
		cardID = 26;
		cardName = "Show Off The PHD";
		manaCost = 4;
		description = "Deal 3 damage to all characters";
		isTargetable = false;
	}
	@Override
	public void perform(){
		owner.game.player1.hero.minionHealth-=3;
		owner.game.player2.hero.minionHealth-=3;
		for (int j = 0; j<2; j++){
			for (int i = 0; i<7; i++){
				if (owner.game.boardState[j][i]!=null){
					owner.game.boardState[j][i].minionHealth-=3;
				}
			}
		}
	}
}
