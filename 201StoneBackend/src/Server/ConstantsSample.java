package Server;

public class ConstantsSample {
	public static final String HOST_NAME = "localhost";
	public static final String SQL_USERNAME = "root";
	public static final String SQL_PASSWORD = "";
	public static final String DB_NAME = "201Stone";
	
	public static final String SQL_CONNECTION = "jdbc:mysql://"+ HOST_NAME
												+":3306/"+ DB_NAME
			  									+ "?user="+ SQL_USERNAME
			  									+ "&password=" + SQL_PASSWORD;
	
	public static final String CLIENT_DELIMETER = " ";
}
