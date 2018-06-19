package io.github.shardbytes.lbs;

import com.github.sarxos.webcam.Webcam;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import io.github.shardbytes.lbs.database.BorrowDatabase;
import io.github.shardbytes.lbs.database.ClassDatabase;
import io.github.shardbytes.lbs.database.DBZipper;
import io.github.shardbytes.lbs.database.Database;
import io.github.shardbytes.lbs.database.LBSDatabase;
import io.github.shardbytes.lbs.gui.swing.LookAndFeelSettingsList;
import io.github.shardbytes.lbs.gui.swing.MainMenu;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import net.lingala.zip4j.exception.ZipException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Load{
	
	public static final String VERSION = "v1.2.2";
	
	public static String DATABASE_PATH;
	public static String B_DATABASE_PATH;
	public static String C_DATABASE_PATH;
	public static String WEBCAM_OPTIMIZE_PATH;
	public static String ZIP_PATH;
	
	public static boolean webcamOptimise;

	private static LBSDatabase db = LBSDatabase.getInstance();
	private static BorrowDatabase bdb = BorrowDatabase.getInstance();
	private static ClassDatabase cdb = ClassDatabase.getInstance();
	private static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.GRAPHITE;
	
	public static void main(String[] args){
		begin(args[0]);
	}
	
	/**
	 * Method that loads everything and starts the GUI.
	 * @param zipPath Path to a zip file with everything
	 */
	private static void begin(String zipPath){
		splashProgress(0);
		splashText("Pre-Initialisation");
		
		ZIP_PATH = zipPath;
		
		DATABASE_PATH = "data" + File.separator + "lbsdatabase.xml";
		B_DATABASE_PATH = "data" + File.separator + "borrowdatabase.xml";
		C_DATABASE_PATH = "data" + File.separator + "classdatabase.json";
		WEBCAM_OPTIMIZE_PATH = "data" + File.separator + "webcamsettings.ser";
		
		/*
		 * Init coloured printer
		 */
		TermUtils.init();
		if(System.console() == null){
			System.setProperty("jansi.passthrough", "true");
		}
		
		splashProgress(20);
		splashText("Initialisation");
		
		TermUtils.println("Loading databases");
		
		/*
		 * Check if "data" folder exists, if not, create it
		 */
		try{
			if(!(new File("data").isDirectory()) ||
					!(new File("data").exists())){
				Files.createDirectories(Paths.get("data"));
			}
		}catch(IOException e){
			TermUtils.println("Data folder already exists");
		}
		
		/*
		 * Unzip or fail when zip does not exist
		 */
		try{
			if(new File(ZIP_PATH).exists()){
				DBZipper.getInstance().unzipAll(ZIP_PATH);
			}
		}catch(ZipException e){
			TermUtils.printerr(e.getMessage());
			JOptionPane.showMessageDialog(null, "Fatal exception:\n" + e.getMessage());
			throw new Error("Unrecoverable error");
		}
		
		db.load(DATABASE_PATH);
		bdb.load(B_DATABASE_PATH);
		cdb.load(C_DATABASE_PATH);
		
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
					break;
				}
				
			}
			
		};
		Thread as = new Thread(autosave);
		as.setDaemon(true);
		as.start();
		
		TermUtils.println("Loading configs");
		webcamOptimise = readBoolean(new File(WEBCAM_OPTIMIZE_PATH));
		
		splashProgress(60);
		splashText("Post-Initialisation");
		
		Database.flagTemp(DATABASE_PATH,
				B_DATABASE_PATH,
				C_DATABASE_PATH,
				WEBCAM_OPTIMIZE_PATH);
		
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
		
		try{
			if(webcamOptimise){
				Webcam w = Webcam.getDefault();
				w.open();
				w.getImage();
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Pripojte webkameru.", "Chyba", JOptionPane.ERROR_MESSAGE);
			webcamOptimise = false;
			writeBoolean(webcamOptimise, new File(WEBCAM_OPTIMIZE_PATH));
		}
		
		MainMenu.open();
		
		splashProgress(100);
		splashText("Finishing");
		
	}
	
	private static synchronized void saveDatabase(){
		try{
			Database.saveAll();
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
	
	public static void resetDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj vymaza\u0165 datab\u00E1zu? Tento krok sa ned\u00E1 vr\u00E1ti\u0165!","Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			int dialog2Result = JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
			if(dialog2Result == JOptionPane.YES_OPTION){
				try{
					db.resetBook();
					Database.saveAll();
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Chyba pri mazan\u00ED datab\u00E1zy:\n" + e.getMessage());
				}finally{
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	public static void resetUsers(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj vymaza\u0165 datab\u00E1zu? Tento krok sa ned\u00E1 vr\u00E1ti\u0165!", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			int dialog2Result = JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
			if(dialog2Result == JOptionPane.YES_OPTION){
				try{
					db.resetPerson();
					Database.saveAll();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Chyba pri mazan\u00ED datab\u00E1zy:\n" + e.getMessage());
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
					Database.saveAll();
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Chyba pri mazan\u00ED datab\u00E1zy:\n" + e.getMessage());
				}finally{
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	public static void resetClasses(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj vymaza\u0165 datab\u00E1zu? Tento krok sa ned\u00E1 vr\u00E1ti\u0165!","Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			int dialog2Result = JOptionPane.showConfirmDialog(null, "Ste si ist\u00FD/\u00E1?", "Vymaza\u0165 datab\u00E1zu", JOptionPane.YES_NO_OPTION);
			if(dialog2Result == JOptionPane.YES_OPTION){
				try{
					cdb.getClassList().clear();
					Database.saveAll();
				}catch (Exception e){
					JOptionPane.showMessageDialog(null, "Chyba pri mazan\u00ED datab\u00E1zy:\n" + e.getMessage());
				}finally{
					System.exit(0);
				}
				
			}
			
		}
		
	}
	
	public static void writeBoolean(boolean b, File f){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))){
			oos.writeBoolean(b);
		}catch(IOException e){
			TermUtils.printerr(e.toString());
		}
		
	}
	
	public static boolean readBoolean(File f){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
			return ois.readBoolean();
		}catch(IOException e){
			return false;
		}
		
	}
	
}
