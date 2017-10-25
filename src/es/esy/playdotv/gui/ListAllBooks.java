package es.esy.playdotv.gui;

import es.esy.playdotv.Load;
import es.esy.playdotv.objects.Paper;

import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListAllBooks extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public ListAllBooks(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Zoznam v\u0161etk\u00FDch kn\u00EDh");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		getContentPane().add(new JScrollPane(table));
		
		DefaultTableModel tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "N\u00E1zov knihy", "Autor knihy", "Vypo\u017Ei\u010Dan\u00E1?"
		});
		table.setModel(tblModel);
		
		setVisible(true);
		
		SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy");

		for (String key : Load.papers.keySet()){
			Paper p = Load.papers.get(key);
			
			if(!p.isBorrowed()){
				tblModel.addRow(new Object[]{p.getID(), p.getTitle(), p.getAuthor(), "Nie"});
			}else{
				tblModel.addRow(new Object[]{p.getID(), p.getTitle(), p.getAuthor(), "Do " + sdf.format(p.getBorrowedUntilDate())});
			}
			
		}

	}

}
