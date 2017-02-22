package GameLogic;

import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import Server.GameServer;
public class Game {
	//Connection Units
	
	//Send Functions
	public int currentTurn;
	//current turn: 0: starting, 1: processing, 2: 
	public int currentPlayer;
	public Player player1;
	public Player player2;
	public Minions[][] boardState = new Minions[2][7];
	private GameServer s;
	
	public Game(String player1Name, String player2Name, GameServer s, boolean student1Deck, boolean student2Deck) throws SQLException{
		Player player1 = new Player(player1Name, this, 0, student1Deck); 
		Player player2 = new Player(player2Name, this, 1, student2Deck);
		this.player1 = player1;
		this.player2 = player2;
		currentTurn = 1;
		currentPlayer = 1;
		this.s = s;
		startPhase();
	}
	
	public void printBoard(){
		//Testing function to print the whole board.
		System.out.println("Current Turn: "+ currentTurn);
		System.out.println("Current Player: "+ currentPlayer);
		System.out.println(player1.username+" "+player1.hero.getHealth()+
				" mana:"+player1.availableMana+"/"+player1.manaCapacity);
		player1.printhand();
		for (int i = 0; i<2; i++){
			for (int j = 0; j<7; j++){
				if (boardState[i][j]!=null)
				System.out.print(j+ boardState[i][j].minionName+" "
						+boardState[i][j].minionAttack+" "
						+boardState[i][j].minionHealth);
			}
			System.out.println();
		}
		player2.printhand();
		System.out.println(player2.username+" "+player2.hero.getHealth()+
				" mana:"+player2.availableMana+"/"+player2.manaCapacity);
		System.out.println("===========");
	}
	
	public void PerformingPhase(){
		//Use a TimerThread to count the time
	}
	
	
	public void EndGame(){
		System.out.println("End");
		if (player1.hero.minionHealth<=0 && player2.hero.minionHealth<=0){
			System.out.println("draw");
		}
		else if (player1.hero.minionHealth<=0){
			System.out.println("2 win");
		}
		else if (player2.hero.minionHealth<=0){
			System.out.println("1 win");
		}
		else {
			System.out.println("IDK what happened");
		}
		
	}
	
	public String ConvertGameToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("player1", player1.convertToJSONPlayer());
		obj.put("player2", player2.convertToJSONPlayer());
		obj.put("currentTurn", currentTurn);
		obj.put("currentPlayer", currentPlayer);
		JSONArray player1minions = new JSONArray();
		for(int i = 0; i < boardState[0].length; i++) {
			if(boardState[0][i] != null) {
				player1minions.add(boardState[0][i].convertCharacterToJSONString());
			}
		}
		obj.put("player1minions", player1minions);
		
		JSONArray player2minions = new JSONArray();
		for(int i = 0; i < boardState[1].length; i++) {
			if(boardState[1][i] != null) {
				player2minions.add(boardState[1][i].convertCharacterToJSONString());
			}
		}
		obj.put("player2minions", player2minions);
		String gameAsJSONString = obj.toString();
		return gameAsJSONString;
	}
	
	public void println(String s){
		System.out.println(s);
	}
	
	public void nextTurn(){
		endPhase();
		currentTurn++;
		currentPlayer = 3-currentPlayer;
		startPhase();
	}
	
	public void playCard(int i){
		System.out.println("player "+currentPlayer+" is playingCard"+i);
		if (currentPlayer==1){
			player1.playCard(i);
		}
		else{
			player2.playCard(i);
		}
	}
	public void startPhase(){
		if (currentPlayer == 1){
			player1.gainMana();
			player1.drawCard();
			for (int i = 0; i<7; i++){
				if (boardState[0][i]!=null){
					boardState[0][i].setAttacked(false);
				}
			}
		}
		if (currentPlayer == 2){
			player2.gainMana();
			player2.drawCard();
			for (int i = 0; i<7; i++){
				if (boardState[1][i]!=null){
					boardState[1][i].setAttacked(false);
				}
			}
		}
		
	}
	public void endPhase(){
		
	}
	public void attack(int i, int j){
		printBoard();
		System.out.println(i+" "+j);
		if (j == -1){
			//attacking hero
			if (currentPlayer == 1){
				boardState[0][i].attack(player2.hero);
			}
			if (currentPlayer==2){
				boardState[1][i].attack(player1.hero);
			}
		}
		else{
			if (currentPlayer == 1){
				boardState[0][i].attack(boardState[1][j]);
			}
			if (currentPlayer==2){
				boardState[1][i].attack(boardState[0][j]);
			}
		}
		
		//clearing the board
		if (player1.hero.minionHealth<=0){
			EndGame();
		}
		if (player2.hero.minionHealth<=0){
			EndGame();
		}
		System.out.println("AFTER ATTACK");
		printBoard();
		s.sendToAll(ConvertGameToJSON());
		clearBoard();
	}
	public void clearBoard(){
		for (int m = 0 ; m < 2; m++){
			for (int n = 0; n < 7; n++){
				if (boardState[m][n]!=null){
					if (boardState[m][n].minionHealth<=0){
						boardState[m][n] = null;
					}
				}
			}
			for (int n=0; n<7; n++){
				if (boardState[m][n]==null){
					int index = n;
					boolean check = false;
					while (index!=7 && !check){
						index++;
						if (index < 7)
							if (boardState[m][index]!=null){
								check = true;
							}
					}
					if (check){
						boardState[m][n] = boardState[m][index];
						boardState[m][index] = null;
					}
				}
			}
		}
	}
}
