package GameLogic;

public class AaronCoteCard extends Card{
	public AaronCoteCard(){
		super();
		cardID=29;
		minionID=29;
		cardName = "AaronCote";
		manaCost = 7;
		description = "It is completely NP to be more OP(i) than Aaron Cote";
		isTargetable = false;
	}
	
	@Override
	public void perform(){
		owner.summon(minionID);
	}
}
