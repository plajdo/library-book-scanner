package es.esy.playdotv.datareader;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;

import net.miginfocom.swing.MigLayout;

public class TestMainClassGUI {

	private JFrame frame;
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					TestMainClassGUI window = new TestMainClassGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	public TestMainClassGUI() {
		initialize();
	}
	
	private void initialize() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[]", "[]"));
		
		JLabel lblObrzok = new ImageLabel(new ImageIcon(webcam.getImage()));
		frame.getContentPane().add(lblObrzok, "cell 0 0,grow");
		
		frame.setVisible(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					lblObrzok.setIcon(new ImageIcon(webcam.getImage()));
					lblObrzok.repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		}).start();
		
	}

}
