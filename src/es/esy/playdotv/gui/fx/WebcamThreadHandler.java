package es.esy.playdotv.gui.fx;

public class WebcamThreadHandler {
	
	static Thread t;
	static FXRefreshImage frt;
	
	public static void createNewThread(){
		if(t == null && frt == null){
			t = new FXRefreshImage();
			frt = new FXRefreshImage();
			t.start();
			
		}
		
	}
	
	public static void stopThread(){
		if(t != null && frt != null){
			frt.terminate();
			t.interrupt();
			t = null;
			frt = null;
			
		}
		
	}
	
}
