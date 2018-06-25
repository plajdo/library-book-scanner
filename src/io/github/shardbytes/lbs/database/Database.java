package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import net.lingala.zip4j.exception.ZipException;

import javax.swing.JOptionPane;
import java.io.File;

public class Database{
	
	private static LBSDatabase adb = LBSDatabase.getInstance();
	private static BorrowDatabase bdb = BorrowDatabase.getInstance();
	private static ClassDatabase cdb = ClassDatabase.getInstance();
	
	public static void saveAll(){
		TermUtils.println("Saving databases");
		
		adb.save(Load.DATABASE_PATH);
		bdb.save(Load.B_DATABASE_PATH);
		cdb.save(Load.C_DATABASE_PATH);
		
		try{
			DBZipper.getInstance().zipAll(Load.ZIP_PATH,
					Load.DATABASE_PATH, 
					Load.B_DATABASE_PATH, 
					Load.C_DATABASE_PATH, 
					Load.WEBCAM_OPTIMIZE_PATH,
					Load.SHARE_BOOLEAN_PATH);
		}catch(ZipException e){
			TermUtils.printerr("Cannot save database");
			JOptionPane.showMessageDialog(null, "Ukladanie zlyhalo:\n" + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public static void flagTemp(String... temp){
		for(String f : temp){
			new File(f).deleteOnExit();
		}
		
	}
	
}
