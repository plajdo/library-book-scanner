package es.esy.playdotv.gui;

import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.sarxos.webcam.Webcam;

import es.esy.playdotv.datareader.Reader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookScanner extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	volatile BufferedImage webcamImage;
	private boolean isClosing = false;
	
	boolean isClosing(){
		return isClosing;
	}
	
	public BookScanner() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIcon ic = new ImageIcon(webcam.getImage());
		
		setTitle("Nasn\u00EDma\u0165 knihu");
		setBounds(100, 100, 450, 330);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		
		JLabel lblSkener = new JLabel("Skener:");
		getContentPane().add(lblSkener, "cell 0 0,alignx trailing,aligny center");
		
		JLabel lblInsertSkenerHere = new JLabel(ic);
		lblInsertSkenerHere.setMaximumSize(new Dimension(200, 150));
		getContentPane().add(lblInsertSkenerHere, "cell 1 0");
		
		JLabel lblsloKnihy = new JLabel("\u010C\u00EDslo knihy:");
		getContentPane().add(lblsloKnihy, "cell 0 1,alignx trailing,aligny center");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 1,growx");
		textField.setColumns(10);
		
		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		getContentPane().add(lblNzovKnihy, "cell 0 2,alignx trailing,aligny center");
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 1 2,growx");
		textField_1.setColumns(10);
		
		JLabel lblAutorKnihy = new JLabel("Autor knihy:");
		getContentPane().add(lblAutorKnihy, "cell 0 3,alignx trailing,aligny center");
		
		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 1 3,growx");
		textField_2.setColumns(10);
		
		RefreshThread r = new RefreshThread(webcamImage, lblInsertSkenerHere, ic, webcam, textField, textField_1, textField_2);
		Thread t = new Thread(r);
		t.start();
		
		JButton btnZrui = new JButton("Zru\u0161i\u0165");
		btnZrui.setPreferredSize(new Dimension(75, 25));
		btnZrui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					r.terminate();
					t.join();
					dispose();
					isClosing = true;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					dispose();
					isClosing = true;
				}
			}
		});
		
		JButton btnPotvrdi = new JButton("Potvrdi\u0165");
		btnPotvrdi.setPreferredSize(new Dimension(75, 25));
		btnPotvrdi.setMinimumSize(new Dimension(75, 25));
		btnPotvrdi.setMaximumSize(new Dimension(75, 25));
		getContentPane().add(btnPotvrdi, "cell 0 5,growx,aligny center");
		btnZrui.setMinimumSize(new Dimension(75, 25));
		btnZrui.setMaximumSize(new Dimension(75, 25));
		getContentPane().add(btnZrui, "cell 1 5,aligny center");
		
		setVisible(true);

	}

}

class RefreshThread extends Thread{
	
	private volatile boolean running = true;
	private volatile BufferedImage webcamImage;
	private volatile JLabel lblObrzok;
	private volatile ImageIcon ic;
	private volatile Webcam webcam;
	private volatile JTextField textField;
	private volatile JTextField textField_1;
	private volatile JTextField textField_2;
	
	RefreshThread(BufferedImage b, JLabel l, ImageIcon i, Webcam w, JTextField t1, JTextField t2, JTextField t3){
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
