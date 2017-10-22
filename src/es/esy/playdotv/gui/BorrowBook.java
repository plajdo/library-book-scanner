package es.esy.playdotv.gui;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.SwingConstants;

import es.esy.playdotv.event.DDEventListener;
import es.esy.playdotv.event.DataDialogEvent;
import es.esy.playdotv.event.DataDialogEventOperation;

import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

public class BorrowBook extends JInternalFrame {
	
	static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	public BorrowBook(JDesktopPane desktopPane) {
		setTitle("Vypo\u017Ei\u010Dia\u0165 knihu");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][grow][]"));
		
		JLabel lblisloKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblisloKnihy, "cell 0 0,alignx trailing,aligny center");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx,aligny center");
		textField.setColumns(10);
		
		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		getContentPane().add(lblNzovKnihy, "cell 0 1,alignx trailing,aligny center");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		getContentPane().add(textField_1, "cell 1 1,growx,aligny center");
		textField_1.setColumns(10);
		
		JLabel lblAutorKnihy = new JLabel("Autor knihy:");
		getContentPane().add(lblAutorKnihy, "cell 0 2,alignx trailing,aligny center");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		getContentPane().add(textField_2, "cell 1 2,growx,aligny center");
		textField_2.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 3 2 1,grow");
		
		JLabel lblIdiaka = new JLabel("ID osoby:");
		getContentPane().add(lblIdiaka, "cell 0 4,alignx trailing,aligny center");
		
		textField_3 = new JTextField();
		getContentPane().add(textField_3, "cell 1 4,growx,aligny center");
		textField_3.setColumns(10);
		
		JLabel lblMeno = new JLabel("Meno:");
		getContentPane().add(lblMeno, "cell 0 5,alignx trailing,aligny center");
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		getContentPane().add(textField_4, "cell 1 5,growx,aligny center");
		textField_4.setColumns(10);
		
		JLabel lblTrieda = new JLabel("Trieda:");
		getContentPane().add(lblTrieda, "cell 0 6,alignx trailing,aligny center");
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		getContentPane().add(textField_5, "cell 1 6,growx,aligny center");
		textField_5.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		getContentPane().add(separator_1, "cell 0 7 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 9 2 1,grow");
		panel.setLayout(new MigLayout("", "[110.00][][110.00]", "[][]"));
		
		JButton btnSkenovaKnihu = new JButton("Nasn\u00EDma\u0165 knihu");
		btnSkenovaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookScanner bs = new BookScanner();
				bs.addDataDialogListener(new DDEventListener(){
					@Override
					public void handleDataDialogEvent(DataDialogEvent evt){
						if(evt.getOperation() == DataDialogEventOperation.EVENT_SUCCEEDED){
							textField.setText(bs.getData()[0]);
							textField_1.setText(bs.getData()[1]);
							textField_2.setText(bs.getData()[2]);
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_FAILED){
							textField.setText("Chyba");
							textField_1.setText("Chyba");
							textField_2.setText("Chyba");
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_CANCELLED){
							textField.setText("Zrušené");
							textField_1.setText("Zrušené");
							textField_2.setText("Zrušené");
						}
					}
				});
				desktopPane.add(bs);
				try {
					bs.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSkenovaKnihu, "cell 0 0,growx,aligny center");
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_2, "cell 1 0 1 2,grow");
		
		JButton btnPotvrdi = new JButton("Potvrdi\u0165");
		panel.add(btnPotvrdi, "cell 2 0,growx,aligny center");
		
		JButton btnSkenovaOsobu = new JButton("Nasn\u00EDma\u0165 osobu");
		btnSkenovaOsobu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonScanner ps = new PersonScanner();
				ps.addDataDialogListener(new DDEventListener(){
					@Override
					public void handleDataDialogEvent(DataDialogEvent evt){
						if(evt.getOperation() == DataDialogEventOperation.EVENT_SUCCEEDED){
							textField_3.setText(ps.getData()[0]);
							textField_4.setText(ps.getData()[1]);
							textField_5.setText(ps.getData()[2]);
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_FAILED){
							textField_3.setText("Chyba");
							textField_4.setText("Chyba");
							textField_5.setText("Chyba");
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_CANCELLED){
							textField_3.setText("Zrušené");
							textField_4.setText("Zrušené");
							textField_5.setText("Zrušené");
						}
					}
				});
				desktopPane.add(ps);
				try {
					ps.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSkenovaOsobu, "cell 0 1,growx,aligny center");
		
		JButton btnZrui = new JButton("Zru\u0161i\u0165");
		btnZrui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		panel.add(btnZrui, "cell 2 1,growx,aligny center");
		
		setVisible(true);

	}

}
