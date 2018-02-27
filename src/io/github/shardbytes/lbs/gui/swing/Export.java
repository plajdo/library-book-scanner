package io.github.shardbytes.lbs.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.document.Table;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Export extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JCheckBox chckbxZoznamKnh;
	private JCheckBox chckbxZoznamitateov;
	private JCheckBox chckbxZoznamVpoiiek;
	private JButton btnExportova;
	
	BorrowDatabase bdb = BorrowDatabase.getInstance();

	public Export(){
		setTitle("Exportova\u0165 datab\u00E1zu");
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblExportova = new JLabel("Exportova\u0165:");
		getContentPane().add(lblExportova, "cell 0 0");
		
		chckbxZoznamKnh = new JCheckBox("Zoznam kn\u00EDh");
		chckbxZoznamKnh.addActionListener((e) -> {
			updateButton();
		});
		getContentPane().add(chckbxZoznamKnh, "cell 1 0,grow");
		
		chckbxZoznamitateov = new JCheckBox("Zoznam \u010Ditate\u013Eov");
		chckbxZoznamitateov.addActionListener((e) -> {
			updateButton();
		});
		getContentPane().add(chckbxZoznamitateov, "cell 1 1,grow");
		
		chckbxZoznamVpoiiek = new JCheckBox("Zoznam v\u00FDpo\u017Ei\u010Diek");
		chckbxZoznamVpoiiek.addActionListener((e) -> {
			updateButton();
		});
		getContentPane().add(chckbxZoznamVpoiiek, "cell 1 2,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[][grow][]", "[][]"));
		
		JLabel lblExportovaDo = new JLabel("Exportova\u0165 do:");
		panel.add(lblExportovaDo, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		panel.add(textField, "cell 1 0 2 1,growx");
		textField.setColumns(10);
		
		btnExportova = new JButton("Exportova\u0165");
		btnExportova.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!textField.getText().isEmpty()){
					if(chckbxZoznamitateov.isSelected()){
						try{
							Table.createPersonTable(textField.getText() + File.separator);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						
					}
					if(chckbxZoznamKnh.isSelected()){
						try{
							Table.createBooksTable(textField.getText() + File.separator);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						
					}
					if(chckbxZoznamVpoiiek.isSelected()){
						try{
							ArrayList<String> groupsList = new ArrayList<String>();
							bdb.borrowings.forEach((group, map) -> {groupsList.add(group);});
							Object[] groups = groupsList.toArray();
							
							try{
								String input = (String)JOptionPane.showInputDialog(null, "Vyberte triedu na exportovanie:", "export", JOptionPane.INFORMATION_MESSAGE, null, groups, groups[0]);
								if(!(input == null)){
									Table.createBorrowingsTable(input, textField.getText() + File.separator);							
								}
							}catch(ArrayIndexOutOfBoundsException e1){
								JOptionPane.showMessageDialog(null, "\u017Diadna trieda nem\u00E1 vypo\u017Ei\u010Dan\u00E9 knihy - nie je \u010Do exportova\u0165.", "Chyba", JOptionPane.ERROR_MESSAGE);
								e1.printStackTrace();
							}
							
						}catch(Exception e1){
							e1.printStackTrace();
						}
						
					}
					dispose();
					
				}else{
					JOptionPane.showMessageDialog(null, "Zvo\u013Ete prie\u010Dinok, do ktor\u00E9ho sa m\u00E1 datab\u00E1za exportova\u0165.", "Chyba", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		panel.add(btnExportova, "cell 2 1,grow");
		
		JButton btnZvoliPrieinok = new JButton("Zvoli\u0165 prie\u010Dinok");
		btnZvoliPrieinok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileSystemView fs = FileSystemView.getFileSystemView();
				@SuppressWarnings("unused")
				File[] roots = fs.getRoots();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(fs.getHomeDirectory());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = chooser.showDialog(null, "Select directory");
				if(r == JFileChooser.APPROVE_OPTION){
					textField.setText(chooser.getSelectedFile().toString());
				}
				
			}
			
		});
		panel.add(btnZvoliPrieinok, "cell 1 1,grow");
		
		updateButton();
		setVisible(true);
		
	}
	
	private void updateButton(){
		btnExportova.setEnabled(chckbxZoznamitateov.isSelected() || chckbxZoznamKnh.isSelected() || chckbxZoznamVpoiiek.isSelected());
	}

}
