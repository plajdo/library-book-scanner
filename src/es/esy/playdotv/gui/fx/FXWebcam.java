package es.esy.playdotv.gui.fx;

import com.github.sarxos.webcam.Webcam;

public class FXWebcam {
	
	public static Webcam w;
	
	public static void startCam(){
		w = Webcam.getDefault();
		w.open();
	}
	
	public static Webcam getCam(){
		return w;
	}
	
	public static void stopCam(){
		w.close();
	}
	
}
