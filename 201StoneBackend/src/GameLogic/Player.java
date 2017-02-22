package GameLogic;

import java.sql.SQLException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Player {
	public Vector<Card> cardsInHand = new Vector<Card>();
	public Deck deck;
	public Character hero;
	int availableMana;
	public int manaCapacity;
	String username;
	public Game game;
	int index;
	int numberOfMinionsOnBoard = 0;
	boolean studentOrProffesor;
	
	public Player(String username, Game game, int index, boolean studentOrProffesor) throws SQLException {
		this.username = username;
		this.game = game;
		this.index = index;
		this.studentOrProffesor = studentOrProffesor;
		availableMana = 0; 
		manaCapacity = 0;
		hero = new Hero();
		deck = new Deck(studentOrProffesor);
		deck.connect();
		deck.createDeck();
		for (int i = 0; i<4; i++){
			drawCard();
//			cardsInHand.add(deck.DrawCard());
		}
	}
	public void drawCard(){
		cardsInHand.add(deck.DrawCard());
		if (cardsInHand.size()>10){
			System.out.println("Hand Exploded");
			cardsInHand.remove(10);
		}
	}
	public void drawCardFromDeck(Deck thedeck){
		cardsInHand.add(thedeck.DrawCard());
		if (cardsInHand.size()>10){
			System.out.println("Hand Exploded");
			cardsInHand.remove(10);
		}
	}
	public void gainMana(){
		if(manaCapacity < 10) manaCapacity++;
		availableMana = manaCapacity;
	}
	public void summon(int minionID){
		int indexToSummonAt = 0;
		for(int i = 0; i < game.boardState[game.currentPlayer-1].length; i++) {
			if(game.boardState[game.currentPlayer-1][i] == null) {
				indexToSummonAt = i;
				break;
			}
		}
		game.boardState[index][indexToSummonAt]
			= MinionCreator.createMinion(minionID);
		game.boardState[index][indexToSummonAt].battleCry(this);
		game.printBoard();
		System.out.println("BATTLECRY");
	}
	public void playCard(int cardIndex){
		//TODO
		//Check Mana
		if (availableMana < cardsInHand.get(cardIndex).manaCost){
			System.out.println("not valid");
			return;
		}
		Card card = cardsInHand.remove(cardIndex);
		card.owner = this;
		card.perform();
		availableMana-=card.manaCost;
	}
	public void printhand(){
		for (int i = 0; i<cardsInHand.size(); i++){
			System.out.print(cardsInHand.get(i).cardName+" "+cardsInHand.get(i).manaCost+";");
		}
		System.out.println();
	}
	public JSONObject convertToJSONPlayer() {
		JSONObject obj = new JSONObject();
		JSONArray jsonArrayInHand = new JSONArray();
		if(cardsInHand.size() != 0) {
			for(int i = 0; i < cardsInHand.size(); i++) {
				jsonArrayInHand.add(cardsInHand.get(i).convertToJSON());
			}
		}
		obj.put("cardsInHand", jsonArrayInHand);
		JSONArray jsonArrayInDeck = new JSONArray();
		if(deck.cardsInDeck.size() != 0) {
			for(int i = 0; i < deck.cardsInDeck.size(); i++) {
				jsonArrayInDeck.add(deck.cardsInDeck.get(i).convertToJSON());
			}
		}
		//obj.put("cardsInDeck", jsonArrayInDeck);
		obj.put("availableMana", availableMana);
		obj.put("manaCapacity", manaCapacity);
		obj.put("username", username);
		obj.put("health", hero.minionHealth);
		obj.put("deckNumber", studentOrProffesor ? 1 : 0);
		System.out.println("health"+hero.minionHealth);
		return obj;
	}
}
