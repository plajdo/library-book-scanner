package es.esy.playdotv.gui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FXApp extends Application{
	
	static Stage stg;
	static Scene sc;
	
	@Override
	public void start(Stage stage) throws Exception{
		stg = stage;
		
		Parent root = FXMLLoader.load(FXApp.class.getResource("menu.fxml"));
		sc = new Scene(root);
		
		stg.setTitle("FXML");
		stg.setScene(sc);
		stg.show();
		
		FXWebcam.startCam();
		FXAppController.imgView = (ImageView)FXApp.sc.lookup("#WebcamImage");
	}
	
	public static void view(){
		launch();		
	}
	
}
