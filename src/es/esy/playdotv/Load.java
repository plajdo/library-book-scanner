package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.LBSDatabase;
import es.esy.playdotv.gui.swing.LookAndFeelSettingsList;
import es.esy.playdotv.gui.swing.MainMenu;

import javax.swing.*;

//import es.esy.playdotv.gui.swing.MainMenu;

public class Load
{
	
	public static final String VERSION = "v1.0 BETA";
	
	public static String DATABASE_PATH = "lbsdatabase.xml";
	private static LBSDatabase db = LBSDatabase.getInstance();
	
	static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.DEFAULT;
	
	public static void resetDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
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
		/*
		Properties prop = new Properties();
		
		try(InputStream input = new FileInputStream("config.properties")){
			prop.load(input);
			
			DATABASE_PATH = prop.getProperty("DATABASE_FILE_PATH");
			LAF = LookAndFeelSettingsList.valueOf(prop.getProperty("LAF"));
			
		}catch(IOException | NullPointerException e){
			System.err.println("Missing config.properties, using default database path.");
		}
		*/
		/*
		
		try{
			papers = SebuLink.load(BOOK_DATABASE_PATH);
		}catch(ClassNotFoundException | IOException e){
			createNewBookDatabase();
		}
		
		try{
			students = SebuLink.loadStudent(STUDENT_DATABASE_PATH);
		}catch(ClassNotFoundException | IOException e){
			createNewStudentDatabase();
		}
		
		*/
		
		// TODO : nechat databazu nech sa resetuje kebyze dostane nejaky error pri subore, ak error pri parsovani nech zhodi program ( teda vymazanie suboru / opravenie databazy to opravi )
		
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
