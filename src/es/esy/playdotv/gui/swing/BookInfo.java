package es.esy.playdotv.gui.swing;

import java.text.SimpleDateFormat;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

public class BookInfo extends JInternalFrame{
	
	LBSDatabase db = LBSDatabase.getInstance();
	
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblIdKnihy;
	private JTextField textField;
	private JLabel lblVypoian;
	private JTextField textField_3;
	private JLabel lblOsoba;
	private JLabel lblTrieda;
	private JTextField textField_4;
	private JTextField textField_5;
	private JLabel lblOd;
	private JLabel lblDo;
	private JTextField textField_6;
	private JTextField textField_7;

	public BookInfo(Book b) {
		setClosable(true);
		setIconifiable(true);
		setTitle("Inform\u00E1cie o knihe - " + b.getID());
		setBounds(120, 120, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		
		lblIdKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setText(b.getID());
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		getContentPane().add(lblAutor, "cell 0 1,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText(b.getAuthor());
		getContentPane().add(textField_1, "cell 1 1,growx");
		textField_1.setColumns(10);
		
		JLabel lblNzov = new JLabel("N\u00E1zov:");
		getContentPane().add(lblNzov, "cell 0 2,alignx trailing");
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setText(b.getName());
		getContentPane().add(textField_2, "cell 1 2,growx");
		textField_2.setColumns(10);
		
		lblVypoian = new JLabel("Vypožičaná:");
		getContentPane().add(lblVypoian, "cell 0 3,alignx trailing");
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setText((b.getTakerID().isEmpty() ? "Nie" : "Áno"));
		getContentPane().add(textField_3, "cell 1 3,growx");
		textField_3.setColumns(10);
		
		lblOsoba = new JLabel("Osoba:");
		getContentPane().add(lblOsoba, "cell 0 4,alignx trailing");
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		getContentPane().add(textField_4, "cell 1 4,growx");
		textField_4.setColumns(10);
		
		lblTrieda = new JLabel("Trieda:");
		getContentPane().add(lblTrieda, "cell 0 5,alignx trailing");
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		getContentPane().add(textField_5, "cell 1 5,growx");
		textField_5.setColumns(10);
		
		lblOd = new JLabel("Od:");
		getContentPane().add(lblOd, "cell 0 6,alignx trailing");
		
		textField_6 = new JTextField();
		textField_6.setEditable(false);
		getContentPane().add(textField_6, "cell 1 6,growx");
		textField_6.setColumns(10);
		
		lblDo = new JLabel("Do:");
		getContentPane().add(lblDo, "cell 0 7,alignx trailing");
		
		textField_7 = new JTextField();
		textField_7.setEditable(false);
		getContentPane().add(textField_7, "cell 1 7,growx");
		textField_7.setColumns(10);
		
		if(b.getTakerID().isEmpty()){
			textField_4.setEnabled(false);
			textField_5.setEnabled(false);
			textField_6.setEnabled(false);
			textField_7.setEnabled(false);
		}else{
			Person p = db.persons.get(b.getTakerID());
			SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM yyyy");
			
			textField_4.setText(p.getName());
			textField_5.setText(p.getGroup());
			textField_6.setText(sdf.format(b.getBorrowedTime()));
			textField_7.setText(sdf.format(b.getBorrowedUntilTime()));
		}
		
		setVisible(true);
		
	}

}
