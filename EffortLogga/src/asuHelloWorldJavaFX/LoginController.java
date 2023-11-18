package asuHelloWorldJavaFX;
//FUNCTIONALITY FOR LOGIN PAGE
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;




public class LoginController {
	
	
	private Stage stage;
	private Scene scene;
	
	@FXML
	private BorderPane borderpane;
	@FXML
	public Parent root;
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
	@FXML
	private void SignUpButtonPressed(ActionEvent e) {
		//Static desgin of creating a signup window
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));	
			root = loader.load();	
			stage = (Stage)((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch(Exception signUpException){
			signUpException.printStackTrace();
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
		            if (fetchResult.getInt(1) == 1) {
		                SystemMessage.setOpacity(1);
		                String roleQuery = "SELECT roleSpecification FROM userAccounts WHERE username = '" + UsernameTextInput.getText() + "'";
		                fetchResult.close();
		                fetchResult = x.executeQuery(roleQuery);
		                while(fetchResult.next()) {
		                	if(fetchResult.getInt(1) == 1) {
		                		SystemMessage.setText("Supervisor!");
		                		
		                	}else {
		                		SystemMessage.setText("Employee!");
		                	}
		                }
		                
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

	
	

}
