package es.esy.playdotv.gui.terminal;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

public class TermUtils {
	
	private static ColoredPrinter cp;
	
	public static void init(){
		cp = new ColoredPrinter.Builder(1, false).foreground(FColor.WHITE).background(BColor.BLACK).build();
		
	}
	
	public static void println(String text){
		cp.print(cp.getDateFormatted(), Attribute.NONE, FColor.CYAN, BColor.BLACK);
		cp.println(" " + text);
	}
	
	public static void printerr(String text){
		cp.print(cp.getDateFormatted(), Attribute.NONE, FColor.RED, BColor.BLACK);
		cp.println(" " + text, Attribute.NONE, FColor.RED, BColor.BLACK);
	}
	
}
