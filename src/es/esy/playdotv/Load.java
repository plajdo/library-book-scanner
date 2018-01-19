package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.document.BorrowingEntry;
import es.esy.playdotv.document.Table;
import es.esy.playdotv.gui.swing.LookAndFeelSettingsList;
import es.esy.playdotv.gui.swing.MainMenu;
import es.esy.playdotv.gui.terminal.TermUtils;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class Load
{
	
	public static final String VERSION = "v1.1.0 PRE-RELEASE";
	
	public static String DATABASE_PATH = "lbsdatabase.xml";
	private static LBSDatabase db = LBSDatabase.getInstance();
	
	static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.GRAPHITE;
	
	public static void resetDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj vymaza\u0165 datab\u00E1zu? Tento krok sa ned\u00E1 vr\u00E1ti\u0165!","Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			int dialog2Result = JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
			if(dialog2Result == JOptionPane.YES_OPTION){
				try{
					db.reset();
					db.save(DATABASE_PATH);
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Chyba pri mazan\u00ED datab\u00E1zy.");
					e.printStackTrace();
				}finally{
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	public static void main(String[] args){
		
		if(System.console() == null){
			System.setProperty("jansi.passthrough", "true");
		}
		
		TermUtils.println("Loading database");
		db.load(DATABASE_PATH);
		
		Runnable autosave = () -> {
			Thread t = Thread.currentThread();
			TermUtils.println("Autosave running");
			while(1 < 2){
				try{
					Thread.sleep(60000);
					TermUtils.println("Saving database");
					saveDatabase();
				}catch(InterruptedException e){
					TermUtils.println("Autosave thread " + t.getName() + " stopped");
				}
			}
		};
		//new Thread(autosave).start();
		
		switch(LAF){
		case MCWIN:
			try{
				McWinLookAndFeel.setTheme("Modern", "", "");
				UIManager.setLookAndFeel(new McWinLookAndFeel());
			}catch(Exception e){
				e.printStackTrace();
			}
			TermUtils.println("Using McWin theme.");
			break;
			
		case GRAPHITE:
			try{
				GraphiteLookAndFeel.setTheme("Default", "", "");
				UIManager.setLookAndFeel(new GraphiteLookAndFeel());
			}catch(Exception e){
				e.printStackTrace();
			}
			TermUtils.println("Using Graphite theme.");
			break;
			
		default:
			TermUtils.println("Using Metal theme");
			break;
		}
		
		//MainMenu.open();
		
		ArrayList<BorrowingEntry> entryList = new ArrayList<BorrowingEntry>();
		entryList.add(new BorrowingEntry(new Date(3281903), new Date(), "Filip Šašala", "Antigona", "172/B2980"));
		entryList.add(new BorrowingEntry(new Date(3281903), new Date(), "Random Týpek", "Antigona", "172/B2980"));
		
		try {
			Table.createTable(entryList, "Kvinta");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static synchronized void saveDatabase(){
		try{
			db.save(DATABASE_PATH);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Chyba pri automatickom ulo\u017Een\u00ED datab\u00E1zy!", "Ulo\u017Ei\u0165 datab\u00E1zu", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
