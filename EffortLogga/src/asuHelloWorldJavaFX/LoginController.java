package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.control.Button;

public class LoginController {
	@FXML
	private Button loginButton;
	@FXML
	private Button signUpButton;
	
	@FXML
	private Button ExitButton;
	
	@FXML
	private void exitButtonPressed(ActionEvent e) {
		Stage stage = (Stage) ExitButton.getScene().getWindow();
		stage.close();
		
	}
	
	
	

}
