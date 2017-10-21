package es.esy.playdotv;

import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import es.esy.playdotv.gui.MainMenu;

public class Load {

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
		}catch(Exception e){
			e.printStackTrace();
		}
		MainMenu.open();
		
	}

}
