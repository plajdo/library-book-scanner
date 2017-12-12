package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.objects.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.awt.BorderLayout;

public class ListAllBooks extends JInternalFrame implements TableRefreshEventListener{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tblModel;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public ListAllBooks(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setTitle("Zoznam v\u0161etk\u00FDch kn\u00EDh");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		
		getContentPane().add(new JScrollPane(table), BorderLayout.NORTH);
		
		tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "N\u00E1zov knihy", "Autor knihy", "Vypo\u017Ei\u010Dan\u00E1?"
		});
		table.setModel(tblModel);
		table.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());
		
		refreshTable();
		
		setVisible(true);
		
	}
	
	private void addStuffToTable(){
		for (String key : db.books.keySet()){
			Book b = db.books.get(key);
			
			if(b.getTakerID().isEmpty())
			{
				tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), null});
			} else {
				tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
			}
			
			
		}
		
	}
	
	private void clearStuffFromTable(){
		tblModel.getDataVector().removeAllElements();
		tblModel.fireTableDataChanged();
	}
	
	private void refreshTable(){
		clearStuffFromTable();
		addStuffToTable();
	}

	@Override
	public void handleTableRefreshEvent(TableRefreshEvent evt) {
		System.out.println("BLYAAAAAAAAAAT");
		
	}
	
}
