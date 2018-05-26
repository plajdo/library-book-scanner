package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.database.LBSDatabase;

import io.github.shardbytes.lbs.event.TableRefreshEvent;
import io.github.shardbytes.lbs.event.TableRefreshEventListener;
import io.github.shardbytes.lbs.event.TableRefreshEventOperation;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RemovePerson extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	public RemovePerson() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Odstr\u00E1ni\u0165 \u010Ditate\u013Ea");
		setBounds(100, 100, 300, 125);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow][pref!][]"));

		JLabel lblIdKnihy = new JLabel("ID \u010Ditate\u013Ea:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 2 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[]"));

		JButton btnPotvrdiAPrida = new JButton("Odstr\u00E1ni\u0165");
		btnPotvrdiAPrida.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(textField.getText().length() > 0){
					if(db.persons.containsKey(textField.getText())){
						if(db.persons.get(textField.getText()).getNumBookCount() == 0){
							db.persons.remove(textField.getText());
							dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
							dispose();
						}else{
							JOptionPane.showMessageDialog(null, "\u010Citate\u013E m\u00E1 " + (db.persons.get(textField.getText()).getNumBookCount() == 1 ? "vypo\u017Ei\u010Dan\u00FA knihu." : "vypo\u017Ei\u010Dan\u00E9 viacer\u00E9 knihy."), "Chyba", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "\u010Citate\u013E neexistuje v datab\u00E1ze.", "Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Zadajte ID \u010Ditate\u013Ea.", "Chyba", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		panel.add(btnPotvrdiAPrida, "flowy,cell 0 0,growx");
		
		setVisible(true);

	}
	
	public static void addDataDialogListener(TableRefreshEventListener trel){
		if(!listeners.contains(trel)){
			listeners.add(trel);
		}
	}
	
	public static void removeDataDialogListener(TableRefreshEventListener trel){
		listeners.remove(trel);
	}
	
	public static void dispatchTableRefreshEvent(TableRefreshEvent evt){
		for(TableRefreshEventListener trel: listeners){
			trel.handleTableRefreshEvent(evt);
			
		}
		
	}
	
}
