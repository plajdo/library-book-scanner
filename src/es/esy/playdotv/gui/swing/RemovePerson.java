package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemovePerson extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public RemovePerson() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Odstr\u00E1ni\u0165 osobu");
		setBounds(100, 100, 500, 112);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow][pref!][]"));

		JLabel lblIdKnihy = new JLabel("ID osoby:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 2 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[]"));

		JButton btnPotvrdiAPrida = new JButton("Odstr\u00E1ni\u0165 osobu z datab\u00E1zy");
		btnPotvrdiAPrida.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				/*
				 * TODO: Kód na vymazanie týpeèkov z databázy
				 */
				
			}
			
		});
		panel.add(btnPotvrdiAPrida, "flowy,cell 0 0,growx");
		
		setVisible(true);

	}
	
}
