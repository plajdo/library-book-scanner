package io.github.shardbytes.lbs.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import io.github.shardbytes.lbs.objects.Group;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class AddClass extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
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
		
		JList<String> list = new JList<String>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		getContentPane().add(list, "cell 1 1,grow");
		
		JButton btnPrida = new JButton("Prida\u0165");
		btnPrida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		getContentPane().add(btnPrida, "cell 0 2");
		
		/*
		 * TODO: THIS BLYAT
		 */
		for(;;){
			addGroupToList(listModel, null);
		}
		
		setVisible(true);

	}
	
	private void addGroupToList(DefaultListModel<String> model, Group group){
		model.addElement(group.getName());
	}
	
	private void clearList(DefaultListModel<String> model){
		model.clear();
	}

}
