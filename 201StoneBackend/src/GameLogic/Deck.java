package GameLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import com.mysql.jdbc.Driver;

import Server.Constants;

public class Deck {
	public Connection con;
	public PreparedStatement ps;
	public final static String getStudentDeck = "SELECT * FROM StudentDeck";
	public final static String getProffesorDeck = "SELECT * FROM ProfessorDeck";

	public boolean whichDeckToPull;
	public Vector<Card> cardsInDeck = new Vector<Card>();
	public Card DrawCard(){
		Card card = cardsInDeck.remove(0);
		
		return card;
	}
	
	public Deck(boolean whichDeckToPull) throws SQLException{
		this.whichDeckToPull = whichDeckToPull;
		try {
			new Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createDeck() throws SQLException {
		if(whichDeckToPull == false) {
			ps = con.prepareStatement(getStudentDeck);
		}
		else {
			ps = con.prepareStatement(getProffesorDeck);
		}
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			Card newCard = new Card();
			newCard.cardName = result.getString("CardName");
			newCard.manaCost = result.getInt("ManaCost");
			newCard.description = result.getString("Effect");
			newCard.Attack = result.getInt("Attack");
			newCard.Defense = result.getInt("Defense");
			newCard.cardID = result.getInt("CardId");
			int cardID = result.getInt("CardID");
//			cardsInDeck.addElement(newCard);
			for( int i = 0; i < result.getInt("Quantity"); i++) {
//				cardsInDeck.addElement(newCard);
				if (newCard.Attack!=-1){
					cardsInDeck.addElement(
							new MinionCard(cardID, newCard.cardName, newCard.description, newCard.manaCost, newCard.Attack, newCard.Defense));
				}
				else {
					cardsInDeck.addElement(CardCreator.createCard(cardID));
				}
			}
			Collections.shuffle(cardsInDeck); //Randomizes the deck
		}
	}
	
	public void connect() {
		try {
			con = DriverManager.getConnection(Constants.SQL_CONNECTION, Constants.SQL_USERNAME, Constants.SQL_PASSWORD);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
