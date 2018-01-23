package es.esy.playdotv.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

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
		textField_2.setText(p.getGroup());
		getContentPane().add(textField_2, "cell 1 1,growx");
		textField_2.setColumns(10);
		
		JLabel lblVypoianKnihy = new JLabel("Vypo\u017Ei\u010Dan\u00E9 knihy (" + p.getBookCount() + "):");
		getContentPane().add(lblVypoianKnihy, "cell 0 2,alignx trailing");
		
		AbstractListModel<String> model = new AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		};
		
		addStuffToList(model, p);
		
		JList<String> list = new JList<>(model);
		list.setToolTipText("");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(list, "cell 0 3 2 1,grow");
		
		setVisible(true);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addStuffToList(AbstractListModel model, Person p){
		for(String key : db.books.keySet()){
			Book tempBook = db.books.get(key);
			if(tempBook.getTakerID() == p.getID()){
				((DefaultListModel)model).addElement("ASDasdasda");
			}
			
		}
		
	}
	
	private void clearStuffFromList(){
		//TODO: Update, refresh, eventy
	}
	
	private void refreshList() {
		//TODO: Update, refresh, eventy
	}

}
