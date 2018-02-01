package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.LBSDatabase;

import es.esy.playdotv.document.BorrowingsDatabase;
import es.esy.playdotv.gui.swing.LookAndFeelSettingsList;
import es.esy.playdotv.gui.swing.MainMenu;
import es.esy.playdotv.gui.terminal.TermUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

public class Load{
	
	public static final String VERSION = "v1.1.0 PRE-RELEASE";
	
	public static final String DATABASE_PATH = "lbsdatabase.xml";
	public static final String B_DATABASE_PATH = "lbsdatabase2.json";
	private static LBSDatabase db = LBSDatabase.getInstance();
	private static BorrowingsDatabase dbBorrowings = BorrowingsDatabase.getInstance();
	
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
		
		splashProgress(0);
		splashText("Terminal initialisation");
		
		TermUtils.init();
		if(System.console() == null){
			System.setProperty("jansi.passthrough", "true");
		}
		
		splashProgress(20);
		splashText("Loading database");
		
		TermUtils.println("Loading database");
		db.load(DATABASE_PATH);
		dbBorrowings.load(B_DATABASE_PATH);
		
		splashProgress(40);
		splashText("Running autosave");
		
		Runnable autosave = () -> {
			Thread t = Thread.currentThread();
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
		new Thread(autosave).start();
		
		splashProgress(60);
		splashText("Loading themes");
		
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
		splashText("Setting up the menu");
		
		MainMenu.open();
		
		splashProgress(100);
		splashText("Finishing");
		
	}
	
	private static synchronized void saveDatabase(){
		try{
			db.save(DATABASE_PATH);
			dbBorrowings.save(B_DATABASE_PATH);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Chyba pri automatickom ulo\u017Een\u00ED datab\u00E1zy!", "Ulo\u017Ei\u0165 datab\u00E1zu", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private static void splashProgress(int pct){
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

		}else{
			try{
				sp.setImageURL(new URL("/src/res/splash.png"));
				splashProgress(pct);
			}catch(NullPointerException | IllegalStateException | IOException e){
				TermUtils.printerr("Splash load failed");
				/*
				 * TODO: Fix this error (shows when database is not found)
				 */
			}
			
		}

	}

	private static void splashText(String str){
		SplashScreen sp = SplashScreen.getSplashScreen();
		if(sp != null && sp.isVisible()){
			Graphics2D splashGraphics = sp.createGraphics();
			Dimension bounds = sp.getSize();
			Rectangle2D splashTextArea = new Rectangle2D.Double(15.0d, bounds.getHeight() * 0.88d, bounds.getWidth() * 0.45d, 32.0d);

			splashGraphics.setPaint(Color.BLACK);
			splashGraphics.fill(splashTextArea);

			splashGraphics.setPaint(Color.WHITE);
			splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));
		}else{
			try{
				sp.setImageURL(new URL("/src/res/splash.png"));
				splashText(str);
			}catch(NullPointerException | IllegalStateException | IOException e){
				TermUtils.printerr("Splash load failed");
				/*
				 * TODO: Fix this error (shows when database is not found)
				 */
			}
			
		}

	}
	
}
