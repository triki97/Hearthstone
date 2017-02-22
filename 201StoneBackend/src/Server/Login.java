package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import com.mysql.jdbc.Driver;

public class Login extends ClientSocketReader {
	public Connection con;
	public final static String selectName = "SELECT * FROM Users WHERE Username=?";
	public final static String selectUserNameAndPass = "Select * FROM Users WHERE Username=? AND Password=?";
	public final static String createUser = "INSERT INTO Users(Username,Password) Values(?,?)";
	public Login() {
		try {
			new Driver();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject parseMessage(String clientMessage) {
		String[] tokens = clientMessage.split(Constants.CLIENT_DELIMETER);
		switch(tokens[0]){
		case "Login":
			if(tokens.length != 3){
				System.out.println("Login Error: Not all fields have been filled");
				return successResponse(false);
			}
			return successResponseWithUser(LogIn(tokens[1], tokens[2]), tokens[1]);
			
		case "CreateAccount":
			if(tokens.length != 3){
				System.out.println("Login Error: Not all fields have been filled");
				return successResponse(false);
			}
			return successResponseWithUser(CreateAccount(tokens[1], tokens[2]), tokens[1]);
		}
		
		return successResponse(false);
	}
	
	public boolean CreateAccount(String username, String password) {
		try {
			
			PreparedStatement ps = con.prepareStatement(selectName);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			if(!result.next()) {
				addToDatabase(username, password);
				return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;			

	}
	
	public void addToDatabase(String username, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(createUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
}
	
	public boolean LogIn(String username, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUserNameAndPass);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void connect() {
		try {
			con = DriverManager.getConnection(Constants.SQL_CONNECTION, Constants.SQL_USERNAME, Constants.SQL_PASSWORD);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			con.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
