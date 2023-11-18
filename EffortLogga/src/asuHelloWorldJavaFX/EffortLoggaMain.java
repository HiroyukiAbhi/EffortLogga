package asuHelloWorldJavaFX;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
 
public class EffortLoggaMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) {
        
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root, 520, 400);
			stage.initStyle(StageStyle.UNDECORATED);
			stage. setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			//Just incase that Login-FXML cant be located
			e.printStackTrace();
		}
		
    }
}