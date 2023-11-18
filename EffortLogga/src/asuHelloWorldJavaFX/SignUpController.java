package asuHelloWorldJavaFX;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class SignUpController {
	private Stage stage;
	private Scene scene;
	public Parent root;
	
	
	@FXML
	TextField SignUpUsernameText;
	@FXML
	TextField SignUpPasswordText;
	@FXML
	Button SignUpButton;
	@FXML
	Button SignUpBackButton;
	@FXML
	private void SignUpButtonPressed(ActionEvent e) {
		
	
	}
	
	@FXML
	private void SignInBackButtonPressed(ActionEvent e) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));	
			root = loader.load();	
			stage = (Stage)((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		}catch(Exception signUpException){
			signUpException.printStackTrace();
		}
		
	}
	
}
