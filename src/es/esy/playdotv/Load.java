package es.esy.playdotv;

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
