package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.database.LBSDatabase;

import io.github.shardbytes.lbs.event.DDEventListener;
import io.github.shardbytes.lbs.event.DataDialogEvent;
import io.github.shardbytes.lbs.event.DataDialogEventOperation;
import io.github.shardbytes.lbs.event.TableRefreshEvent;
import io.github.shardbytes.lbs.event.TableRefreshEventListener;
import io.github.shardbytes.lbs.event.TableRefreshEventOperation;
import io.github.shardbytes.lbs.objects.Book;
import io.github.shardbytes.lbs.objects.Person;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

public class ReturnBook extends JInternalFrame{
	
	static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	private LBSDatabase db = LBSDatabase.getInstance();
	private BorrowDatabase bdb = BorrowDatabase.getInstance();
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	public ReturnBook(JDesktopPane desktopPane){
		setTitle("Vr\u00E1ti\u0165 knihu");
		setBounds(100, 100, 460, 155);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
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
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 4 2 1,grow");
		panel.setLayout(new MigLayout("", "[110.00,grow][110.00,grow][grow]", "[]"));
		
		JButton btnSkenovaKnihu = new JButton("Nasn\u00EDma\u0165 knihu");
		btnSkenovaKnihu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BookScanner bs = new BookScanner();
				bs.addDataDialogListener(new DDEventListener(){
					@Override
					public void handleDataDialogEvent(DataDialogEvent evt){
						if(evt.getOperation() == DataDialogEventOperation.EVENT_SUCCEEDED){
							String tempid = bs.getData();
							Book tempbook = db.books.get(tempid);
							textField.setText(tempid);
							textField_1.setText(tempbook.getAuthor());
							textField_2.setText(tempbook.getName());
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_FAILED){
							textField.setText("Chyba");
							textField_1.setText("Chyba");
							textField_2.setText("Chyba");
						}else if(evt.getOperation() == DataDialogEventOperation.EVENT_CANCELLED){
							textField.setText("Zru\u0161en\u00E9");
							textField_1.setText("Zru\u0161en\u00E9");
							textField_2.setText("Zru\u0161en\u00E9");
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
		
		JButton btnPotvrdi = new JButton("Vr\u00E1ti\u0165");
		btnPotvrdi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					if(textField.getText().length() > 0){
						if(isInteger(textField.getText())){
							if(db.books.containsKey(textField.getText())){
								Book b = db.books.get(textField.getText());
								if(!b.getTakerID().isEmpty() && !(b.getBorrowedTime() == 0) && !(b.getBorrowedUntilTime() == 0)){
									Person p = db.persons.get(b.getTakerID());
									p.subtractBookCount();
									b.setTakerID("");
									b.setBorrowedTime(0);
									b.setBorrowedUntilTime(0);
									
									bdb.borrowings.get(p.getGroup()).put(id, borrowentry);
									
									dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
									dispose();
								}else{
									JOptionPane.showMessageDialog(null, "Kniha nie je vypo\u017Ei\u010Dan\u00E1.", "Chyba", JOptionPane.ERROR_MESSAGE);
								}
							}else{
								JOptionPane.showMessageDialog(null, "Kniha neexistuje v datab\u00E1ze.", "Chyba", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(null, "Neplatn\u00E9 ID knihy.", "Chyba", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Zadajte ID knihy.", "Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}catch(NullPointerException e1){
					
				}
			}
		});
		panel.add(btnPotvrdi, "cell 1 0,growx,aligny center");
		
		JButton btnZrui = new JButton("Zru\u0161i\u0165");
		btnZrui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				dispose();
			}
		});
		panel.add(btnZrui, "cell 2 0,growx,aligny center");
		
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
	
	private boolean isInteger(String s){
		try{
			String parseString = s.split("/")[0];
			Integer.parseInt(parseString);
			return true;
		}catch(Exception e){
			return false;
		}
	
	}

}
