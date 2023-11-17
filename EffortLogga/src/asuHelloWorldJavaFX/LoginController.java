package asuHelloWorldJavaFX;
//FUNCTIONALITY FOR LOGIN PAGE
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class LoginController {
	@FXML
	private Button LoginButton;
	@FXML
	private Button signUpButton;
	
	@FXML
	private Button ExitButton;
	@FXML
	private Label SystemMessage;
	@FXML
	private TextField UsernameTextInput;
	@FXML
	private TextField PasswordTextInput;
	@FXML
	private void exitButtonPressed(ActionEvent e) {
		Stage stage = (Stage) ExitButton.getScene().getWindow();
		stage.close();
		
	}
	@FXML	
	private void LogInButtonPressed(ActionEvent e) {
		
		if(!UsernameTextInput.getText().isBlank() && !PasswordTextInput.getText().isBlank()) {

			checkCredentials();
		}else {
			
			SystemMessage.setText("Empty Field!");
			SystemMessage.setOpacity(1);
		}
	}
	public void checkCredentials() {
		DatabaseConnection connection = new DatabaseConnection();
		Connection connector = connection.getConnection();
		
		String loginQuery = "SELECT count(1) FROM userAccounts WHERE username = '"+ UsernameTextInput.getText() + "' AND password = '" + PasswordTextInput.getText() +"'";
		try {
			Statement x = connector.createStatement();
			ResultSet fetchResult = x.executeQuery(loginQuery);
			
			 while(fetchResult.next()) {
				 System.out.println(fetchResult.getInt(1));
		            if (fetchResult.getInt(1) == 1) {
		                SystemMessage.setText("WELL DONE!");
		                SystemMessage.setOpacity(1);
		                
		                int role = getRole();
		            } else {
		                SystemMessage.setText("WRONG!");
		                SystemMessage.setOpacity(1);
		            }
			 }
			 connector.close();
		            
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private int getRole() {
		String roleQuery = "";
		return 0;
	}
	
	

}
