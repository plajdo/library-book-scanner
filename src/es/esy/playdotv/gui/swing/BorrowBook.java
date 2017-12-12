package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.event.DDEventListener;
import es.esy.playdotv.event.DataDialogEvent;
import es.esy.playdotv.event.DataDialogEventOperation;
import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.event.TableRefreshEventOperation;
import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;
import net.miginfocom.swing.MigLayout;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowBook extends JInternalFrame {
	
	static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	public BorrowBook(JDesktopPane desktopPane) {
		
		setTitle("Vypo\u017Ei\u010Dia\u0165 knihu");
		setBounds(100, 100, 460, 315);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][][grow][]"));
		
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
		
		JLabel lblOd = new JLabel("Od:");
		getContentPane().add(lblOd, "cell 0 7,alignx trailing");
		
		UtilDateModel m2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(m2);
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2);
		getContentPane().add(datePicker2, "flowx,cell 1 7,growx");
		
		JSeparator separator_1 = new JSeparator();
		getContentPane().add(separator_1, "cell 0 8 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 10 2 1,grow");
		panel.setLayout(new MigLayout("", "[110.00][][110.00]", "[][]"));
		
		JButton btnSkenovaKnihu = new JButton("Nasn\u00EDma\u0165 knihu");
		btnSkenovaKnihu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
				try{
					bs.setSelected(true);
				}catch (PropertyVetoException e1){
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSkenovaKnihu, "cell 0 0,growx,aligny center");
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_2, "cell 1 0 1 2,grow");
		
		UtilDateModel m1 = new UtilDateModel();
		JDatePanelImpl datePanel1 = new JDatePanelImpl(m1);
		
		JLabel lblDo = new JLabel("Do:");
		getContentPane().add(lblDo, "cell 1 7");
		JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1);
		getContentPane().add(datePicker1, "cell 1 7");
		
		JButton btnPotvrdi = new JButton("Potvrdi\u0165");
		btnPotvrdi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					if(textField.getText().length() > 0 && textField_3.getText().length() > 0 && datePicker1.getModel().getValue() != null && datePicker2.getModel().getValue() != null){
						
						Person per = db.persons.get(textField_3.getText()); // taker of the book
						Book b = db.books.get(textField.getText()); // book to be taken
						
						b.setBorrowedTime( ((Date)datePicker2.getModel().getValue()).getTime() );
						b.setBorrowedUntilTime( ((Date)datePicker1.getModel().getValue()).getTime() );
						
						if (!per.getBorrowedIDs().contains(b.getID()))
						{
							b.setTakerID(per.getID());
							per.getBorrowedIDs().add(b.getID());
							dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
							dispose();
							
						} else {
							JOptionPane.showMessageDialog(null, "Kniha je už vypožièaná osobou s ID = " + per.getID(), "Chyba", JOptionPane.ERROR_MESSAGE);
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Zadajte ID knihy, ID osoby a dátumy.", "Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}catch(NullPointerException e1){
					
				}
			}
		});
		panel.add(btnPotvrdi, "cell 2 0,growx,aligny center");
		
		JButton btnSkenovaOsobu = new JButton("Nasn\u00EDma\u0165 osobu");
		btnSkenovaOsobu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
				try{
					ps.setSelected(true);
				}catch (PropertyVetoException e1){
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnSkenovaOsobu, "cell 0 1,growx,aligny center");
		
		JButton btnZrui = new JButton("Zru\u0161i\u0165");
		btnZrui.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				dispose();
			}
		});
		panel.add(btnZrui, "cell 2 1,growx,aligny center");
		
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
