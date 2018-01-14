package es.esy.playdotv.gui.swing;

import javax.swing.JInternalFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Export extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public Export() {
		setTitle("Exportovať databázu");
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblExportova = new JLabel("Exportovať:");
		getContentPane().add(lblExportova, "cell 0 0");
		
		JCheckBox chckbxZoznamKnh = new JCheckBox("Zoznam kníh");
		getContentPane().add(chckbxZoznamKnh, "cell 1 0,grow");
		
		JCheckBox chckbxZoznamitateov = new JCheckBox("Zoznam čitateľov");
		getContentPane().add(chckbxZoznamitateov, "cell 1 1,grow");
		
		JCheckBox chckbxZoznamVpoiiek = new JCheckBox("Zoznam výpožičiek");
		getContentPane().add(chckbxZoznamVpoiiek, "cell 1 2,grow");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3 2 1,grow");
		panel.setLayout(new MigLayout("", "[][grow][]", "[][]"));
		
		JLabel lblExportovaDo = new JLabel("Exportovať do:");
		panel.add(lblExportovaDo, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		panel.add(textField, "cell 1 0 2 1,growx");
		textField.setColumns(10);
		
		JButton btnExportova = new JButton("Exportovať");
		btnExportova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO exportovať tabuľku
			}
		});
		panel.add(btnExportova, "cell 2 1,grow");
		
		JButton btnZvoliPrieinok = new JButton("Zvoliť priečinok");
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
					btnExportova.setEnabled(true);
				}else{
					btnExportova.setEnabled(false);
				}
			}
		});
		panel.add(btnZvoliPrieinok, "cell 1 1,grow");
		
		btnExportova.setEnabled(false);
		setVisible(true);
		
	}

}
