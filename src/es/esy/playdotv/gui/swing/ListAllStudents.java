package es.esy.playdotv.gui.swing;

import es.esy.playdotv.Load;
import es.esy.playdotv.objects.Paper;
import es.esy.playdotv.objects.Student;

import java.util.ArrayList;

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

		for(String key : Load.students.keySet()){
			Student s = (Student)Load.students.get(key);
			tblModel.addRow(new Object[]{s.getID(), s.getName(), s.getGroup(), getBookList(s.getPapers())});
			
		}
		
	}
	
	private String getBookList(ArrayList<Paper> a){
		String x = "";
		for(Paper index : a){
			x = x.concat(index.getID() + ", ");
			
		}
		return x;
		
	}

}
