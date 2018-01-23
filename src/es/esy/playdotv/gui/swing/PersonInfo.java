package es.esy.playdotv.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

import es.esy.playdotv.objects.Person;

public class PersonInfo extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;
	private JTextField textField_2;

	public PersonInfo(Person p) {
		setClosable(true);
		setIconifiable(true);
		setTitle("Inform\u00E1cie o pou\u017E\u00EDvate\u013Eovi - " + p.getID());
		setBounds(100, 100, 450, 300);
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
		
		JList list = new JList();
		list.setToolTipText("");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		getContentPane().add(list, "cell 0 3 2 1,grow");
		
	}

}
