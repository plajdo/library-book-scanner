package io.github.shardbytes.lbs.gui.swing;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.objects.Group;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

public class EditClass extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	private ClassDatabase cdb = ClassDatabase.getInstance();
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	private JTextField textField;
	private JButton btnNovyRad;
	
	public EditClass() {
		setMaximizable(true);
		setIconifiable(true);
		setResizable(true);
		setTitle("Upravi\u0165 triedy");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow][]", "[][][grow]"));
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		getContentPane().add(scrollPane, "cell 0 2,grow");
		
		String[] groupList = new String[cdb.getGroupNumber()];
		for(int i = 0; i < cdb.getGroupNumber(); i++){
			groupList[i] = cdb.getGroupNames().get(i);
		}
		
		tblModel = new DefaultTableModel(null, groupList){
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column){
				return true;
			}
		};
		table.setModel(tblModel);
		
		textField = new JTextField();
		getContentPane().add(textField, "flowx,cell 0 0,growx");
		textField.setColumns(10);
		
		btnNovyRad = new JButton("Nov\u00FD rad");
		getContentPane().add(btnNovyRad, "cell 0 0");
		
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
