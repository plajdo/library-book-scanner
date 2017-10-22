package es.esy.playdotv;

import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import es.esy.playdotv.gui.MainMenu;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

public class Load {

	public static void main(String[] args){
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
