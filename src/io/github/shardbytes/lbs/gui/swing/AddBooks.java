package io.github.shardbytes.lbs.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import io.github.shardbytes.lbs.database.LBSDatabase;

import io.github.shardbytes.lbs.datareader.Generator;
import io.github.shardbytes.lbs.event.TableRefreshEvent;
import io.github.shardbytes.lbs.event.TableRefreshEventListener;
import io.github.shardbytes.lbs.event.TableRefreshEventOperation;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Book;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class AddBooks extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField sig;
	private JTextField name;
	private JTextField author;
	private JTextField qrloc;
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public AddBooks() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Prida\u0165 viacero kn\u00EDh");
		setBounds(100, 100, 450, 225);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][][grow][grow]", "[][][][][][grow][]"));
		
		JLabel lblIdKnihy = new JLabel("ID knihy:");
		getContentPane().add(lblIdKnihy, "cell 0 0,alignx trailing");
		
		JSpinner spinnerprir = new JSpinner();
		spinnerprir.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		getContentPane().add(spinnerprir, "flowx,cell 1 0,growx");
		
		JLabel label = new JLabel("/");
		getContentPane().add(label, "cell 2 0,alignx trailing");
		
		sig = new JTextField();
		getContentPane().add(sig, "cell 3 0,growx");
		sig.setColumns(10);
		
		JSpinner spinnersig = new JSpinner();
		spinnersig.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		getContentPane().add(spinnersig, "flowx,cell 4 0,growx");
		
		JLabel lblNzovKnihy = new JLabel("N\u00E1zov knihy:");
		getContentPane().add(lblNzovKnihy, "cell 0 1,alignx trailing");
		
		name = new JTextField();
		getContentPane().add(name, "cell 1 1 4 1,growx");
		name.setColumns(10);
		
		JLabel lblAutorKnihy = new JLabel("Autor knihy:");
		getContentPane().add(lblAutorKnihy, "cell 0 2,alignx trailing");
		
		author = new JTextField();
		getContentPane().add(author, "cell 1 2 4 1,growx");
		author.setColumns(10);
		
		JLabel lblQrFid = new JLabel("QR FID:");
		getContentPane().add(lblQrFid, "cell 0 3,alignx trailing");
		
		qrloc = new JTextField();
		getContentPane().add(qrloc, "cell 1 3 4 1,growx");
		qrloc.setColumns(10);
		
		JSeparator separator = new JSeparator();
		getContentPane().add(separator, "cell 0 4 5 1,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 5 5 2,grow");
		panel.setLayout(new MigLayout("", "[122px,grow][128px,grow]", "[29px][]"));
		
		JButton btnaliaKniha = new JButton("\u010Eal\u0161ia kniha");
		btnaliaKniha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int prir_int = (int)spinnerprir.getModel().getValue();
				int sign_int = (int)spinnersig.getModel().getValue();
				
				StringBuilder bookID = new StringBuilder();
				bookID.append(prir_int);
				bookID.append("/");
				bookID.append(sig.getText());
				bookID.append(sign_int);
				
				StringBuilder qrName = new StringBuilder();
				qrName.append(prir_int);
				qrName.append(sig.getText());
				qrName.append(sign_int);
				
				Book nb = new Book(bookID.toString());
				nb.setName(name.getText());
				nb.setAuthor(author.getText());
				nb.setTakerID("");
				
				try{
					saveQR(qrloc.getText(), bookID.toString(), qrName.toString());
				}catch(IOException e1){
					JOptionPane.showMessageDialog(null, "Chyba pri ukladan\u00ED QR k\u00F3du!", "Chyba", JOptionPane.ERROR_MESSAGE);
					TermUtils.printerr("Cannot save QR code!");
					return;
				}
				
				db.books.put(bookID.toString(), nb);
				dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
				
				prir_int++;
				sign_int++;
				spinnerprir.getModel().setValue(prir_int);
				spinnersig.getModel().setValue(sign_int);
				
			}
		});
		panel.add(btnaliaKniha, "flowx,cell 0 0,growx,aligny center");
		
		JButton btnZmazadaje = new JButton("Zmaza\u0165 \u00FAdaje");
		btnZmazadaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				author.setText("");
				name.setText("");
				
			}
		});
		panel.add(btnZmazadaje, "cell 1 0,growx,aligny center");
		
		JButton btnPreskoi = new JButton("Presko\u010Di\u0165");
		btnPreskoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int prir_int = (int)spinnerprir.getModel().getValue();
					int sign_int = (int)spinnersig.getModel().getValue();
					prir_int++;
					sign_int++;
					spinnerprir.getModel().setValue(prir_int);
					spinnersig.getModel().setValue(sign_int);
				}catch(Exception e1){
					TermUtils.printerr("Cannot increment!");
				}
			}
		});
		panel.add(btnPreskoi, "flowx,cell 0 1,growx,aligny center");
		
		JButton btnVybraPrieinok = new JButton("Vybra\u0165 prie\u010Dinok");
		panel.add(btnVybraPrieinok, "cell 1 1,growx,aligny center");
		
		FileSystemView fs = FileSystemView.getFileSystemView();
		@SuppressWarnings("unused")
		File[] roots = fs.getRoots();
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(fs.getHomeDirectory());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int r = chooser.showDialog(null, "Select directory");
		if(r == JFileChooser.APPROVE_OPTION){
			qrloc.setText(chooser.getSelectedFile().toString());
			setVisible(true);
		}else{
			dispose();
		}

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
	
	private void saveQR(String folderPath, String qrText, String qrName) throws IOException{
		ImageIO.write(Generator.writeQRCode(qrText), "jpg", new File(folderPath + "/" + qrName + ".jpg"));
	}

}
