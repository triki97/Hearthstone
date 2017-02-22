package Server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONObject;

import GameLogic.Game;

public class GameServer extends WebSocketServer {
	public Login loginLogic;
	private Game game;
	static GameServer s;
	static int guestIndex = 1;
	boolean deck1Chosen = false;
	
	public ConcurrentHashMap<WebSocket, User> socketsToUsers = new ConcurrentHashMap<>();
	public GameServer(int port) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
		loginLogic = new Login();
		loginLogic.connect(); 
		
		checkUsersReady();
	}

	public GameServer( InetSocketAddress address ) {
		super( address );
	}

	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
		socketsToUsers.put(conn, new User());
	}

	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		System.out.println( conn + " has left the room!" );
		User disconnectedUser = socketsToUsers.get(conn);
		if(disconnectedUser.isReady){
			WebSocket otherConn = null;
			for(Map.Entry<WebSocket, User> entry : socketsToUsers.entrySet()){
				if(!disconnectedUser.username.equals(entry.getValue().username) && entry.getValue().isReady){
					otherConn = entry.getKey();
					break;
				}
			}
			
			otherConn.send(ClientSocketReader.successResponse(false).toString());
		}
		socketsToUsers.remove(conn);
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		String m = null;
	    try {
	        m = new String(message.array());
	        onMessage(conn, m);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public void onMessage( WebSocket conn, String message ) {
		JSONObject response;
		
		String[] tokens = message.split(Constants.CLIENT_DELIMETER);
		if(tokens.length <= 0) return;
		
		if(tokens[0].equals("Login") || tokens[0].equals("CreateAccount")) {
			if(tokens.length >= 3){
				for(Map.Entry<WebSocket, User> entry : socketsToUsers.entrySet()) {
					if(entry.getValue().username.equals(tokens[1])) {
						response = ClientSocketReader.successResponse(false);
						conn.send(response.toString());
						return;
					}
				}
			}
			response = loginLogic.parseMessage(message);
			conn.send(response.toString());
	
			if((boolean) response.get("success")){
				User user = socketsToUsers.get(conn);
				user.hasLoggedIn = true;
				user.username = response.get("username").toString();
			}
		}
		else if(tokens[0].equals("Guest")) {
			response = ClientSocketReader.successResponse(true);
			conn.send(response.toString());
			socketsToUsers.get(conn).isGuest = true;
			socketsToUsers.get(conn).hasLoggedIn = true;
			socketsToUsers.get(conn).username = "Guest_" + guestIndex;
			guestIndex++;
		}
		else if(tokens[0].equals("GameReady")) {
			socketsToUsers.get(conn).isReady = true;
			boolean deckToChoose = Integer.parseInt(tokens[1]) == 1;
			socketsToUsers.get(conn).studentOrProf = deckToChoose;
		}
		else if(tokens[0].equals("Card")) {
			int cardIndexInHand = Integer.parseInt(tokens[1]);
			game.playCard(cardIndexInHand);
			this.sendToAll(game.ConvertGameToJSON());
			game.clearBoard();
		}
		else if(tokens[0].equals("EndTurn")) {
			game.nextTurn();
			this.sendToAll(game.ConvertGameToJSON());
		}
		else if(tokens[0].equals("Minion")) {
			int minionAttacking = Integer.parseInt(tokens[1]);
			int minionToAttack = Integer.parseInt(tokens[2]);
			game.attack(minionAttacking, minionToAttack); // Sends to Client in here
		}
		
	}


	public static void main( String[] args ) throws InterruptedException , IOException, SQLException {
		WebSocketImpl.DEBUG = true;
		int port = 6789;
		try {
			port = Integer.parseInt( args[ 0 ] );
		} catch ( Exception ex ) {
		}
		
		s = new GameServer(port);
		s.start();
		
		System.out.println( "Server started on port: " + s.getPort() );
	}
	
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}

	public void sendToAll( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	}
	
	private void checkUsersReady () {
		Thread readyChecker = new Thread(new Runnable() {

			public void run() {
				try {
					while(true) {
						Vector<WebSocket> readyConns = new Vector<WebSocket>();
						synchronized( socketsToUsers ) {
							for(Map.Entry<WebSocket, User> entry : socketsToUsers.entrySet()) {
								if(entry.getValue().isReady){
									readyConns.add(entry.getKey());
								}
							}
							
							if(readyConns.size() == 2) {
								// Make Game
								for(int k = 1; k <= readyConns.size(); ++k){
									JSONObject response = new JSONObject();
									System.out.println(k);
									response.put("player", k);
									readyConns.get(k-1).send(response.toString());
								}
								
								Thread.sleep(1000);
								
								User one = socketsToUsers.get(readyConns.get(0));
								User two = socketsToUsers.get(readyConns.get(1));
								game = new Game(one.username, two.username, s, one.studentOrProf, two.studentOrProf);
								System.out.println("STARTING GAME: " + one.username + " " + two.username);
								String gameString = game.ConvertGameToJSON();
								
								for(WebSocket conn : readyConns) {
									conn.send(gameString);
								}
								break;
							}
						}
					}
				}
				catch (SQLException sqle) {
					sqle.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		});
		
		readyChecker.start();
	}
}