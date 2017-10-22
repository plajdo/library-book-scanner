package es.esy.playdotv;

import javax.swing.UIManager;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import es.esy.playdotv.gui.MainMenu;

public class Load {

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(new GraphiteLookAndFeel());
		}catch(Exception e){
			e.printStackTrace();
		}
		MainMenu.open();
		
	}

}
