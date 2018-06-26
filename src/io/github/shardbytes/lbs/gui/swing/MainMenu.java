package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.database.WebDB;
import io.github.shardbytes.lbs.event.TableRefreshEventListener;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMenu{
	
	private JFrame frmGymnziumLipany;
	private static JDesktopPane desktopPane;
	
	static List<TableRefreshEventListener> listeners = new ArrayList<>();
	
	public static void open() {
		EventQueue.invokeLater(() -> {
			try {
				MainMenu window = new MainMenu();
				window.frmGymnziumLipany.setLocationRelativeTo(null);
				window.frmGymnziumLipany.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		});
		
	}
	
	private MainMenu() {
		initialize();
	}

	private void closeWindow(){
		frmGymnziumLipany.dispatchEvent(new WindowEvent(frmGymnziumLipany, WindowEvent.WINDOW_CLOSING));
	}
	
	private void initialize() {
		frmGymnziumLipany = new JFrame();
		frmGymnziumLipany.setTitle("ShardBytes Library Book Scanner - [" + Load.VERSION + "] [SK]");
		frmGymnziumLipany.setBounds(100, 100, 1280, 720);
		frmGymnziumLipany.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmGymnziumLipany.setMinimumSize(new Dimension(650, 365));
		frmGymnziumLipany.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent event){
				super.windowClosing(event);
				
				WebDB.getInstance().close();
				
				TermUtils.println("Exiting LBS");
				System.exit(0);
					
			}
			
		});
		frmGymnziumLipany.getContentPane().setLayout(new BorderLayout(0, 0));
		
		desktopPane = new ImageDesktopPane();
		
		frmGymnziumLipany.getContentPane().add(desktopPane);
		
		JMenuBar menuBar = new JMenuBar();
		frmGymnziumLipany.setJMenuBar(menuBar);
		
		JMenu mnSbor = new JMenu("S\u00FAbor");
		menuBar.add(mnSbor);
		
		JMenuItem mntmUkoni = new JMenuItem("Ukon\u010Di\u0165");
		mntmUkoni.addActionListener(e -> closeWindow());
		mnSbor.add(mntmUkoni);
		
		JMenu mnKniha_1 = new JMenu("Kniha");
		menuBar.add(mnKniha_1);
		
		JMenuItem mntmVypoiaKnihu = new JMenuItem("Vypo\u017Ei\u010Da\u0165 knihu");
		mntmVypoiaKnihu.addActionListener(e -> openBorrowBook());
		mntmVypoiaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnKniha_1.add(mntmVypoiaKnihu);
		
		JMenuItem mntmVrtiKnihu = new JMenuItem("Vr\u00E1ti\u0165 knihu");
		mntmVrtiKnihu.addActionListener(e -> openReturnBook());
		mntmVrtiKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mnKniha_1.add(mntmVrtiKnihu);
		
		JSeparator separator_2 = new JSeparator();
		mnKniha_1.add(separator_2);
		
		JMenuItem mntmZoznamKnh = new JMenuItem("Zoznam kn\u00EDh");
		mntmZoznamKnh.addActionListener(e -> openListAllBooks());
		mntmZoznamKnh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mnKniha_1.add(mntmZoznamKnh);
		
		JMenuItem mntmKnihyNaVrtenie = new JMenuItem("Knihy na vr\u00E1tenie");
		mntmKnihyNaVrtenie.addActionListener(e -> openBooksToReturn());
		mntmKnihyNaVrtenie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.SHIFT_MASK));
		mnKniha_1.add(mntmKnihyNaVrtenie);
		
		JMenu mntudent = new JMenu("\u010Citate\u013E");
		menuBar.add(mntudent);
		
		JMenuItem mntmZoznamtudentov = new JMenuItem("Zoznam \u010Ditate\u013Eov");
		mntmZoznamtudentov.addActionListener(e -> openListAllStudents());
		mntmZoznamtudentov.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		mntudent.add(mntmZoznamtudentov);
		
		JMenu mnIn = new JMenu("In\u00E9");
		menuBar.add(mnIn);
		
		JCheckBoxMenuItem chckbxmntmOptimalizovaKameru = new JCheckBoxMenuItem("Optimalizova\u0165 kameru");
		chckbxmntmOptimalizovaKameru.addActionListener(e -> {
			Load.webcamOptimise = chckbxmntmOptimalizovaKameru.isSelected();
			Load.writeBoolean(Load.webcamOptimise, new File(Load.WEBCAM_OPTIMIZE_PATH));
			JOptionPane.showMessageDialog(null, "LBS je potrebn\u00E9 re\u0161tartova\u0165", "Zmena nastaven\u00ED", JOptionPane.INFORMATION_MESSAGE);
			WebDB.getInstance().close();
			System.exit(0);
		});
		chckbxmntmOptimalizovaKameru.setSelected(Load.webcamOptimise);
		mnIn.add(chckbxmntmOptimalizovaKameru);
		
		JSeparator separator_5 = new JSeparator();
		mnIn.add(separator_5);
		
		JMenuItem mntmZoznamVpoiiek = new JMenuItem("Zoznam výpožičiek");
		mntmZoznamVpoiiek.addActionListener(e -> openBorrowingsList());
		mnIn.add(mntmZoznamVpoiiek);
		
	}
	
	private void openBooksToReturn(){
		BooksToReturn btr = new BooksToReturn();
		desktopPane.add(btr);
		try{
			btr.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
		
	}
	
	private void openBorrowBook(){
		BorrowBook bb = new BorrowBook(desktopPane);
		desktopPane.add(bb);
		try{
			bb.setSelected(true);
		}catch(PropertyVetoException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void openListAllBooks(){
		ListAllBooks lab = new ListAllBooks();
		desktopPane.add(lab);
		try{
			lab.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
		
	}
	
	private void openListAllStudents(){
		ListAllPersons las = new ListAllPersons();
		desktopPane.add(las);
		try{
			las.setSelected(true);
		}catch(PropertyVetoException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void openReturnBook(){
		ReturnBook rb = new ReturnBook(desktopPane);
		desktopPane.add(rb);
		try{
			rb.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
		
	}
	
	private void openBorrowingsList(){
		BorrowingsList bl = new BorrowingsList();
		desktopPane.add(bl);
		try{
			bl.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
		
	}

	static JDesktopPane getDesktopPane() {
		return desktopPane;
	}
	
	public static void addDataDialogListener(TableRefreshEventListener trel){
		if(!listeners.contains(trel)){
			listeners.add(trel);
		}
		
	}
	
}
