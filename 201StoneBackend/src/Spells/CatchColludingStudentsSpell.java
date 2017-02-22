package Spells;

import GameLogic.Card;

public class CatchColludingStudentsSpell extends Card{
	public CatchColludingStudentsSpell() {
		super();
		cardID = 23;
		cardName = "Catch Colluding Students";
		manaCost = 8;
		description = "Destroy all minions.";
		isTargetable = false;
	}
	@Override
	public void perform(){
		for(int i = 0; i < 2; i++) {
			for (int j = 0; j < 7; j++) {
				if(owner.game.boardState[i][j] != null) {
					owner.game.boardState[i][j].minionHealth = 0;
				}
			}
		}
		
	}
}
