package io.github.shardbytes.lbs.gui.swing;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.objects.Group;
import net.miginfocom.swing.MigLayout;

public class AddClass extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JList<String> list;
	
	private ClassDatabase cdb = ClassDatabase.Companion.getInstance();
	
	public AddClass() {
		setTitle("Prida\u0165 triedu");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
		
		JLabel lblNzovTriedy = new JLabel("N\u00E1zov triedy:");
		getContentPane().add(lblNzovTriedy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JLabel lblZoznamTried = new JLabel("Zoznam tried:");
		getContentPane().add(lblZoznamTried, "cell 0 1,alignx trailing");
		
		list = new JList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		JScrollPane scroll = new JScrollPane(list);
		getContentPane().add(scroll, "cell 1 1,grow");
		
		JButton btnPrida = new JButton("Prida\u0165");
		btnPrida.addActionListener((e) -> {
			if(!textField.getText().isEmpty()){
				cdb.getClassList().add(new Group(textField.getText()));
				cdb.save();
				refreshList(listModel, cdb.getClassList());
				textField.setText("");
			}else{
				JOptionPane.showMessageDialog(null, "Nezadali ste \u017Eiadny n\u00E1zov.", "Chyba", JOptionPane.ERROR_MESSAGE);
			}

		});
		getContentPane().add(btnPrida, "cell 0 2");
		
		addGroupsToList(listModel, cdb.getClassList());
		
		setVisible(true);

	}
	
	private void addGroupsToList(DefaultListModel<String> model, ArrayList<Group> list){
		list.forEach((group) -> {
			model.addElement(group.getName());
		});
		
	}
	
	private void clearList(DefaultListModel<String> model){
		model.clear();
	}
	
	private void refreshList(DefaultListModel<String> model, ArrayList<Group> groupList){
		clearList(model);
		addGroupsToList(model, groupList);
	}

}
