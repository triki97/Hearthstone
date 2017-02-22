package GameLogic;

import org.json.simple.JSONObject;

public class Card {
	public int cardID;
	public int manaCost;
	public String cardName;
	public String description;
	protected Player owner;
	protected boolean isTargetable;
	public int Attack;
	public int Defense;
	public int minionID;
	//TODO
	//card Image
	
	public Card(){
		
	}
	
	public void perform(){
		owner.summon(2);
	}
	public void perform(Character c){
		
	}
	
	public JSONObject convertToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("manaCost", manaCost);
		obj.put("cardName", cardName);
		obj.put("description", description);
		obj.put("attack", Attack);
		obj.put("health", Defense);
		obj.put("cardID", cardID);
		obj.put("minionId", minionID);
		return obj;
	}
}
