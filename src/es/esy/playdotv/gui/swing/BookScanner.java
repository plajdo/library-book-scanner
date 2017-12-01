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
	private JTextField textField_1;
	private JTextField textField_2;
	volatile BufferedImage webcamImage;
	private String[] data = {"", "", ""};
	
	List<DDEventListener> listeners = new ArrayList<>();
	
	String[] getData(){
		return data;
	}
	
	public BookScanner() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIcon ic = new ImageIcon(webcam.getImage());
		
		setTitle("Nasn\u00EDma\u0165 knihu");
		setBounds(100, 100, 450, 330);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][grow][][]"));
		
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
		
		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		getContentPane().add(lblNzovKnihy, "cell 0 2,alignx trailing,aligny center");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		getContentPane().add(textField_1, "cell 1 2,growx");
		textField_1.setColumns(10);
		
		JLabel lblAutorKnihy = new JLabel("Autor knihy:");
		getContentPane().add(lblAutorKnihy, "cell 0 3,alignx trailing,aligny center");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		getContentPane().add(textField_2, "cell 1 3,growx");
		textField_2.setColumns(10);
		
		RefreshImage r = new RefreshImage(webcamImage, lblInsertSkenerHere, ic, webcam, textField, textField_1, textField_2);
		Thread t = new Thread(r);
		t.start();
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 5 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 6 2 1,grow");
		panel.setLayout(new MigLayout("", "[75px][75px]", "[25px]"));
		
		JButton btnPotvrdi = new JButton("Potvrdi\u0165");
		btnPotvrdi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(textField.getText().length() > 0 && textField_1.getText().length() > 0 && textField_2.getText().length() > 0){
						data[0] = textField.getText();
						data[1] = textField_1.getText();
						data[2] = textField_2.getText();
						dispatchDataDialogEvent(new DataDialogEvent(this, DataDialogEventOperation.EVENT_SUCCEEDED));
					}else{
						data[0] = "";
						data[1] = "";
						data[2] = "";
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
