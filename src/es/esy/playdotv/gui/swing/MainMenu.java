package es.esy.playdotv.gui.swing;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.Load;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

public class MainMenu {
	
	private JFrame frmGymnziumLipany;
	private JDesktopPane desktopPane;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	
	public static void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmGymnziumLipany.setLocationRelativeTo(null);
					window.frmGymnziumLipany.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainMenu() {
		initialize();
	}

	public void closeWindow()
	{
		frmGymnziumLipany.dispatchEvent(new WindowEvent(frmGymnziumLipany, WindowEvent.WINDOW_CLOSING));
	}
	
	private void initialize() {

		frmGymnziumLipany = new JFrame();
		frmGymnziumLipany.setTitle("ShardBytes Library Book Scanner - [" + Load.VERSION + "] [SK]");
		frmGymnziumLipany.setBounds(100, 100, 1280, 720);
		frmGymnziumLipany.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmGymnziumLipany.setMinimumSize(new Dimension(650, 365)); // minsize
		
		// ---------EXIT--------

		frmGymnziumLipany.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				super.windowClosing(event);
				
				db.save(Load.DATABASE_PATH);
				
				System.exit(0);
					
			}
		});
		
		
		//-----------------
		
		frmGymnziumLipany.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		desktopPane = new ImageDesktopPane();
		
		frmGymnziumLipany.getContentPane().add(desktopPane, "cell 0 0,grow");
		
		JMenuBar menuBar = new JMenuBar();
		frmGymnziumLipany.setJMenuBar(menuBar);
		
		JMenu mnSbor = new JMenu("S\u00FAbor");
		menuBar.add(mnSbor);
		
		JMenuItem mntmUkoni = new JMenuItem("Ukon\u010Di\u0165");
		mntmUkoni.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mntmUkoni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();
			}
		});
		mnSbor.add(mntmUkoni);
		
		JMenuItem mntmUloi = new JMenuItem("Ulo\u017Ei\u0165");
		mntmUloi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmUloi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db.save(Load.DATABASE_PATH);
			}
		});
		
		JSeparator separator_4 = new JSeparator();
		mnSbor.add(separator_4);
		mnSbor.add(mntmUloi);
		
		JMenuItem mntmZahodiZmeny = new JMenuItem("Zahodiť zmeny");
		mntmZahodiZmeny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Zahodi\u0165 a ukon\u010Di\u0165?");
				if(dialogResult == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});
		mnSbor.add(mntmZahodiZmeny);
		
		JMenu mnKniha_1 = new JMenu("Kniha");
		menuBar.add(mnKniha_1);
		
		JMenuItem mntmPridaKnihu = new JMenuItem("Prida\u0165 knihu");
		mnKniha_1.add(mntmPridaKnihu);
		mntmPridaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmPridaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAddBook();
			}
		});
		
		JMenuItem mntmOdstrniKnihu = new JMenuItem("Odstr\u00E1ni\u0165 knihu");
		mntmOdstrniKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openRemoveBook();
			}
			
		});
		mnKniha_1.add(mntmOdstrniKnihu);
		mntmOdstrniKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		
		JSeparator separator_1 = new JSeparator();
		mnKniha_1.add(separator_1);
		
		JMenuItem mntmVypoiaKnihu = new JMenuItem("Vypo\u017Ei\u010Da\u0165 knihu");
		mntmVypoiaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBorrowBook();
			}
		});
		mntmVypoiaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mnKniha_1.add(mntmVypoiaKnihu);
		
		JMenuItem mntmVrtiKnihu = new JMenuItem("Vr\u00E1ti\u0165 knihu");
		mntmVrtiKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openReturnBook();
			}
		});
		mntmVrtiKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mnKniha_1.add(mntmVrtiKnihu);
		
		JSeparator separator_2 = new JSeparator();
		mnKniha_1.add(separator_2);
		
		JMenuItem mntmZoznamKnh = new JMenuItem("Zoznam kn\u00EDh");
		mntmZoznamKnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openListAllBooks();
			}
		});
		mntmZoznamKnh.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mnKniha_1.add(mntmZoznamKnh);
		
		JMenuItem mntmKnihyNaVrtenie = new JMenuItem("Knihy na vr\u00E1tenie");
		mntmKnihyNaVrtenie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openBooksToReturn();
			}
		});
		mntmKnihyNaVrtenie.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.SHIFT_MASK));
		mnKniha_1.add(mntmKnihyNaVrtenie);
		
		JMenu mntudent = new JMenu("\u010Citate\u013E");
		menuBar.add(mntudent);
		
		JMenuItem mntmPridaiaka = new JMenuItem("Prida\u0165 \u010Ditate\u013Ea");
		mntudent.add(mntmPridaiaka);
		mntmPridaiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mntmPridaiaka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddStudent();
			}
			
		});
		
		JMenuItem mntmOdstrniiaka = new JMenuItem("Odstr\u00E1ni\u0165 \u010Ditate\u013Ea");
		mntmOdstrniiaka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openRemovePerson();
			}
		});
		mntudent.add(mntmOdstrniiaka);
		mntmOdstrniiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		
		JSeparator separator_3 = new JSeparator();
		mntudent.add(separator_3);
		
		JMenuItem mntmZoznamtudentov = new JMenuItem("Zoznam \u010Ditate\u013Eov");
		mntmZoznamtudentov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openListAllStudents();
			}
		});
		mntmZoznamtudentov.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		mntudent.add(mntmZoznamtudentov);
		
		JMenu mnIn = new JMenu("In\u00E9");
		menuBar.add(mnIn);
		
		JMenu mnVymaza = new JMenu("Vymaza\u0165");
		mnIn.add(mnVymaza);
		
		JMenuItem mntmVymazatDatabazu = new JMenuItem("Vymaza\u0165 datab\u00E1zu");
		mnVymaza.add(mntmVymazatDatabazu);
		mntmVymazatDatabazu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Load.resetDatabase();
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
	}
	
	@SuppressWarnings("unused")
	private void openWindow(String property){
		try{
			switch(property){
			case "ADD_BOOK":
				openAddBook();
				break;
				
			case "ADD_STUDENT":
				openAddStudent();
				break;	
				
			case "BOOKS_TO_RETURN":
				openBooksToReturn();
				break;
				
			case "BORROW_BOOK":
				openBorrowBook();
				break;
				
			case "LIST_ALL_BOOKS":
				openListAllBooks();
				break;
				
			case "LIST_ALL_STUDENTS":
				openListAllStudents();
				break;
				
			case "NONE":
				break;
				
			default:
				break;
			}
			
		}catch(NullPointerException e){
			
		}
		
	}
	
	private void openAddBook(){
		AddBook ab = new AddBook();
		desktopPane.add(ab);
		try{
			ab.setSelected(true);
		}catch(PropertyVetoException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void openAddStudent(){
		AddPerson as = new AddPerson();
		desktopPane.add(as);
		try{
			as.setSelected(true);
		}catch(PropertyVetoException e1) {
			e1.printStackTrace();
		}
		
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
	
	private void openRemovePerson(){
		RemovePerson rp = new RemovePerson();
		desktopPane.add(rp);
		try{
			rp.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
	}
	
	private void openRemoveBook(){
		RemoveBook rb = new RemoveBook();
		desktopPane.add(rb);
		try{
			rb.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
	}
}
