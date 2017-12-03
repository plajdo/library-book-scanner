package es.esy.playdotv.gui.fx;

import javafx.embed.swing.SwingFXUtils;

public class FXRefreshImage extends Thread{
	
	private boolean running = true;
	
	public void terminate(){
		running = false;
	}
	
	@Override
	public void run(){
		while(running){
			FXAppController.imgView.setImage(SwingFXUtils.toFXImage(FXWebcam.getCam().getImage(), null));
		}
		
	}
	
}
