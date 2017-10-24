package es.esy.playdotv;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.unaux.plasmoxy.libscan.database.SebuLink;
import es.esy.playdotv.gui.MainMenu;
import es.esy.playdotv.objects.Paper;
import es.esy.playdotv.objects.Person;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Load{

	public static final String BOOK_DATABASE_PATH = "papers.ser";
	public static final String STUDENT_DATABASE_PATH = "students.ser";
	
	public static volatile Map<String, Paper> papers;
	public static volatile Map<String, Person> students;
	
	public static void resetBookDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				SebuLink.save(BOOK_DATABASE_PATH, new HashMap<String, Paper>());
			}catch (IOException e){
				JOptionPane.showMessageDialog(null, "Chyba <IOException resetDatabase()>");
				System.exit(-1);
			}
		}else{
			System.exit(0);
		}
		
	}
	
	public static void resetStudentDatabase(){
		int dialogResult = JOptionPane.showConfirmDialog(null, "Naozaj resetova\u0165 datab\u00E1zu ?","Reset datab\u00E1zy", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION){
			try{
				SebuLink.saveStudent(STUDENT_DATABASE_PATH, new HashMap<String, Person>());
			}catch (IOException e){
				JOptionPane.showMessageDialog(null, "Chyba <IOException resetDatabase()>");
				System.exit(-1);
			}
		}else{
			System.exit(0);
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
		
		try{
			switch(args[0]){
			case "McWin":
				try{
					McWinLookAndFeel.setTheme("Modern", "", "");
					UIManager.setLookAndFeel(new McWinLookAndFeel());
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println("Argument " + args[0] + " recongnised.\nLaF set to McWin Modern (JTattoo).");
				break;

			case "Graphite":
				try{
					GraphiteLookAndFeel.setTheme("Default", "", "");
					UIManager.setLookAndFeel(new GraphiteLookAndFeel());
				}catch(Exception e){
					e.printStackTrace();
				}
				System.out.println("Argument " + args[0] + " recognised.\nLaF set to Graphite (JTattoo).");
				break;

			default:
				System.out.println("Invalid argument - " + args[0] + ".\nLaF set to default (Metal).");
				break;
			}

		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("No argument supplied.\nLaF set to default (Metal).");
		}
		
		MainMenu.open();
		
	}
	
}
