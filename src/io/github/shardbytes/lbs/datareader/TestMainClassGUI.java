package io.github.shardbytes.lbs.datareader;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.sarxos.webcam.Webcam;

import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import javax.swing.JTextField;

public class TestMainClassGUI {

	private JFrame frame;
	
	volatile BufferedImage webcamImage;
	private JTextField textField;
	
	public static void openScanner() {
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
	
	public TestMainClassGUI() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		
		webcamImage = webcam.getImage();
		
		initialize(webcam);
	}
	
	private void initialize(Webcam webcam) {
		
		ImageIcon ic = new ImageIcon(webcam.getImage());
		
		frame = new JFrame();
		frame.setBounds(100, 100, 670, 555);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[]", "[][]"));
		
		JLabel lblObrzok = new JLabel(ic);
		lblObrzok.setMinimumSize(new Dimension(640, 480));
		lblObrzok.setMaximumSize(new Dimension(640, 480));
		frame.getContentPane().add(lblObrzok, "cell 0 0,grow");
		
		JLabel lblOutput = new JLabel("Output: ");
		frame.getContentPane().add(lblOutput, "flowx,cell 0 1");
		
		textField = new JTextField();
		frame.getContentPane().add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		
		frame.setVisible(true);
		
		RefreshThread r = new RefreshThread(webcamImage, lblObrzok, ic, webcam, textField);
		Thread t = new Thread(r);
		t.start();
		
		WindowListener exitListener = new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e){
				try {
					r.terminate();
					t.join();
					System.exit(0);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
			
		};
		
		frame.addWindowListener(exitListener);
		
	}

}

class RefreshThread extends Thread{
	
	private volatile boolean running = true;
	private volatile BufferedImage webcamImage;
	private volatile JLabel lblObrzok;
	private volatile ImageIcon ic;
	private volatile Webcam webcam;
	private volatile JTextField textField;
	
	RefreshThread(BufferedImage b, JLabel l, ImageIcon i, Webcam w, JTextField t){
		this.webcamImage = b;
		this.lblObrzok = l;
		this.ic = i;
		this.webcam = w;
		this.textField = t;
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
