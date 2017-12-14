package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.event.TableRefreshEventOperation;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RemoveBook extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public RemoveBook() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Odstr\u00E1ni\u0165 knihu");
		setBounds(100, 100, 500, 112);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow][pref!][]"));

		JLabel lblIdKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 2 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow]", "[]"));

		JButton btnPotvrdiAPrida = new JButton("Odstr\u00E1ni\u0165 knihu z datab\u00E1zy");
		btnPotvrdiAPrida.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(textField.getText().length() > 0){
					if(db.books.containsKey(textField.getText())){
						if(db.books.get(textField.getText()).getTakerID().isEmpty()){
							db.books.remove(textField.getText());
							dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
							dispose();
						}else{
							JOptionPane.showMessageDialog(null, "Kniha je vypožièaná osobou " + db.books.get(textField.getText()).getTakerID() + ".", "Chyba", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Kniha neexistuje v databáze.", "Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Zadajte ID knihy.", "Chyba", JOptionPane.ERROR_MESSAGE);
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
