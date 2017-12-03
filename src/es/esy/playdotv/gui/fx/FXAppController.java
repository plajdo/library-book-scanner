package es.esy.playdotv.gui.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class FXAppController{
	
	@FXML static ImageView imgView;
	
	@FXML
	private void startButtonAction(ActionEvent event){
		WebcamThreadHandler.createNewThread();
		
	}
	
	@FXML
	private void stopButtonAction(ActionEvent event){
		WebcamThreadHandler.stopThread();
		
	}
	
}
