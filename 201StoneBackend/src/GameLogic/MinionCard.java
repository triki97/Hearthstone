package GameLogic;

public class MinionCard extends Card{
	public MinionCard(int id, String name, String descript, int cost, int attack, int defense){
		super();
		cardID = id;
		minionID=id;
		cardName = name;
		manaCost = cost;
		description = descript;
		isTargetable = false;
		Attack = attack;
		Defense = defense;
	}
	
	@Override
	public void perform(){
		owner.summon(minionID);
	}
}
