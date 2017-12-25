package es.esy.playdotv.gui.swing;

import com.github.sarxos.webcam.Webcam;
import es.esy.playdotv.event.DDEventListener;
import es.esy.playdotv.event.DataDialogEvent;
import es.esy.playdotv.event.DataDialogEventOperation;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BookScanner extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	volatile BufferedImage webcamImage;
	private String data = "";
	
	List<DDEventListener> listeners = new ArrayList<>();
	
	String getData(){
		return data;
	}
	
	public BookScanner() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIcon ic = new ImageIcon(webcam.getImage());
		
		setTitle("Nasn\u00EDma\u0165 knihu");
		setBounds(100, 100, 450, 260);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][grow][][]"));
		
		JLabel lblSkener = new JLabel("Skener:");
		getContentPane().add(lblSkener, "cell 0 0,alignx trailing,aligny center");
		
		JLabel lblInsertSkenerHere = new JLabel(ic);
		lblInsertSkenerHere.setMaximumSize(new Dimension(200, 150));
		getContentPane().add(lblInsertSkenerHere, "cell 1 0");
		
		JLabel lblsloKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblsloKnihy, "cell 0 1,alignx trailing,aligny center");
		
		textField = new JTextField();
		textField.setEditable(false);
		getContentPane().add(textField, "cell 1 1,growx");
		textField.setColumns(10);
		
		
		RefreshImage r = new RefreshImage(webcamImage, lblInsertSkenerHere, ic, webcam, textField);
		Thread t = new Thread(r);
		t.start();
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 3 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 4 2 1,grow");
		panel.setLayout(new MigLayout("", "[75px][75px]", "[25px]"));
		
		JButton btnPotvrdi = new JButton("Potvrdi\u0165");
		btnPotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(textField.getText().length() > 0){
						data = textField.getText();
						dispatchDataDialogEvent(new DataDialogEvent(this, DataDialogEventOperation.EVENT_SUCCEEDED));
					}else{
						data = "";
						dispatchDataDialogEvent(new DataDialogEvent(this, DataDialogEventOperation.EVENT_FAILED));
					}
					
					r.terminate();
					t.join();
					dispose();
				}catch(InterruptedException e1){
					e1.printStackTrace();
					dispose();
				}
			}
		});
		panel.add(btnPotvrdi, "cell 0 0,alignx left,aligny top");
		btnPotvrdi.setPreferredSize(new Dimension(75, 25));
		btnPotvrdi.setMinimumSize(new Dimension(75, 25));
		btnPotvrdi.setMaximumSize(new Dimension(75, 25));
		
		JButton btnZrui = new JButton("Zru\u0161i\u0165");
		panel.add(btnZrui, "cell 1 0,alignx left,aligny top");
		btnZrui.setPreferredSize(new Dimension(75, 25));
		btnZrui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					r.terminate();
					t.join();
					dispatchDataDialogEvent(new DataDialogEvent(this, DataDialogEventOperation.EVENT_CANCELLED));
					dispose();
				} catch(InterruptedException e1) {
					e1.printStackTrace();
					dispose();
				}
			}
		});
		btnZrui.setMinimumSize(new Dimension(75, 25));
		btnZrui.setMaximumSize(new Dimension(75, 25));
		
		setVisible(true);

	}
	
	public void addDataDialogListener(DDEventListener ddel){
		if(!listeners.contains(ddel)){
			listeners.add(ddel);
		}
	}
	
	public void removeDataDialogListener(DDEventListener ddel){
		listeners.remove(ddel);
	}
	
	public void dispatchDataDialogEvent(DataDialogEvent evt){
		for(DDEventListener ddl: listeners){
			ddl.handleDataDialogEvent(evt);
		}
	}

}
