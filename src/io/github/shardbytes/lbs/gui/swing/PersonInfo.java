package io.github.shardbytes.lbs.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

import io.github.shardbytes.lbs.database.LBSDatabase;

import io.github.shardbytes.lbs.objects.Book;
import io.github.shardbytes.lbs.objects.Person;

public class PersonInfo extends JInternalFrame {
	
	LBSDatabase db = LBSDatabase.getInstance();
	
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;
	private JTextField textField_2;

	public PersonInfo(Person p) {
		setClosable(true);
		setIconifiable(true);
		setTitle("Inform\u00E1cie o pou\u017E\u00EDvate\u013Eovi - " + p.getID());
		setBounds(120, 120, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][grow]"));
		
		JLabel lblMenoAPriezvisko = new JLabel("Meno a priezvisko:");
		getContentPane().add(lblMenoAPriezvisko, "cell 0 0,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText(p.getName());
		getContentPane().add(textField_1, "cell 1 0,growx");
		textField_1.setColumns(10);
		
		JLabel lblTrieda = new JLabel("Trieda:");
		getContentPane().add(lblTrieda, "cell 0 1,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setText(p.getGroup().getName());
		getContentPane().add(textField_2, "cell 1 1,growx");
		textField_2.setColumns(10);
		
		JLabel lblVypoianKnihy = new JLabel("Vypo\u017Ei\u010Dan\u00E9 knihy (" + p.getBookCount() + "):");
		getContentPane().add(lblVypoianKnihy, "cell 0 2,alignx trailing");
		
		JList<String> list = new JList<>();
		DefaultListModel<String> model = new DefaultListModel<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(new JScrollPane(list), "cell 0 3 2 1,grow");
		
		ReturnBook.addDataDialogListener((TableRefreshEventListener) -> refreshList(model, p, list, lblVypoianKnihy));
		BorrowBook.addDataDialogListener((TableRefreshEventListener) -> refreshList(model, p, list, lblVypoianKnihy));
		MainMenu.addDataDialogListener(trel -> {
			try{
				refreshList(model, p, list, lblVypoianKnihy);
			}catch(Exception e){
				dispose();
			}
			
		});
		
		refreshList(model, p, list, lblVypoianKnihy);
		
		setVisible(true);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addStuffToList(DefaultListModel model, Person p){
		db.books.keySet().forEach((s) -> {
			Book tempBook = db.books.get(s);
			if(tempBook.getTakerID().equals(p.getID())){
				model.addElement("[" + tempBook.getID() + "] " + tempBook.getAuthor() + ": " + tempBook.getName());
			}
			
		});
		
	}
	
	private void clearStuffFromList(DefaultListModel<String> dlm){
		dlm.clear();
	}
	
	private void refreshList(DefaultListModel<String> dml, Person p, JList<String> l, JLabel label) {
		clearStuffFromList(dml);
		addStuffToList(dml, p);
		l.setModel(dml);
		label.setText("Vypo\u017Ei\u010Dan\u00E9 knihy (" + p.getBookCount() + "):");
	}

}
