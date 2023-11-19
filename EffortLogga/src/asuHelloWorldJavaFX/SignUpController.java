package asuHelloWorldJavaFX;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
	private Stage stage;
	private Scene scene;
	public Parent root;


	@FXML
	Label SignUpMessage;
	@FXML
	TextField SignUpUsernameText;
	@FXML
	TextField SignUpPasswordText;
	@FXML
	Button SignUpButton;
	@FXML
	Button SignUpBackButton;

	@FXML
	private void SignUpButtonisPressed(ActionEvent e) {
		if(Password.isPasswordValid(SignUpPasswordText.getText()))
		{

			try {
				DatabaseConnection connection = new DatabaseConnection();
				Connection connector = connection.getConnection();
				String loginQuery = "SELECT count(1) FROM userAccounts WHERE username = '"+ SignUpUsernameText.getText() + "'";
				Statement x = connector.createStatement();
				ResultSet fetchResult = x.executeQuery(loginQuery);
				while(fetchResult.next()) {

					if (fetchResult.getInt(1) == 1) {
						SignUpMessage.setText("User Already Exists!");
						SignUpMessage.setOpacity(1);

					} else {
						String insert = "INSERT INTO userAccounts (username, password, roleSpecification)" + "VALUES ('" + SignUpUsernameText.getText() + "', '" + Encryption.hashPassword(SignUpPasswordText.getText()) + "', 0);";

						PreparedStatement preparedStatement = connector.prepareStatement(insert);
						//fetchResult = x.executeQuery(insert);
						int rowsAffected = preparedStatement.executeUpdate();
						if(rowsAffected > 0) {
							SignUpMessage.setText("User Created!");
							SignUpMessage.setOpacity(1);
							preparedStatement.close();
						}
					}
				}

				fetchResult.close();
				connector.close();
			} catch(Exception e1) {
				e1.printStackTrace();

			}
		}
		else
		{
			SignUpMessage.setText(Password.getMessage());
			SignUpMessage.setOpacity(1);
		}

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
