package io.github.shardbytes.lbs;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.database.LBSDatabase;

import io.github.shardbytes.lbs.gui.swing.LookAndFeelSettingsList;
import io.github.shardbytes.lbs.gui.swing.MainMenu;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Load{
	
	public static final String VERSION = "v1.1.1";
	
	public static String DATABASE_PATH;
	public static String B_DATABASE_PATH;

	private static LBSDatabase db = LBSDatabase.getInstance();
	private static BorrowDatabase bdb = BorrowDatabase.getInstance();
	
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
	
	public static void resetBorrowing(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj vymaza\u0165 datab\u00E1zu? Tento krok sa ned\u00E1 vr\u00E1ti\u0165!","Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			int dialog2Result = JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
			if(dialog2Result == JOptionPane.YES_OPTION){
				try{
					bdb.reset();
					bdb.save(B_DATABASE_PATH);
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
		begin(args[0], args[1]);
	}
	
	/**
	 * Method that loads everything and starts the GUI.
	 * @param db_path Path to the main database file
	 * @param b_db_path Path and name of the second database file
	 */
	public static void begin(String db_path, String b_db_path){
		splashProgress(0);
		splashText("Pre-Initialisation");
		
		DATABASE_PATH = db_path;
		B_DATABASE_PATH = b_db_path;
		
		TermUtils.init();
		if(System.console() == null){
			System.setProperty("jansi.passthrough", "true");
		}
		
		splashProgress(20);
		splashText("Initialisation");
		
		TermUtils.println("Loading databases");
		db.load(DATABASE_PATH);
		bdb.load(B_DATABASE_PATH);
		
		splashProgress(40);
		splashText("Initialisation");
		
		Runnable autosave = () -> {
			Thread t = Thread.currentThread();
			t.setName("Thread-Autosave");
			TermUtils.println("Autosave running");
			while(1 < 2){
				try{
					Thread.sleep(300000);
					TermUtils.println("Saving database");
					saveDatabase();
				}catch(InterruptedException e){
					TermUtils.println("Autosave thread " + t.getName() + " stopped");
				}
			}
		};
		Thread as = new Thread(autosave);
		as.setDaemon(true);
		as.start();
		
		splashProgress(60);
		splashText("Post-Initialisation");
		
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
		
		splashProgress(80);
		splashText("Post-Initialisation");
		
		MainMenu.open();
		
		splashProgress(100);
		splashText("Finishing");
		
	}
	
	private static synchronized void saveDatabase(){
		try{
			db.save(DATABASE_PATH);
			bdb.save(B_DATABASE_PATH);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Chyba pri automatickom ulo\u017Een\u00ED datab\u00E1zy!", "Ulo\u017Ei\u0165 datab\u00E1zu", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private static void splashProgress(int pct){
		try{
			SplashScreen sp = SplashScreen.getSplashScreen();
			if(sp != null && sp.isVisible()){
				Graphics2D splashGraphics = sp.createGraphics();
				Dimension bounds = sp.getSize();
				Rectangle2D splashProgressArea = new Rectangle2D.Double(bounds.getWidth() * 0.55d, bounds.getHeight() * 0.92d, bounds.getWidth() * 0.40d, 12);
				splashGraphics.setPaint(Color.LIGHT_GRAY);
				splashGraphics.fill(splashProgressArea);

				splashGraphics.setPaint(Color.BLUE);
				splashGraphics.draw(splashProgressArea);

				int x = (int) splashProgressArea.getMinX();
				int y = (int) splashProgressArea.getMinY();
				int wid = (int) splashProgressArea.getWidth();
				int hgt = (int) splashProgressArea.getHeight();

				int doneWidth = Math.round(pct*wid/100.0f);
				doneWidth = Math.max(0, Math.min(doneWidth, wid-1));

				splashGraphics.setPaint(Color.GREEN);
				splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);
				
				sp.update();

			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private static void splashText(String str){
		try{
			SplashScreen sp = SplashScreen.getSplashScreen();
			if(sp != null && sp.isVisible()){
				Graphics2D splashGraphics = sp.createGraphics();
				Dimension bounds = sp.getSize();
				Rectangle2D splashTextArea = new Rectangle2D.Double(15.0d, bounds.getHeight() * 0.88d, bounds.getWidth() * 0.45d, 32.0d);

				splashGraphics.setPaint(Color.BLACK);
				splashGraphics.fill(splashTextArea);

				splashGraphics.setPaint(Color.WHITE);
				splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));
				
				sp.update();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
}
