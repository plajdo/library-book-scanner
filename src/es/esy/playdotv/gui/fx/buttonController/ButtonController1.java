package es.esy.playdotv.gui.fx.buttonController;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ButtonController1 {
	
	@FXML JFXButton button;
	
	@FXML protected void handlePrint(ActionEvent evt){
		System.out.println("xxx");
	}
	
}
