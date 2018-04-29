package io.github.shardbytes.lbs.gui.swing;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.objects.Group;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RemoveClass extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	
	private ClassDatabase cdb = ClassDatabase.Companion.getInstance();
	
	public RemoveClass() {
		setTitle("Odstru00E1ni\u0165 triedu");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		JLabel lblZoznamTried = new JLabel("Zoznam tried:");
		getContentPane().add(lblZoznamTried, "cell 0 0,alignx leading");
		
		list = new JList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane();
		getContentPane().add(scroll, "cell 0 1,grow");
		
		JButton btnRemove = new JButton("Odstr\u00E1ni\u0165");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				cdb.getClassList().remove(list.getSelectedIndex());
				list.remove(list.getSelectedIndex());
				refreshList(listModel, cdb.getClassList());
			}
		});
		getContentPane().add(btnRemove, "cell 0 2");
		
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
