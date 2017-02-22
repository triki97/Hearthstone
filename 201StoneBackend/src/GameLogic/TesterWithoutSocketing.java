package GameLogic;

import java.sql.SQLException;
import java.util.Scanner;

public class TesterWithoutSocketing extends Thread{
	Game game;
	public TesterWithoutSocketing(Game game){
		this.game = game;
		this.start();
	}
	
	public void run(){
		Scanner sc = new Scanner(System.in);
		while (true){
			String s = sc.nextLine();
			game.println(s);
			String [] ss = s.split(" ");
			if (ss[0].equals("endturn") || ss[0].equals("pass")){
				game.nextTurn();
			}
			if (ss[0].equals("playcard") || ss[0].equals("play")){
				game.playCard(Integer.parseInt(ss[1]));
			}
			if (ss[0].equals("print")){
				game.printBoard();
			}
			if (ss[0].equals("attack")){
				game.attack(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
			}
			if (ss[0].equals("end")){
				game.attack(Integer.parseInt(ss[1]), Integer.parseInt(ss[2]));
			}
		}
	}

	public static void main(String [] args) throws SQLException{
		Game game = new Game("a", "b", null, true , false);
		game.printBoard();
		new TesterWithoutSocketing(game);
	}
}
