package es.esy.playdotv.gui;

import es.esy.playdotv.Load;
import es.esy.playdotv.objects.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListAllStudents extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public ListAllStudents(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Zoznam v\u0161etk\u00FDch žiakov");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		getContentPane().add(new JScrollPane(table));
		
		DefaultTableModel tblModel = new DefaultTableModel(null, new String[]{
				"ID žiaka", "Meno", "Trieda", "Knihy"
		});
		table.setModel(tblModel);
		
		setVisible(true);

		for (String key : Load.students.keySet()){
			Student s = (Student)Load.students.get(key);
			tblModel.addRow(new Object[]{s.getID(), s.getName(), s.getGroup(), s.getPapers()});
			
		}
		
	}

}
