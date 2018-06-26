package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.database.WebDB;
import io.github.shardbytes.lbs.objects.BorrowEntry;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

class BorrowingsList extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private final Map<String, Map<Long, BorrowEntry>> borrowings = WebDB.getInstance().getBorrowings();

	BorrowingsList(){
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Zoznam výpožičiek");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		ArrayList<String> arr = new ArrayList<>();
		borrowings.forEach((group, map) -> arr.add(group));
		
		JLabel lblTrieda = new JLabel("Trieda: ");
		getContentPane().add(lblTrieda, "cell 0 0,alignx trailing");
		
		JList<String> list = new JList<>();
		DefaultListModel<String> model = new DefaultListModel<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(model);
		getContentPane().add(new JScrollPane(list), "cell 0 1 2 1,grow");
		
		JComboBox<String> comboBox = new JComboBox<>();
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<>();
		arr.forEach(comboModel::addElement);
		comboBox.setModel(comboModel);
		comboBox.addActionListener((e) -> refreshList(model, comboBox.getSelectedItem().toString()));
		getContentPane().add(comboBox, "cell 1 0,growx");
		
		BorrowBook.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		ReturnBook.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		MainMenu.addDataDialogListener(trel -> refreshList(model, comboBox.getSelectedItem().toString()));
		
		try{
			addStuffToList(model, comboBox.getSelectedItem().toString());
			setVisible(true);
		}catch(NullPointerException e){
			dispose();
			JOptionPane.showMessageDialog(null, "Nebola zatia\u013E vypo\u017Ei\u010Dan\u00E1 \u017Eiadna kniha.", "Chyba", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	private void addStuffToList(DefaultListModel<String> model, String group){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		borrowings.get(group).forEach((id, entry) -> model.addElement(dateFormat.format(new Date(entry.getBorrowDate())) + " - " + entry.getBookID() + " " + entry.getBookName() + ", " + entry.getBorrowerCompleteName()));
		
	}
	
	private void clearStuffFromList(DefaultListModel<String> model){
		model.clear();
	}
	
	private void refreshList(DefaultListModel<String> model, String group){
		clearStuffFromList(model);
		addStuffToList(model, group);
	}

}
