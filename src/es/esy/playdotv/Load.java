package es.esy.playdotv;

import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
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
