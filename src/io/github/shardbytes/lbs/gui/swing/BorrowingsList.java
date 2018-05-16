package io.github.shardbytes.lbs.gui.swing;

import javax.swing.JInternalFrame;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import io.github.shardbytes.lbs.database.BorrowDatabase;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;

public class BorrowingsList extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	
	BorrowDatabase bdb = BorrowDatabase.getInstance();

	public BorrowingsList(){
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Zoznam výpožičiek");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		ArrayList<String> arr = new ArrayList<String>();
		bdb.borrowings.forEach((group, map) -> {
			arr.add(group);
		});
		
		JLabel lblTrieda = new JLabel("Trieda: ");
		getContentPane().add(lblTrieda, "cell 0 0,alignx trailing");
		
		JList<String> list = new JList<String>();
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(model);
		getContentPane().add(new JScrollPane(list), "cell 0 1 2 1,grow");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();
		arr.forEach((group) -> {
			comboModel.addElement(group);
		});
		comboBox.setModel(comboModel);
		comboBox.addActionListener((e) -> {
			refreshList(model, comboBox.getSelectedItem().toString());
		});
		getContentPane().add(comboBox, "cell 1 0,growx");
		
		BorrowBook.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		ReturnBook.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		MainMenu.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		
		addStuffToList(model, comboBox.getSelectedItem().toString());
		
		setVisible(true);

	}
	
	private void addStuffToList(DefaultListModel<String> model, String group){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		bdb.borrowings.get(group).forEach((id, entry) -> {
			model.addElement(dateFormat.format(new Date(entry.getBorrowDate())) + " - " + entry.getBookID() + " " + entry.getBookName() + ", " + entry.getBorrowerCompleteName());
		});
		
	}
	
	private void clearStuffFromList(DefaultListModel<String> model){
		model.clear();
	}
	
	private void refreshList(DefaultListModel<String> model, String group){
		clearStuffFromList(model);
		addStuffToList(model, group);
	}

}
