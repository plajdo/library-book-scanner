package io.github.shardbytes.lbs.gui.swing;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.database.Database;
import io.github.shardbytes.lbs.database.LBSDatabase;
import io.github.shardbytes.lbs.event.TableRefreshEvent;
import io.github.shardbytes.lbs.event.TableRefreshEventListener;
import io.github.shardbytes.lbs.event.TableRefreshEventOperation;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Group;
import io.github.shardbytes.lbs.objects.Person;

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
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MainMenu{
	
	private JFrame frmGymnziumLipany;
	private static JDesktopPane desktopPane;
	
	private LBSDatabase db = LBSDatabase.getInstance();
	private ClassDatabase cdb = ClassDatabase.getInstance();
	
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
			public void windowClosing(WindowEvent event)
			{
				super.windowClosing(event);
				
				Database.saveAll();
				
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
		
		JMenuItem mntmUkoni = new JMenuItem("Ulo\u017Ei\u0165 a ukon\u010Di\u0165");
		mntmUkoni.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mntmUkoni.addActionListener(e -> closeWindow());
		mnSbor.add(mntmUkoni);
		
		JMenuItem mntmUloi = new JMenuItem("Ulo\u017Ei\u0165");
		mntmUloi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmUloi.addActionListener(e -> {
			TermUtils.println("Saving database");
			try{
				Database.saveAll();
				JOptionPane.showMessageDialog(null, "Ulo\u017Een\u00E9", "Ulo\u017Ei\u0165 datab\u00E1zu", JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, "Chyba pri ukladan\u00ED datab\u00E1zy!", "Ulo\u017Ei\u0165 datab\u00E1zu", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		JSeparator separator_4 = new JSeparator();
		mnSbor.add(separator_4);
		mnSbor.add(mntmUloi);
		
		JMenuItem mntmZahodiZmeny = new JMenuItem("Zahodi\u0165 zmeny");
		mntmZahodiZmeny.addActionListener(e -> {
			int dialogResult = JOptionPane.showConfirmDialog(null, "Zahodi\u0165 a ukon\u010Di\u0165?", "Zahodi\u0165 zmeny?", JOptionPane.YES_NO_OPTION);
			if(dialogResult == JOptionPane.YES_OPTION){
				TermUtils.println("Exiting LBS");
				System.exit(0);
			}
		});
		mnSbor.add(mntmZahodiZmeny);
		
		JMenu mnKniha_1 = new JMenu("Kniha");
		menuBar.add(mnKniha_1);
		
		JMenuItem mntmPridaKnihu = new JMenuItem("Prida\u0165 knihu");
		mnKniha_1.add(mntmPridaKnihu);
		mntmPridaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmPridaKnihu.addActionListener(e -> openAddBook());
		
		JMenuItem mntmOdstrniKnihu = new JMenuItem("Odstr\u00E1ni\u0165 knihu");
		mntmOdstrniKnihu.addActionListener(e -> openRemoveBook());
		mnKniha_1.add(mntmOdstrniKnihu);
		mntmOdstrniKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		
		JMenuItem mntmPridaKnihy = new JMenuItem("Prida\u0165 knihy");
		mntmPridaKnihy.addActionListener(e -> openAddBooks());
		mnKniha_1.add(mntmPridaKnihy);
		
		JSeparator separator_1 = new JSeparator();
		mnKniha_1.add(separator_1);
		
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
		
		JMenuItem mntmPridaiaka = new JMenuItem("Prida\u0165 \u010Ditate\u013Ea");
		mntudent.add(mntmPridaiaka);
		mntmPridaiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mntmPridaiaka.addActionListener(e -> openAddStudent());
		
		JMenuItem mntmOdstrniiaka = new JMenuItem("Odstr\u00E1ni\u0165 \u010Ditate\u013Ea");
		mntmOdstrniiaka.addActionListener(e -> openRemovePerson());
		mntudent.add(mntmOdstrniiaka);
		mntmOdstrniiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		
		JSeparator separator_3 = new JSeparator();
		mntudent.add(separator_3);
		
		JMenuItem mntmZoznamtudentov = new JMenuItem("Zoznam \u010Ditate\u013Eov");
		mntmZoznamtudentov.addActionListener(e -> openListAllStudents());
		mntmZoznamtudentov.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		mntudent.add(mntmZoznamtudentov);
		
		JMenu mnTrieda = new JMenu("Trieda");
		menuBar.add(mnTrieda);
		
		JMenuItem menuItemEditTriedu = new JMenuItem("Zoznam tried");
		menuItemEditTriedu.addActionListener(e -> openEditClass());
		mnTrieda.add(menuItemEditTriedu);
		
		JMenuItem menuItemAdvanceClass = new JMenuItem("Posun\u00FA\u0165 triedy");
		menuItemAdvanceClass.addActionListener(e -> doAdvanceClass());
		mnTrieda.add(menuItemAdvanceClass);
		
		JMenu mnIn = new JMenu("In\u00E9");
		menuBar.add(mnIn);
		
		JMenu mnVymaza = new JMenu("Vymaza\u0165");
		mnIn.add(mnVymaza);
		
		JMenuItem mntmVymazatDatabazu = new JMenuItem("Vymaza\u0165 datab\u00E1zu kn\u00EDh");
		mnVymaza.add(mntmVymazatDatabazu);
		
		JMenuItem mntmVymazatOsoby = new JMenuItem("Vymaza\u0165 datab\u00E1zu pou\u017E\u00EDvate\u013Eov");
		mntmVymazatOsoby.addActionListener(e -> Load.resetUsers());
		mnVymaza.add(mntmVymazatOsoby);
		
		JMenuItem mntmVymazaDatabzuVpoiiek = new JMenuItem("Vymaza\u0165 datab\u00E1zu v\u00FDpo\u017Ei\u010Diek");
		mntmVymazaDatabzuVpoiiek.addActionListener(e -> Load.resetBorrowing());
		
		mnVymaza.add(mntmVymazaDatabzuVpoiiek);
		mntmVymazatDatabazu.addActionListener(e -> Load.resetDatabase());
		
		JMenuItem mntmVymazatTriedy = new JMenuItem("Vymaza\u0165 datab\u00E1zu tried");
		mnVymaza.add(mntmVymazatTriedy);
		mntmVymazatTriedy.addActionListener(e -> Load.resetClasses());
		
		JSeparator separator = new JSeparator();
		mnIn.add(separator);
		
		JMenu mnNastavenia = new JMenu("Nastavenia");
		mnIn.add(mnNastavenia);
		
		JCheckBoxMenuItem chckbxmntmOptimalizovaKameru = new JCheckBoxMenuItem("Optimalizova\u0165 kameru");
		chckbxmntmOptimalizovaKameru.addActionListener(e -> {
			Load.webcamOptimise = chckbxmntmOptimalizovaKameru.isSelected();
			Load.writeBoolean(Load.webcamOptimise, new File(Load.WEBCAM_OPTIMIZE_PATH));
			JOptionPane.showMessageDialog(null, "LBS je potrebn\u00E9 re\u0161tartova\u0165", "Zmena nastaven\u00ED", JOptionPane.INFORMATION_MESSAGE);
			Database.saveAll();
			System.exit(0);
		});
		chckbxmntmOptimalizovaKameru.setSelected(Load.webcamOptimise);
		mnNastavenia.add(chckbxmntmOptimalizovaKameru);
		
		JCheckBoxMenuItem checkBoxShareOnNetwork = new JCheckBoxMenuItem("Zdie\u013Ea\u0165 datab\u00E1zu");
		checkBoxShareOnNetwork.addActionListener(e -> {
			Load.shareDatabase = checkBoxShareOnNetwork.isSelected();
			Load.writeBoolean(Load.shareDatabase, new File(Load.SHARE_BOOLEAN_PATH));
			JOptionPane.showMessageDialog(null, "LBS je potrebn\u00E9 re\u0161tartova\u0165", "Zmena nastaven\u00ED", JOptionPane.INFORMATION_MESSAGE);
			Database.saveAll();
			System.exit(0);
		});
		checkBoxShareOnNetwork.setSelected(Load.shareDatabase);
		mnNastavenia.add(checkBoxShareOnNetwork);
		
		JSeparator separator_5 = new JSeparator();
		mnIn.add(separator_5);
		
		JMenuItem mntmExportova = new JMenuItem("Exportova\u0165 datab\u00E1zu");
		mntmExportova.addActionListener(e -> openExportMenu());
		
		JMenuItem mntmZoznamVpoiiek = new JMenuItem("Zoznam výpožičiek");
		mntmZoznamVpoiiek.addActionListener(e -> openBorrowingsList());
		mnIn.add(mntmZoznamVpoiiek);
		mnIn.add(mntmExportova);
		
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
			
		}catch(NullPointerException ignored){
			
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
	
	private void openAddBooks(){
		AddBooks abs = new AddBooks();
		desktopPane.add(abs);
		try{
			abs.setSelected(true);
		}catch(PropertyVetoException e1){
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
	
	private void openExportMenu(){
		Export exp = new Export();
		desktopPane.add(exp);
		try{
			exp.setSelected(true);
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
	
	private void openEditClass(){
		EditClass ec = new EditClass();
		desktopPane.add(ec);
		try{
			ec.setSelected(true);
		}catch(PropertyVetoException e1){
			e1.printStackTrace();
		}
		
	}
	
	private void doAdvanceClass(){
		if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "T\u00E1to oper\u00E1cia sa ned\u00E1 vr\u00E1ti\u0165!", "Posun\u00FA\u0165 triedy", JOptionPane.OK_CANCEL_OPTION)){
			if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Posun\u00FA\u0165 triedy", JOptionPane.OK_CANCEL_OPTION)){
				ArrayList<Person> arp = new ArrayList<>();
				
				db.persons.forEach((id, dude) -> {
					try{
						int classesInGroupCount = cdb.getClassList().get(dude.getGroup().getCategory()).size();
						int dudesGroup = cdb.getClassList().get(dude.getGroup().getCategory()).indexOf(dude.getGroup()) + 1;
						
						if(dudesGroup > classesInGroupCount){
							arp.add(dude);
						}else{
							dude.setGroup(cdb.getClassList().get(dude.getGroup().getCategory()).get(cdb.getClassList().get(dude.getGroup().getCategory()).indexOf(dude.getGroup()) + 1));
						}
						
					}catch(Exception e){
						arp.add(dude);
					}
					
				});
				
				int dudesWithBooks = 0;
				
				for(Person typek : arp){
					String[] keys = getKeys(db.persons, typek).toArray(new String[0]);
					
					if(keys.length > 0){
						for(String key : keys){
							if(db.persons.get(key).getNumBookCount() == 0){
								db.persons.remove(key);
							}else{
								dudesWithBooks++;
								db.persons.get(key).setGroup(new Group("~~CHYBA~~", "ec"));
							}
							
						}
						
					}
					
				}
				
				if(dudesWithBooks == 0){
					JOptionPane.showMessageDialog(null, "V\u0161etky triedy boli posunut\u00E9.", "Hotovo", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "V\u0161etky triedy boli posunut\u00E9. " + dudesWithBooks + " " +
									(dudesWithBooks == 1 ? "\u017Eiak m\u00E1 e\u0161te vypo\u017Ei\u010Dan\u00E9 knihy." : (dudesWithBooks > 1 && dudesWithBooks < 5 ? "\u017Eiaci maj\u00FA e\u0161te vypo\u017Ei\u010Dan\u00E9 knihy." : "\u017Eiakov m\u00E1 e\u0161te vypo\u017Ei\u010Dan\u00E9 knihy.")) + "\n" +
									(dudesWithBooks == 1 ? "Mus\u00ED by\u0165 vymazan\u00FD z datab\u00E1zy manu\u00E1lne." : "Musia by\u0165 vymazan\u00ED z datab\u00E1zy manu\u00E1lne."),
							"Hotovo", JOptionPane.WARNING_MESSAGE);
				}
				
				TermUtils.println("All classes advanced");
				dispatchTableRefreshEvent(new TableRefreshEvent(this, TableRefreshEventOperation.REFRESH));
				
			}
			
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
	
	private static void dispatchTableRefreshEvent(TableRefreshEvent evt){
		for(TableRefreshEventListener trel: listeners){
			trel.handleTableRefreshEvent(evt);
		}
		
	}
	
	private static <K, V> Set<K> getKeys(Map<K, V> map, V val){
		return map.entrySet().stream()
				.filter(e -> Objects.equals(e.getValue(), val))
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}
	
}
