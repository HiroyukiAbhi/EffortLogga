// Import statements for JavaFX and database operations
package asuHelloWorldJavaFX;

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

// Controller class for the login page
public class LoginController {
    // Variables for window dragging
    private double xOffset = 0;
    private double yOffset = 0;
    
    // JavaFX components injected from the FXML file
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

    // Variables to store username and password
    public String username, password;

    // Initialization method for the controller
    @FXML
    public void initialize() {
        // Initialize the controller, establish database connection
        connection = new DatabaseConnection();
    }

    // Event handler for exit button
    @FXML
    private void exitButtonPressed(ActionEvent e) {
        // Handle exit button click, close the application window
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

    // Event handler for login button
    @FXML
    private void LogInButtonPressed(ActionEvent e) {
        // Check if username and password fields are not empty before proceeding
        if (!UsernameTextInput.getText().isBlank() && !PasswordTextInput.getText().isBlank()) {
            // Validate user credentials
            checkCredentials(e);
        } else {
            // Display a message if either username or password is empty
            SystemMessage.setText("Empty Field!");
            SystemMessage.setOpacity(1);
        }
    }

    // Event handler for sign-up button
    @FXML
    private void SignUpButtonPressed(ActionEvent e) {
        // Load the sign-up window when the sign-up button is clicked
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            root = loader.load();
            // Enable window dragging functionality
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

    // Method to check user credentials when the login button is pressed
    public void checkCredentials(ActionEvent e) {
        // Validate input to prevent SQL injection
        InputValidator x1 = new InputValidator(PasswordTextInput.getText());
        String password = PasswordTextInput.getText();
        String username = UsernameTextInput.getText();
        if (!x1.injectionCheck(password) && !x1.injectionCheck(username)) {
            // Display a success message for valid credentials
            SystemMessage.setText("Authentication Successful :)");
            SystemMessage.setOpacity(1);

            try {
                // Establish database connection
                Connection connector = connection.getConnection();

                // Query to check login credentials in the userAccounts table
                String loginQuery = "SELECT count(1) FROM userAccounts WHERE username = '"
                        + UsernameTextInput.getText() + "' AND password = '"
                        + Encryption.hashPassword(PasswordTextInput.getText()) + "'";

                try {
                    Statement x = connector.createStatement();
                    ResultSet fetchResult = x.executeQuery(loginQuery);

                    while (fetchResult.next()) {
                        if (fetchResult.getInt(1) == 1) {
                            // User authentication successful, retrieve user role
                            String roleQuery = "SELECT roleSpecification FROM userAccounts WHERE username = '"
                                    + UsernameTextInput.getText() + "'";
                            fetchResult.close();
                            fetchResult = x.executeQuery(roleQuery);
                            while (fetchResult.next()) {
                                if (fetchResult.getInt(1) == 1) {
                                    // Display user role as Supervisor
                                    SystemMessage.setText("Supervisor!");
                                } else {
                                    // Display user role as Employee
                                    SystemMessage.setText("Employee!");
                                    // Store username and password for future use
                                    this.username = UsernameTextInput.getText();
                                    this.password = PasswordTextInput.getText();
                                    // Navigate to the employee page
                                    sendToEmployeePage(e);
                                }
                            }
                        } else {
                            // Display a message for incorrect credentials
                            SystemMessage.setText("Incorrect Username or Password!");
                            SystemMessage.setOpacity(1);
                        }
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // Close the database connection
                connector.close();

            } catch (Exception e2) {
                e2.printStackTrace();
            }

        } else {
            // Display a message for SQL injection detection
            SystemMessage.setText("SQL INJECTION DETECTED! >:O");
            SystemMessage.setOpacity(1);
        }
    }

    // Method to navigate to the employee page
    public void sendToEmployeePage(ActionEvent e) {
        try {
            // Load the EmployeePage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePage.fxml"));
            root = loader.load();
            // Get the controller associated with the EmployeePage.fxml
            EmployeeLandingPageController eController = loader.getController();

            // Set the username in the EmployeeLandingPageController
            eController.setUsername(this.username);

            // Print a message related to the DatabaseConnection
            connection.toStringISG();
           

            // Enable window dragging functionality
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            // Set the scene and display the window
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception except) {
            // Print any exceptions that occur during the process
            except.printStackTrace();
        }
    }
}
