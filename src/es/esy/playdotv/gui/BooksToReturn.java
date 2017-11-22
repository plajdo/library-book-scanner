package es.esy.playdotv.gui;

import es.esy.playdotv.Load;
import es.esy.playdotv.objects.Paper;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BooksToReturn extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public BooksToReturn(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Knihy na vr\u00E1tenie");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		getContentPane().add(new JScrollPane(table));
		
		DefaultTableModel tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "N\u00E1zov knihy", "Autor knihy", "Vr\u00E1ti\u0165 do"
		});
		
		table.setModel(tblModel);
		table.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());
		
		setVisible(true);

		for (String key : Load.papers.keySet()){
			Paper p = Load.papers.get(key);
			
			if(p.isBorrowed()){
				tblModel.addRow(new Object[]{p.getID(), p.getTitle(), p.getAuthor(), p.getBorrowedUntilDate()});
			}
			
		}

	}

}
