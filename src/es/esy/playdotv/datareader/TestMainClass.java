package es.esy.playdotv.datareader;

import com.github.sarxos.webcam.Webcam;

public class TestMainClass {
	public static void main(String[] args) throws Exception{
		Webcam w = Webcam.getDefault();
		w.open();
		String x;
		while(true){
			x = Reader.readQR(w.getImage());
			if(!(x == null)){
				System.out.println(x);
				break;
			}
			Thread.sleep(34);
		}
		
	}
	
}
