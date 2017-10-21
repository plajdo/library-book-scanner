package es.esy.playdotv;

import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaRootPaneUI;
import es.esy.playdotv.gui.MainMenu;

public class Load {

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
			System.out.println(SyntheticaRootPaneUI.EVAL_COPY);
		}catch(Exception e){
			e.printStackTrace();
		}
		MainMenu.open();
		
	}
	
	public static final boolean isEvalCopy(){
		return false;
	}

}
