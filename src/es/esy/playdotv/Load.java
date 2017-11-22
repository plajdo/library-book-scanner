package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.SebuLink;

import es.esy.playdotv.gui.LookAndFeelSettingsList;
import es.esy.playdotv.gui.MainMenu;
import es.esy.playdotv.objects.Paper;
import es.esy.playdotv.objects.Person;

import javax.swing.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Load{

	public static String BOOK_DATABASE_PATH = "papers.ser";
	public static String STUDENT_DATABASE_PATH = "students.ser";
	
	public static volatile Map<String, Paper> papers;
	public static volatile Map<String, Person> students;
	
	static LookAndFeelSettingsList LAF = LookAndFeelSettingsList.DEFAULT;
	
	public static void resetBookDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				SebuLink.save(BOOK_DATABASE_PATH, new HashMap<String, Paper>());
			}catch (IOException e){
				JOptionPane.showMessageDialog(null, "Chyba <IOException resetDatabase()>");
			}finally{
				System.exit(0);
			}
			
		}
		
	}
	
	public static void resetStudentDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				SebuLink.saveStudent(STUDENT_DATABASE_PATH, new HashMap<String, Person>());
			}catch (IOException e){
				JOptionPane.showMessageDialog(null, "Chyba <IOException resetDatabase()>");
			}finally{
				System.exit(0);
			}
			
		}
		
	}
	
	public static void createNewBookDatabase(){
		try{
			SebuLink.save(BOOK_DATABASE_PATH, new HashMap<String, Paper>());
			papers = SebuLink.load(BOOK_DATABASE_PATH);
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public static void createNewStudentDatabase(){
		try{
			SebuLink.saveStudent(STUDENT_DATABASE_PATH, new HashMap<String, Person>());
			students = SebuLink.loadStudent(STUDENT_DATABASE_PATH);
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public static void main(String[] args){
		
		Properties prop = new Properties();
		
		try(InputStream input = new FileInputStream("config.properties")){
			prop.load(input);
			
			BOOK_DATABASE_PATH = prop.getProperty("BDP") + "papers.ser";
			STUDENT_DATABASE_PATH = prop.getProperty("SDP") + "students.ser";
			LAF = LookAndFeelSettingsList.valueOf(prop.getProperty("LAF"));
			
		}catch(IOException | NullPointerException e){
			System.err.println("Missing config.properties, using default database path.");
		}
		
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
		
	}
	
}
