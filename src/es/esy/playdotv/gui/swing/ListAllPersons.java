package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class ListAllPersons extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public ListAllPersons(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(false);
		setTitle("Zoznam v\u0161etk\u00FDch \u010Ditate\u013Eov");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		
		getContentPane().add(scrollPane, BorderLayout.NORTH);
		
		tblModel = new DefaultTableModel(null, new String[]{
				"ID \u010Ditate\u013Ea", "Meno", "Trieda", "Knihy"
		});
		table.setModel(tblModel);
		
		AddBook.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		RemoveBook.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		BorrowBook.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		RemovePerson.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		AddPerson.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		ReturnBook.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		this.addComponentListener(new ComponentListener(){
			
			@Override
			public void componentResized(ComponentEvent e){
				resizeTable();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		
		refreshTable();
		
		setVisible(true);
		
	}
	
	private String getBookIDList(List<String> list){
		StringBuilder x = new StringBuilder();
		
		for( int i = 0; i<list.size(); i++ ) 
		{
			x.append(list.get(i));
			x.append((i<list.size()-1) ? ", " : "");
		}
		
		return x.toString();
	}
	
	private void addStuffToTable(){
		for(String key : db.persons.keySet()){
			Person p = db.persons.get(key);
			List<String> borrowed = new ArrayList<>();
			
			for (String key2 : db.books.keySet())
			{
				Book temp = db.books.get(key2);
				if (temp.getTakerID().equals(p.getID())) borrowed.add(temp.getID());
			}
				
			tblModel.addRow(new Object[]{p.getID(), p.getName(), p.getGroup(), getBookIDList(borrowed)});
			
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
	
	private void resizeTable(){
		scrollPane.setPreferredSize(getContentPane().getSize());
	}

}
