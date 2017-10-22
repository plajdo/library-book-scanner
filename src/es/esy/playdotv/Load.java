package es.esy.playdotv;

<<<<<<< HEAD
import javax.swing.UIManager;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
=======
>>>>>>> 8c726a053595b9594fbb763f53c45ca1ecb4a3de
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import es.esy.playdotv.gui.MainMenu;

import javax.swing.*;

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
