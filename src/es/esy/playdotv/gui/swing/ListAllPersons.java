package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.objects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListAllPersons extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public ListAllPersons(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Zoznam v\u0161etk\u00FDch osôb");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		getContentPane().add(new JScrollPane(table));
		
		DefaultTableModel tblModel = new DefaultTableModel(null, new String[]{
				"ID žiaka", "Meno", "Trieda", "Knihy"
		});
		table.setModel(tblModel);
		
		setVisible(true);

		for(String key : db.persons.keySet()){
			Person p = db.persons.get(key);
			tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup(), getBookIDList(p.getBorrowedIDs())  });
			
		}
		
	}
	
	private String getBookIDList(List<String> list){
		String x = "";
		for(String id : list) 
		{
			x = x.concat(id + ", ");
		}
		return x;
	}

}
