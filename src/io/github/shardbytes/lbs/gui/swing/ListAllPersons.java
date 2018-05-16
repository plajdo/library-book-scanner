package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.database.LBSDatabase;
import io.github.shardbytes.lbs.objects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

class ListAllPersons extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	private JTextField textField;
	
	ListAllPersons(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(false);
		setTitle("Zoznam v\u0161etk\u00FDch \u010Ditate\u013Eov");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if(e.getClickCount() == 2 && row != -1){
					PersonInfo pi = new PersonInfo(db.persons.get(table.getModel().getValueAt(row, 0))); //TODO: test .toString as suspicious call
					MainMenu.getDesktopPane().add(pi);
					try{
						pi.setSelected(true);
					}catch(PropertyVetoException e1){
						e1.printStackTrace();
					}
				} else if(e.getClickCount() == 1) {
					Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
					clpbrd.setContents(new StringSelection( (String)table.getModel().getValueAt(row, table.columnAtPoint(point)) ), null);
				}
			}
		});
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tblModel = new DefaultTableModel(null, new String[]{
				"ID \u010Ditate\u013Ea", "Meno", "Trieda", "Knihy"
		}){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		table.setModel(tblModel);
		
		textField = new JTextField();
		textField.addActionListener(e -> refreshTable(textField.getText()));
		getContentPane().add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		
		AddBook.addDataDialogListener(evt -> refreshTable());
		RemoveBook.addDataDialogListener(evt -> refreshTable());
		BorrowBook.addDataDialogListener(evt -> refreshTable());
		RemovePerson.addDataDialogListener(evt -> refreshTable());
		AddPerson.addDataDialogListener(evt -> refreshTable());
		ReturnBook.addDataDialogListener(evt -> refreshTable());
		
		this.addComponentListener(new ComponentListener(){
			
			@Override
			public void componentResized(ComponentEvent e){
				resizeTable();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		
		refreshTable();
		
		setVisible(true);
		
	}
	
	private void addStuffToTable(){
		db.persons.keySet().forEach((key) -> {
			Person p = db.persons.get(key);
			tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup().getName(), p.getBookCount()});
		});
		
	}
	
	private void addSearchToTable(String search){
		db.persons.keySet().forEach((key) -> {
			Person p = db.persons.get(key);
			if(p.getName().contains(search)){
				tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup().getName(), p.getBookCount()});
			}else if(p.getGroup().getName().contains(search)){
				tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup().getName(), p.getBookCount()});
			}else if(p.getID().contains(search)){
				tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup().getName(), p.getBookCount()});
			}
		});
	}
	
	private void clearStuffFromTable(){
		tblModel.getDataVector().removeAllElements();
		tblModel.fireTableDataChanged();
	}
	
	private void refreshTable(){
		clearStuffFromTable();
		addStuffToTable();
	}
	
	private void refreshTable(String text){
		clearStuffFromTable();
		addSearchToTable(text);
	}
	
	private void resizeTable(){
		scrollPane.setPreferredSize(getContentPane().getSize());
	}

}
