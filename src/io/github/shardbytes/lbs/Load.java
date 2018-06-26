package io.github.shardbytes.lbs;

import com.github.sarxos.webcam.Webcam;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import io.github.shardbytes.lbs.database.Database;
import io.github.shardbytes.lbs.database.WebDB;
import io.github.shardbytes.lbs.gui.swing.LookAndFeelSettingsList;
import io.github.shardbytes.lbs.gui.swing.MainMenu;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
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

public class Load{
	
	public static final String VERSION = "v1.2.3";
	
	public static String WEBCAM_OPTIMIZE_PATH;
	public static boolean webcamOptimise;
	private static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.GRAPHITE;
	
	public static void main(String[] args){ 
		splashProgress(0);
		splashText("Pre-Initialisation");
		
		WEBCAM_OPTIMIZE_PATH = "data" + File.separator + "webcamsettings.ser";
		
		TermUtils.init();
		if(System.console() == null){
			System.setProperty("jansi.passthrough", "true");
		}
		
		splashProgress(20);
		splashText("Initialisation");
		
		TermUtils.println("Connecting to remote database");
		
		WebDB webDB = WebDB.getInstance();
		webDB.connect("192.168.100.166", 16360);
		
		splashProgress(40);
		splashText("Initialisation");
		
		TermUtils.println("Loading configs");
		webcamOptimise = readBoolean(new File(WEBCAM_OPTIMIZE_PATH));
		
		splashProgress(60);
		splashText("Post-Initialisation");
		
		Database.flagTemp(WEBCAM_OPTIMIZE_PATH);
		
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
			writeBoolean(false, new File(WEBCAM_OPTIMIZE_PATH));
		}
		
		MainMenu.open();
		
		splashProgress(100);
		splashText("Finishing");
		
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
	
	public static void writeBoolean(boolean b, File f){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))){
			oos.writeBoolean(b);
		}catch(IOException e){
			TermUtils.printerr(e.toString());
		}
		
	}
	
	private static boolean readBoolean(File f){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
			return ois.readBoolean();
		}catch(IOException e){
			return false;
		}
		
	}
	
}
