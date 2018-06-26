package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.database.WebDB;
import io.github.shardbytes.lbs.objects.Book;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

class BooksToReturn extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel tblModel;
	
	private JTextField textField;
	
	BooksToReturn(){
		setIconifiable(true);
		setClosable(true);
		setResizable(true);
		setMaximizable(false);
		setTitle("Knihy na vr\u00E1tenie");
		setBounds(100, 100, 450, 300);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				Point point = e.getPoint();
				int row = table.rowAtPoint(point);
				if(e.getClickCount() == 2 && row != -1){
					BookInfo bi = new BookInfo(WebDB.getInstance().getBooks().get(table.getModel().getValueAt(row, 0)));
					MainMenu.getDesktopPane().add(bi);
					try{
						bi.setSelected(true);
					}catch(PropertyVetoException e1){
						e1.printStackTrace();
					}
				} else if(e.getClickCount() == 1) {
					Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
					if(table.getModel().getValueAt(row, table.columnAtPoint(point)) instanceof java.util.Date){
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
						clpbrd.setContents(new StringSelection(dateFormat.format((Date)table.getModel().getValueAt(row, table.columnAtPoint(point)))), null);
					}else{
						clpbrd.setContents(new StringSelection( (String)table.getModel().getValueAt(row, table.columnAtPoint(point)) ), null);						
					}
					
				}
				
			}
			
		});
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() - 30));
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tblModel = new DefaultTableModel(null, new String[]{
				"ID knihy", "N\u00E1zov knihy", "Autor knihy", "Vr\u00E1ti\u0165 do"
		}){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		
		table.setModel(tblModel);
		
		textField = new JTextField();
		textField.addActionListener(e -> refreshTable(textField.getText()));
		getContentPane().add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		table.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());
		
		BorrowBook.addDataDialogListener(evt -> refreshTable());
		ReturnBook.addDataDialogListener(evt -> refreshTable());
		MainMenu.addDataDialogListener(trel -> refreshTable());
		
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
		final Map<String, Book> books = WebDB.getInstance().getBooks();
		books.keySet().forEach((key) -> {
			Book b = books.get(key);
			if(!b.getTakerID().isEmpty()){
				tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
			}
			
		});
		
	}
	
	private void addSearchToTable(String search){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		final Map<String, Book> books = WebDB.getInstance().getBooks();
		books.keySet().forEach((key) -> {
			Book b = books.get(key);
			if(b.getAuthor().toLowerCase().contains(search.toLowerCase())){
				if(!b.getTakerID().isEmpty()){
					tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
				}
			}else if(b.getName().toLowerCase().contains(search.toLowerCase())){
				if(!b.getTakerID().isEmpty()){
					tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
				}
			}else if(b.getID().toLowerCase().contains(search.toLowerCase())){
				if(!b.getTakerID().isEmpty()){
					tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
				}
			}else if(dateFormat.format(b.getBorrowedUntilTime()).toLowerCase().contains(search.toLowerCase())){
				if(b.getTakerID().isEmpty()){
					tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), null});
				}else{
					tblModel.addRow(new Object[]{b.getID(), b.getName(), b.getAuthor(), new Date(b.getBorrowedUntilTime())});
				}
				
			}
			
		});

	}
	
	private void clearStuffFromTable(){
		tblModel.getDataVector().removeAllElements();
		tblModel.fireTableDataChanged();
	}
	
	private void refreshTable(){
		clearStuffFromTable();
		addStuffToTable();
	}
	
	private void refreshTable(String text){
		clearStuffFromTable();
		addSearchToTable(text);
	}
	
	private void resizeTable(){
		scrollPane.setPreferredSize(getContentPane().getSize());
	}

}
