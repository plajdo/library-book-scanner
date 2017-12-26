package es.esy.playdotv;

import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.gui.swing.MainMenu;

import javax.swing.*;

import java.lang.System;

public class Load
{
	
	public static final String VERSION = "v1.0.1";
	public static final String OS = "OS X";
	
	public static String DATABASE_PATH = "lbsdatabase.xml";
	private static LBSDatabase db = LBSDatabase.getInstance();
	
	public static void resetDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetovať databázu?","Reset databázy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				
				db.reset();
				db.save(DATABASE_PATH);
				
			}catch (Exception e){
				JOptionPane.showMessageDialog(null, "Chyba pri resetovaní databázy.");
				e.printStackTrace();
			}finally{
				System.exit(0);
			}
			
		}
		
	}
	
	public static void main(String[] args){
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "LBS " + VERSION);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.exit(-1);
		}
		
		db.load(DATABASE_PATH);
		
		MainMenu.open();
		
	}
	
}
