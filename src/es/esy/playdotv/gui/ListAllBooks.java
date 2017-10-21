package es.esy.playdotv.gui;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.io.IOException;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.github.plasmoxy.database.SebuLink;

import es.esy.playdotv.objects.Paper;

public class ListAllBooks extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	public ListAllBooks(){
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Zoznam v\u0161etk\u00FDch kn\u00EDh");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		getContentPane().add(new JScrollPane(table));
		
		DefaultTableModel tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "Názov knihy", "Autor knihy", "Vypožièaná?"
		});
		table.setModel(tblModel);
		
		setVisible(true);
		
		Map<String, Paper> papers = null;
		try {
			papers = SebuLink.load("papers.ser");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String key : papers.keySet())
		{
			Paper p = papers.get(key);
			System.out.printf("%s : %s - %s\n", p.getID(), p.getAuthor(), p.getTitle());
			tblModel.addRow(new Object[]{p.getID(), p.getTitle(), p.getAuthor(), "?????"});
			
		}
		
	}

}
