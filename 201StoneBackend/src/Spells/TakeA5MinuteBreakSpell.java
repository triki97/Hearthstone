package Spells;

import java.util.Vector;

import GameLogic.Card;

public class TakeA5MinuteBreakSpell extends Card{
	public TakeA5MinuteBreakSpell() {
		super();
		cardID = 35;
		cardName = "Take A 5 Minute Break";
		manaCost = 4;
		description = "Discard your hand. Gain 10 mana crystals.";
		isTargetable = false;
	}
	public void perform(){
		if (owner.game.currentPlayer==1){
			owner.game.player1.manaCapacity=10;
		}
		else{
			owner.game.player2.manaCapacity=10;
		}
		owner.cardsInHand = new Vector<Card>();
	}
}
