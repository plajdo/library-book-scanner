package es.esy.playdotv;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import atarw.materiallook.GUITheme;
import atarw.materiallook.MaterialLookAndFeel;
import es.esy.playdotv.gui.MainMenu;

public class Load {

	public static void main(String[] args)
	{
		MaterialLookAndFeel ui = new MaterialLookAndFeel (GUITheme.LIGHT_THEME);
	    
		try {
			UIManager.setLookAndFeel (ui.getName ());
		}
		catch (UnsupportedLookAndFeelException e) {}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		
		
		MainMenu.open();
	}

}
