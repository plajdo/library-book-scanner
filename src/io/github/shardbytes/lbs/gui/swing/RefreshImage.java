package io.github.shardbytes.lbs.gui.swing;

import com.github.sarxos.webcam.Webcam;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.datareader.Reader;

import javax.swing.*;
import java.awt.image.BufferedImage;

class RefreshImage extends Thread{
	
	private volatile boolean running = true;
	private volatile BufferedImage webcamImage;
	private volatile JLabel lblObrzok;
	private volatile ImageIcon ic;
	private volatile Webcam webcam;
	private volatile JTextField textField;
	private long sleepTime;
	
	private static byte inUse = 0;
	
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
		inUse++;
		
		while(running){
			sleepTime = calculateSleepFromFps(webcam.getFPS());
			webcamImage = webcam.getImage();
			redrawImage(lblObrzok, webcamImage, ic);
			
			x = Reader.readQR(webcamImage);
			if(!(x == null)){
				textField.setText(x);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		inUse--;
		
		if(!Load.webcamOptimise){
			if(inUse == 0){
				webcam.close();
			}
			
		}
		
	}
	
	private long calculateSleepFromFps(double fps){
		long fps_l = (long)Math.floor(fps);
		return 1000L / fps_l;
	}
	
}
