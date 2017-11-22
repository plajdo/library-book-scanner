package es.esy.playdotv.gui;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;

import es.esy.playdotv.datareader.Reader;

class RefreshImage extends Thread{
	
	private volatile boolean running = true;
	private volatile BufferedImage webcamImage;
	private volatile JLabel lblObrzok;
	private volatile ImageIcon ic;
	private volatile Webcam webcam;
	private volatile JTextField textField;
	private volatile JTextField textField_1;
	private volatile JTextField textField_2;
	
	RefreshImage(BufferedImage b, JLabel l, ImageIcon i, Webcam w, JTextField t1, JTextField t2, JTextField t3){
		this.webcamImage = b;
		this.lblObrzok = l;
		this.ic = i;
		this.webcam = w;
		this.textField = t1;
		this.textField_1 = t2;
		this.textField_2 = t3;
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
				String[] splitText = x.split(";");
				textField.setText(splitText[0]);
				textField_1.setText(splitText[1]);
				textField_2.setText(splitText[2]);
			}
			try {
				Thread.sleep(42);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
