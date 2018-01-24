package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.datareader.Generator;
import es.esy.playdotv.event.TableRefreshEvent;
import es.esy.playdotv.event.TableRefreshEventListener;
import es.esy.playdotv.event.TableRefreshEventOperation;
import es.esy.playdotv.objects.Book;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddBook extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public AddBook() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Prida\u0165 knihu");
		setBounds(100, 100, 500, 200);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][grow][pref!][]"));

		JLabel lblIdKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 0,growx");
		textField.setColumns(10);

		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		getContentPane().add(lblNzovKnihy, "cell 0 1,alignx trailing");
		
		textField_1 = new JTextField();
		getContentPane().add(textField_1, "cell 1 1,growx");
		textField_1.setColumns(10);
		
		JLabel lblAutorKnihy = new JLabel("Autor knihy:");
		getContentPane().add(lblAutorKnihy, "cell 0 2,alignx trailing");
		
		textField_2 = new JTextField();
		getContentPane().add(textField_2, "cell 1 2,growx");
		textField_2.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 4 2 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 5 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow][grow][grow]", "[]"));

		JButton btnPotvrdiAPrida = new JButton("Ulo\u017Ei\u0165 a prida\u0165 knihu do datab\u00E1zy");
		btnPotvrdiAPrida.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!(textField.getText().isEmpty()) && !(textField_1.getText().isEmpty()) && !(textField_2.getText().isEmpty())){
					if(isInteger(textField.getText())){
						Book nb = new Book(textField.getText());
						nb.setName(textField_1.getText());
						nb.setAuthor(textField_2.getText());
						nb.setTakerID("");
						db.books.put(textField.getText(), nb);
						btnPotvrdiAPrida.setEnabled(false);
						
						dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
					}else{
						JOptionPane.showMessageDialog(null, "ID mus\u00ED by\u0165 \u010D\u00EDslo.", "Chyba", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Vypl\u0148te v\u0161etky \u00FAdaje.", "Chyba", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		panel.add(btnPotvrdiAPrida, "flowy,cell 0 0,growx");
		
		JButton btnUloiQrKd = new JButton("Ulo\u017Ei\u0165 QR k\u00F3d na disk");
		btnUloiQrKd.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent arg0) {
				FileSystemView fs = FileSystemView.getFileSystemView();
				File[] roots = fs.getRoots();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(fs.getHomeDirectory());
				int r = chooser.showSaveDialog(null);
				if(r == JFileChooser.APPROVE_OPTION){
					try{
						ImageIO.write(Generator.writeQRCode(textField.getText()), "jpg", new File(chooser.getSelectedFile() + ".jpg"));
					}catch(IOException e1){
						e1.printStackTrace();
					}
					btnUloiQrKd.setEnabled(false);
					
				}
				
			}
			
		});
		panel.add(btnUloiQrKd, "flowx,cell 1 0,growx");

		JButton btnDokoni = new JButton("Dokon\u010Di\u0165");
		btnDokoni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnDokoni, "cell 2 0,growx");
		
		setVisible(true);

	}
	
	public static void addDataDialogListener(TableRefreshEventListener trel){
		if(!listeners.contains(trel)){
			listeners.add(trel);
		}
	}
	
	public static void removeDataDialogListener(TableRefreshEventListener trel){
		listeners.remove(trel);
	}
	
	public static void dispatchTableRefreshEvent(TableRefreshEvent evt){
		for(TableRefreshEventListener trel: listeners){
			trel.handleTableRefreshEvent(evt);
		}
	}
	
	private boolean isInteger(String s){
		try{
			String parseString = s.split("/")[0];
			Integer.parseInt(parseString);
			return true;
		}catch(Exception e){
			return false;
		}
	
	}
	
}
