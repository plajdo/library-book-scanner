package es.esy.playdotv.gui;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import es.esy.playdotv.datareader.Generator;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AddBook extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public AddBook() {
		setClosable(true);
		setIconifiable(true);
		setTitle("Prida\u0165 knihu");
		setBounds(100, 100, 450, 200);
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
		panel.setLayout(new MigLayout("", "[]", "[][]"));
		
		JButton btnPotvrdiAPrida = new JButton("Potvrdi\u0165 a prida\u0165 knihu do datab\u00E1zy");
		panel.add(btnPotvrdiAPrida, "cell 0 0");
		
		JButton btnUloiKd = new JButton("Ulo\u017Ei\u0165 k\u00F3d pre knihu");
		btnUloiKd.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent arg0) {
				FileSystemView fs = FileSystemView.getFileSystemView();
				File[] roots = fs.getRoots();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(fs.getHomeDirectory());
				int r = chooser.showSaveDialog(null);
				if(r == JFileChooser.APPROVE_OPTION){
					try{
						ImageIO.write(Generator.writeQRCode(textField.getText() + ";" + textField_1.getText() + ";" + textField_2.getText() + ";"), "jpg", chooser.getSelectedFile());
					}catch(IOException e){
						e.printStackTrace();
					}
					dispose();
					
				}
				
			}
			
		});
		panel.add(btnUloiKd, "cell 0 1,growx");
		
		setVisible(true);

	}
	
}
