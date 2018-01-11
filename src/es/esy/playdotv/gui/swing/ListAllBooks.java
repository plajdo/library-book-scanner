package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.objects.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ListAllBooks extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public ListAllBooks(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(false);
		setTitle("Zoznam v\u0161etk\u00FDch kn\u00EDh");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		
		getContentPane().add(scrollPane, BorderLayout.NORTH);
		
		tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "N\u00E1zov knihy", "Autor knihy", "Vypo\u017Ei\u010Dan\u00E1?"
		});
		table.setModel(tblModel);
		table.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());
		
		AddBook.addDataDialogListener(new TableRefreshEventListener(){

			@Override
			public void handleTableRefreshEvent(TableRefreshEvent evt) {
				refreshTable();
				
			}
			
		});
		AddBooks.addDataDialogListener((TableRefreshEvent) -> {
			refreshTable();
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
	
	private void addStuffToTable(){
		for (String key : db.books.keySet()){
			Book b = db.books.get(key);
			
			if(b.getTakerID().isEmpty()){
				tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), null});
			}else{
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
	
	private void resizeTable(){
		scrollPane.setPreferredSize(getContentPane().getSize());
	}
	
}
