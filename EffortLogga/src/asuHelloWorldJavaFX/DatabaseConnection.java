package asuHelloWorldJavaFX;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	public Connection connection;
	
	
	
	public Connection getConnection() {
		//String dbName = "mydb"; //Enter DATABASE NAME
		String username = "avnadmin"; //Enter username 
		String dbpassword = "AVNS_Wqz6DbTKBRHQncwW46s";
		String url = "jdbc:mysql://mysql-effortlogger-intelligence2003-8456.a.aivencloud.com:14581/effortloggeruseraccounts?ssl-mode=REQUIRED";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, dbpassword);
			

			
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
		
		
		
	}
	public void toStringISG() {
		System.out.println("this was init");
		
	}

}
