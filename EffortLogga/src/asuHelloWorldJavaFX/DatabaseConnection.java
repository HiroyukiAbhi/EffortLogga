package asuHelloWorldJavaFX;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	public Connection connection;
	
	
	
	public Connection getConnection() {
		String dbName = "mydb";
		String username = "root";
		String dbpassword = "password";
		String url = "jdbc:mysql://localhost/" + dbName;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, dbpassword);
			

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
		
		
		
	}

}
