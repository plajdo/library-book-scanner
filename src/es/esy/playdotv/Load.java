package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.gui.swing.LookAndFeelSettingsList;
import es.esy.playdotv.gui.swing.MainMenu;

import javax.swing.*;

public class Load
{
	
	public static final String VERSION = "v1.0.1";
	
	public static String DATABASE_PATH = "lbsdatabase.xml";
	private static LBSDatabase db = LBSDatabase.getInstance();
	
	static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.MCWIN;
	
	public static void resetDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				
				db.reset();
				db.save(DATABASE_PATH);
				
			}catch (Exception e){
				JOptionPane.showMessageDialog(null, "Chyba pri resetovan� datab�zy.");
				e.printStackTrace();
			}finally{
				System.exit(0);
			}
			
		}
		
	}
	
	public static void main(String[] args){
		
		db.load(DATABASE_PATH);
		
		switch(LAF){
		case MCWIN:
			try{
				McWinLookAndFeel.setTheme("Modern", "", "");
				UIManager.setLookAndFeel(new McWinLookAndFeel());
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Using McWin theme.");
			break;
			
		case GRAPHITE:
			try{
				GraphiteLookAndFeel.setTheme("Default", "", "");
				UIManager.setLookAndFeel(new GraphiteLookAndFeel());
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Using Graphite theme.");
			break;
			
		default:
			System.out.println("Using Metal theme.");
			break;
		}
		
		MainMenu.open();
		//FXApp.view();
		
	}
	
}
