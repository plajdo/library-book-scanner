package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DBZipper{
	
	private static DBZipper ourInstance = new DBZipper();
	
	public static DBZipper getInstance(){
		return ourInstance;
	}
	
	private DBZipper(){
	}
	
	public void unzipAll(String zipFile) throws ZipException{
		try{
			ZipFile z = new ZipFile(zipFile);
			z.extractAll("data");
		}catch(ZipException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
	// TODO: 11/06/2018 missing file error 
	// TODO: 11/06/2018 single save method for all save points 
	public void zipAll(String outputFile, String... files) throws ZipException{
		ArrayList<File> subory = new ArrayList<>();
		for(String filename : files){
			subory.add(new File(filename));
		}
		
		/*
		 * Remove old file if it exists
		 */
		File out = new File(outputFile);
		if(out.exists()){
			out.delete();
		}
		
		ZipFile z = new ZipFile(outputFile);
		ZipParameters zp = new ZipParameters();
		zp.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zp.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
		
		z.createZipFile(subory, zp);
		
	}
	
}
