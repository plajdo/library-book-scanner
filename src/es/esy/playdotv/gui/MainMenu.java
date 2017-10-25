package es.esy.playdotv.gui;

import com.unaux.plasmoxy.libscan.database.SebuLink;
import es.esy.playdotv.Load;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MainMenu {
	
	private JFrame frmGymnziumLipany;
	
	public static void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	
	private void initialize() {
		frmGymnziumLipany = new JFrame();
		frmGymnziumLipany.setTitle("Gymn\u00E1zium Lipany - Kni\u017Enica");
		frmGymnziumLipany.setBounds(100, 100, 1280, 721);
		frmGymnziumLipany.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmGymnziumLipany.getContentPane().setLayout(new MigLayout("", "[][][grow]", "[grow]"));
		JDesktopPane desktopPane = new JDesktopPane();
		frmGymnziumLipany.getContentPane().add(desktopPane, "cell 2 0,grow");
		
		JMenuBar menuBar = new JMenuBar();
		frmGymnziumLipany.setJMenuBar(menuBar);
		
		JMenu mnSbor = new JMenu("S\u00FAbor");
		menuBar.add(mnSbor);
		
		JMenuItem mntmUkoni = new JMenuItem("Ukon\u010Di\u0165");
		mntmUkoni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SebuLink.save(Load.BOOK_DATABASE_PATH, Load.papers);
					SebuLink.saveStudent(Load.STUDENT_DATABASE_PATH, Load.students);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		mnSbor.add(mntmUkoni);
		
		JMenu mnIn = new JMenu("In\u00E9");
		menuBar.add(mnIn);
		
		JMenu mnKniha = new JMenu("Kniha");
		mnIn.add(mnKniha);
		
		JMenuItem mntmOdstrniKnihu = new JMenuItem("Odstr\u00E1ni\u0165 knihu");
		mntmOdstrniKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnKniha.add(mntmOdstrniKnihu);
		
		JMenuItem mntmPridaKnihu = new JMenuItem("Prida\u0165 knihu");
		mntmPridaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmPridaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddBook ab = new AddBook();
				desktopPane.add(ab);
			}
		});
		mnKniha.add(mntmPridaKnihu);
		
		JMenu mnUite = new JMenu("U\u010Dite\u013E");
		mnIn.add(mnUite);
		
		JMenuItem mntmOdstrniUitea = new JMenuItem("Odstr\u00E1ni\u0165 u\u010Dite\u013Ea");
		mntmOdstrniUitea.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnUite.add(mntmOdstrniUitea);
		
		JMenuItem mntmPridaUitea = new JMenuItem("Prida\u0165 u\u010Dite\u013Ea");
		mntmPridaUitea.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mnUite.add(mntmPridaUitea);
		
		JMenu mniak = new JMenu("\u017Diak");
		mnIn.add(mniak);
		
		JMenuItem mntmOdstrniiaka = new JMenuItem("Odstr\u00E1ni\u0165 \u017Eiaka");
		mntmOdstrniiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mniak.add(mntmOdstrniiaka);
		
		JMenuItem mntmPridaiaka = new JMenuItem("Prida\u0165 \u017Eiaka");
		mntmPridaiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmPridaiaka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStudent as = new AddStudent();
				desktopPane.add(as);
			}
		});
		mniak.add(mntmPridaiaka);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JButton btnVypoiiaKnihu = new JButton("Vypo\u017Ei\u010Dia\u0165 knihu");
		btnVypoiiaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowBook bb = new BorrowBook(desktopPane);
				desktopPane.add(bb);
				try{
					bb.setSelected(true);
				}catch(PropertyVetoException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		frmGymnziumLipany.getContentPane().add(btnVypoiiaKnihu, "flowy,cell 0 0,growx,aligny center");
		
		JButton btnVrtiKnihu = new JButton("Vr\u00E1ti\u0165 knihu");
		frmGymnziumLipany.getContentPane().add(btnVrtiKnihu, "cell 0 0,growx,aligny center");
		
		JButton btnKnihyNaVrtenie = new JButton("Knihy na vr\u00E1tenie");
		frmGymnziumLipany.getContentPane().add(btnKnihyNaVrtenie, "cell 0 0,growx,aligny center");
		
		JButton btnZoznamiakov = new JButton("Zoznam \u017Eiakov");
		btnZoznamiakov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListAllStudents las = new ListAllStudents();
				desktopPane.add(las);
			}
		});
		frmGymnziumLipany.getContentPane().add(btnZoznamiakov, "cell 0 0,growx,aligny center");
		
		JButton btnZoznamUiteov = new JButton("Zoznam u\u010Dite\u013Eov");
		frmGymnziumLipany.getContentPane().add(btnZoznamUiteov, "cell 0 0,growx,aligny center");
		
		JButton btnZoznamKnh = new JButton("Zoznam kn\u00EDh");
		btnZoznamKnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListAllBooks lab = new ListAllBooks();
				desktopPane.add(lab);
				try{
					lab.setSelected(true);
				}catch(PropertyVetoException e1){
					e1.printStackTrace();
				}
			}
		});
		frmGymnziumLipany.getContentPane().add(btnZoznamKnh, "cell 0 0,growx,aligny center");
		
		JButton btnPomoc = new JButton("Pomoc");
		btnPomoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ctrl+Q = Odstr·niù knihu\nCtrl+W = Pridaù knihu\nCtrl+A = Odstr·niù ûiaka\nCtrl+S = Pridaù ûiaka\nCtrl+E = Odstr·niù uËiteæa\nCtrl+D = Pridaù uËiteæa", "Pomoc", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		frmGymnziumLipany.getContentPane().add(btnPomoc, "cell 0 0,growx,aligny center");
		
	}

}
