package es.esy.playdotv.document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import es.esy.playdotv.gui.terminal.TermUtils;

public class BorrowingsDatabase implements AutoCloseable{
	
	public ArrayList<BorrowingEntry> borrowings = new ArrayList<BorrowingEntry>();
	
	private String finalPath;
	
	public BorrowingsDatabase(String filepath, String group){
		finalPath = filepath + "_" + group + ".json";
	}
	
	public void open(){
		try(	Scanner s = new Scanner(new File(finalPath))){
			StringBuilder sb = new StringBuilder();
			while(s.hasNext()){
				sb.append(s.next());
			}
			
			borrowings.clear();
			
			JSONObject obj = new JSONObject(sb.toString());
			long max = obj.getLong("maxBorrowing");
			
			TermUtils.println("bd max loaded");
			
			//if(max > 0L){
				TermUtils.println("bd max more than 1L");
				for(long current = 1; current <= max; current++){
					JSONArray arr = obj.getJSONArray("borrowing" + current);
					/*
					 * TODO: Cast from String to Date doesn't work
					 */
					borrowings.add(new BorrowingEntry((Date)arr.get(2), (Date)arr.get(3), arr.getString(4), arr.getString(1), arr.getString(0)));
					TermUtils.println("loopin in the databasee");
				}
			//}
			
		} catch (FileNotFoundException e) {
			TermUtils.printerr("Cannot load borrowings database");
		}
		
	}

	public void reset(){
		
	}
	
	/**
	 * @param group Group/Class name to check
	 * @return True if database for specified group exists, false if database for specified group was not found
	 */
	public static boolean groupExists(String filepath, String group){
		return new File(filepath + "_" + group + ".json").exists();
	}

	@Override
	public void close() throws IOException{
		JSONObject obj = new JSONObject();
		borrowings.forEach((borrowing) -> {
			JSONArray arr = new JSONArray();
			arr.put(borrowing.getBookID());
			arr.put(borrowing.getBookname());
			arr.put(borrowing.getBorrowDate());
			arr.put(borrowing.getReturnDate());
			arr.put(borrowing.getUsername());
			obj.put("borrowing" + borrowing.getBorrowingNum(), arr);
		});
		obj.put("maxBorrowing", BorrowingEntry.getMaxBorrowingNum());

		try(FileWriter file = new FileWriter(finalPath)){
			file.write(obj.toString(2));
		}catch(IOException e){
			TermUtils.printerr("Cannot save borrowings database");
		}
		
	}
	
}
