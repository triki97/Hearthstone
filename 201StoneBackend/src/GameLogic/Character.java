package GameLogic;

import org.json.simple.JSONObject;

import Server.GameServer;
public class Character {
	protected String minionName;
	public int minionHealth;
	public int minionAttack;
	protected boolean minionHasAttacked;
	public int maxHealth;
	
	public void attack(Character characterToAttack){
		if (minionHasAttacked==true) {
			System.out.println("Character attacked.");
			return;
		}
		if (characterToAttack == null){
			System.out.println("characterToAttack DNE");
		}
		
		setHealth(-1 * characterToAttack.getAttack());

		characterToAttack.setHealth(-1 * this.getAttack());

		
		this.setAttacked(true);
		
		//if(this.minionHealth <= 0) {
		if(this.getHealth() <= 0) {
			characterDiesRemoveItFromBoard(this);
		}
		//if(characterToAttack.minionHealth <= 0) {
		if(characterToAttack.getHealth() <= 0) {
			characterDiesRemoveItFromBoard(characterToAttack);
		}
	}
	
	public void characterDiesRemoveItFromBoard(Character characterToDie) {
	
	}
	
	public JSONObject convertCharacterToJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("minionName", minionName);
		obj.put("minionHealth", minionHealth);
		obj.put("minionAttack", minionAttack);
		obj.put("minionHasAttacked", minionHasAttacked);
		obj.put("maxHealth", maxHealth);
		return obj;
	}
	
	public void setHealth(int health) {
		minionHealth = minionHealth + health;
	}
	public int getHealth() {
		return minionHealth;
	}
	public String getName() {
		return minionName;
	}
	public int getAttack() {
		return minionAttack;
	}
	public boolean getAttacked() {
		return minionHasAttacked;
	}
	public void setAttacked(boolean status) {
		minionHasAttacked = status;
	}
	
}
