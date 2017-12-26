package es.esy.playdotv.gui.swing;

import com.github.sarxos.webcam.Webcam;
import es.esy.playdotv.datareader.Reader;

import javax.swing.*;
import java.awt.image.BufferedImage;

class RefreshImage extends Thread{
	
	private volatile boolean running = true;
	private volatile BufferedImage webcamImage;
	private volatile JLabel lblObrzok;
	private volatile ImageIcon ic;
	private volatile Webcam webcam;
	private volatile JTextField textField;
	
	RefreshImage(BufferedImage b, JLabel l, ImageIcon i, Webcam w, JTextField t1){
		this.webcamImage = b;
		this.lblObrzok = l;
		this.ic = i;
		this.webcam = w;
		this.textField = t1;
	}
	
	public void terminate(){
		running = false;
	}
	
	private void redrawImage(JLabel l, BufferedImage i, ImageIcon c){
		c.setImage(i);
		l.setIcon(c);
		l.repaint();
		
	}
	
	@Override
	public void run(){
		String x;
		
		while(running){					
			webcamImage = webcam.getImage();
			redrawImage(lblObrzok, webcamImage, ic);
			
			x = Reader.readQR(webcamImage);
			if(!(x == null)){
				textField.setText(x);
			}
			try {
				Thread.sleep(42);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
