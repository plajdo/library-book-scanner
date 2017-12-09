package es.esy.playdotv.gui.swing;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import es.esy.playdotv.SebuLink;

import es.esy.playdotv.Load;

public class Settings{

	private JFrame frmNastavenia;
	private JTextField textField;
	
	public Settings(){
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize(){
		frmNastavenia = new JFrame();
		frmNastavenia.setTitle("Nastavenia");
		frmNastavenia.setBounds(100, 100, 450, 300);
		frmNastavenia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNastavenia.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][26.00,grow][]"));
		
		JLabel lblVzhadProgramu = new JLabel("Vzh\u013Ead programu:");
		frmNastavenia.getContentPane().add(lblVzhadProgramu, "cell 0 0,alignx trailing");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(3);
		comboBox.setModel(new DefaultComboBoxModel(LookAndFeelSettingsList.values()));
		frmNastavenia.getContentPane().add(comboBox, "cell 1 0,growx");
		
		JSeparator separator = new JSeparator();
		frmNastavenia.getContentPane().add(separator, "cell 0 1 2 1,grow");
		
		JLabel lblOtvoriOknPri = new JLabel("Otvori\u0165 okn\u00E1 pri zapnut\u00ED:");
		frmNastavenia.getContentPane().add(lblOtvoriOknPri, "cell 0 2,alignx trailing");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(GuiWindowsList.values()));
		comboBox_1.setSelectedIndex(6);
		frmNastavenia.getContentPane().add(comboBox_1, "cell 1 2,growx");
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(GuiWindowsList.values()));
		comboBox_2.setSelectedIndex(6);
		frmNastavenia.getContentPane().add(comboBox_2, "cell 1 3,growx");
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(GuiWindowsList.values()));
		comboBox_3.setSelectedIndex(6);
		frmNastavenia.getContentPane().add(comboBox_3, "cell 1 4,growx");
		
		JSeparator separator_2 = new JSeparator();
		frmNastavenia.getContentPane().add(separator_2, "cell 0 5 2 1,grow");
		
		JLabel lblPrieinokNaUkladanie = new JLabel("Prie\u010Dinok na ukladanie d\u00E1t:");
		frmNastavenia.getContentPane().add(lblPrieinokNaUkladanie, "cell 0 6,alignx trailing");
		
		textField = new JTextField();
		frmNastavenia.getContentPane().add(textField, "cell 1 6,growx");
		textField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		frmNastavenia.getContentPane().add(separator_1, "cell 0 7 2 1,grow");
		
		JPanel panel = new JPanel();
		frmNastavenia.getContentPane().add(panel, "cell 0 8 2 1,grow");
		panel.setLayout(new MigLayout("", "[]", "[]"));
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(OutputStream output = new FileOutputStream("config.properties")){
					Properties prop = new Properties();
					
					prop.setProperty("BDP", textField.getText());
					prop.setProperty("SDP", textField.getText());
					prop.setProperty("LAF", comboBox.getSelectedItem().toString());
					prop.setProperty("WO1", comboBox_1.getSelectedItem().toString());
					prop.setProperty("WO2", comboBox_2.getSelectedItem().toString());
					prop.setProperty("WO3", comboBox_3.getSelectedItem().toString());
					prop.store(output, null);
					SebuLink.save(Load.BOOK_DATABASE_PATH, Load.papers);
					SebuLink.saveStudent(Load.STUDENT_DATABASE_PATH, Load.students);
					JOptionPane.showMessageDialog(null, "Kliknite na OK a spustite program znovu.");
					System.exit(0);
				}catch(IOException e1){
					JOptionPane.showMessageDialog(null, "I/O chyba, nemoûno uloûiù nastavenia.");
					frmNastavenia.dispose();
				}
				
			}
		});
		panel.add(btnOk, "cell 0 0");
		
		try(InputStream input = new FileInputStream("config.properties")){
			Properties prop = new Properties();
			
			prop.load(input);
			textField.setText(prop.getProperty("BDP"));
			
			switch(prop.getProperty("LAF")){
			case "GRAPHITE":
				comboBox.setSelectedIndex(1);
				break;
				
			case "MCWIN":
				comboBox.setSelectedIndex(2);
				break;
				
			default:
				comboBox.setSelectedIndex(0);
				break;
			}
			
			comboBox_1.setSelectedIndex(setComboBoxSwitch(prop.getProperty("WO1")));
			comboBox_2.setSelectedIndex(setComboBoxSwitch(prop.getProperty("WO2")));
			comboBox_3.setSelectedIndex(setComboBoxSwitch(prop.getProperty("WO3")));
			
		}catch(IOException e){
			System.err.println("Cannot load settings from config.properties.");
		}
		
		frmNastavenia.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frmNastavenia.setVisible(true);
		
	}
	
	private int setComboBoxSwitch(String property){
		try{
			switch(property){
			case "ADD_BOOK":
				return 0;
				
			case "ADD_STUDENT":
				return 1;	
				
			case "BOOKS_TO_RETURN":
				return 2;
				
			case "BORROW_BOOK":
				return 3;
				
			case "LIST_ALL_BOOKS":
				return 4;
				
			case "LIST_ALL_STUDENTS":
				return 5;
				
			case "NONE":
				return 6;
				
			default:
				return -1;
			}
			
		}catch(NullPointerException e){
			return -1;
		}
		
	}

}
