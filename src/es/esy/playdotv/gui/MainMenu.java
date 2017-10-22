package es.esy.playdotv.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.awt.event.InputEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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
				System.exit(0);
			}
		});
		mntmUkoni.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnSbor.add(mntmUkoni);
		
		JMenu mnIn = new JMenu("In\u00E9");
		menuBar.add(mnIn);
		
		JMenu mnKniha = new JMenu("Kniha");
		mnIn.add(mnKniha);
		
		JMenuItem mntmOdstrniKnihu = new JMenuItem("Odstr\u00E1ni\u0165 knihu");
		mntmOdstrniKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		mnKniha.add(mntmOdstrniKnihu);
		
		JMenuItem mntmPridaKnihu = new JMenuItem("Prida\u0165 knihu");
		mntmPridaKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddBook ab = new AddBook();
				desktopPane.add(ab);
			}
		});
		mntmPridaKnihu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0));
		mnKniha.add(mntmPridaKnihu);
		
		JMenu mnUite = new JMenu("U\u010Dite\u013E");
		mnIn.add(mnUite);
		
		JMenuItem mntmOdstrniUitea = new JMenuItem("Odstr\u00E1ni\u0165 u\u010Dite\u013Ea");
		mntmOdstrniUitea.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
		mnUite.add(mntmOdstrniUitea);
		
		JMenuItem mntmPridaUitea = new JMenuItem("Prida\u0165 u\u010Dite\u013Ea");
		mntmPridaUitea.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
		mnUite.add(mntmPridaUitea);
		
		JMenu mniak = new JMenu("\u017Diak");
		mnIn.add(mniak);
		
		JMenuItem mntmOdstrniiaka = new JMenuItem("Odstr\u00E1ni\u0165 \u017Eiaka");
		mntmOdstrniiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
		mniak.add(mntmOdstrniiaka);
		
		JMenuItem mntmPridaiaka = new JMenuItem("Prida\u0165 \u017Eiaka");
		mntmPridaiaka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
		mniak.add(mntmPridaiaka);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		JButton btnVrtiKnihu = new JButton("Vr\u00E1ti\u0165 knihu");
		frmGymnziumLipany.getContentPane().add(btnVrtiKnihu, "flowy,cell 0 0,growx,aligny center");
		
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
		frmGymnziumLipany.getContentPane().add(btnVypoiiaKnihu, "cell 0 0,growx,aligny center");
		
		JButton btnKnihyNaVrtenie = new JButton("Knihy na vr\u00E1tenie");
		frmGymnziumLipany.getContentPane().add(btnKnihyNaVrtenie, "cell 0 0,growx,aligny center");
		
		JButton btnZoznamiakov = new JButton("Zoznam \u017Eiakov");
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
		
		JButton btnPridatKnihu = new JButton("Prida\u0165 knihu");
		btnPridatKnihu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		frmGymnziumLipany.getContentPane().add(btnPridatKnihu, "cell 0 0,growx,aligny center");
		
	}

}
