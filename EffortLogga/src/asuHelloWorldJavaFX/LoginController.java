package asuHelloWorldJavaFX;
//FUNCTIONALITY FOR LOGIN PAGE

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
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
import java.lang.String;

public class LoginController {
	private double xOffset = 0;
    private double yOffset = 0;
	private Stage stage;
	private Scene scene;
	public DatabaseConnection connection;
	@FXML
	Label EffortLoggerTitle;
	@FXML
	Label EffortLoggerDescription;
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
	public void initialize() {
     //Add more here for when the window is initialized   
		
		connection = new DatabaseConnection();
    }
	@FXML
	private void exitButtonPressed(ActionEvent e) {
		Stage stage = (Stage) ExitButton.getScene().getWindow();
		stage.close();

	}

	@FXML
	private void LogInButtonPressed(ActionEvent e) {
		
		if (!UsernameTextInput.getText().isBlank() && !PasswordTextInput.getText().isBlank()) {
			checkCredentials(e);
			
		} else {

			SystemMessage.setText("Empty Field!");
			SystemMessage.setOpacity(1);
		}
	}

	@FXML
	private void SignUpButtonPressed(ActionEvent e) {
		// Static desgin of creating a signup window
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
			root = loader.load();
			root.setOnMousePressed(event -> {
	            xOffset = event.getSceneX();
	            yOffset = event.getSceneY();
	        });
	        root.setOnMouseDragged(event -> {
	        	stage.setX(event.getScreenX() - xOffset);
	        	stage.setY(event.getScreenY() - yOffset);
	        });
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception signUpException) {
			signUpException.printStackTrace();
		}
	}

	public void checkCredentials(ActionEvent e) {
		
		InputValidator x1 = new InputValidator(PasswordTextInput.getText());
		String password = PasswordTextInput.getText();
		String username = UsernameTextInput.getText();
		if (!x1.injectionCheck(password) && !x1.injectionCheck(username)) {
			SystemMessage.setText("nice :)");
			SystemMessage.setOpacity(1);

			try {
				
				Connection connector = connection.getConnection();

				String loginQuery = "SELECT count(1) FROM userAccounts WHERE username = '" + UsernameTextInput.getText()
						+ "' AND password = '" + Encryption.hashPassword(PasswordTextInput.getText()) + "'";

				try {
					Statement x = connector.createStatement();
					ResultSet fetchResult = x.executeQuery(loginQuery);

					while (fetchResult.next()) {
						if (fetchResult.getInt(1) == 1) {
							SystemMessage.setOpacity(1);
							String roleQuery = "SELECT roleSpecification FROM userAccounts WHERE username = '"
									+ UsernameTextInput.getText() + "'";
							fetchResult.close();
							fetchResult = x.executeQuery(roleQuery);
							while (fetchResult.next()) {
								if (fetchResult.getInt(1) == 1) {
									SystemMessage.setText("Supervisor!");
								} else {
									SystemMessage.setText("Employee!");
									sendToEmployeePage(e);
								}
							}
						} else {
							SystemMessage.setText("WRONG!");
							SystemMessage.setOpacity(1);
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				connector.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		} else {
			SystemMessage.setText("SQL INJECTION DETECTED! >:O");
			SystemMessage.setOpacity(1);
		}
	}
	
	public void sendToEmployeePage(ActionEvent e) {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePage.fxml"));
		root = loader.load();
		EmployeeLandingPageController eController = loader.getController();
		connection.toStringISG();
		eController.setConnection(connection);
		
		root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
        	stage.setX(event.getScreenX() - xOffset);
        	stage.setY(event.getScreenY() - yOffset);
        });
        
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		}catch(Exception except) {
			except.printStackTrace();
		}
	}

}
