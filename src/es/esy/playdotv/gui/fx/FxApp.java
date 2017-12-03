package es.esy.playdotv.gui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxApp extends Application{

	@Override
	public void start(Stage stage) throws Exception{
		setUserAgentStylesheet(null);
		
		Parent root = FXMLLoader.load(FxApp.class.getResource("menu.fxml"));
		
		Scene scene = new Scene(root, 300, 275);
		
		stage.setTitle("FXML");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void view(){
		launch();
	}
	
}
