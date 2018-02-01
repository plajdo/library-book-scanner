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

public class BorrowingsDatabase{
	
	public volatile ArrayList<BorrowingEntry> borrowings = new ArrayList<BorrowingEntry>();
	
	private static BorrowingsDatabase instance = new BorrowingsDatabase();
	public static BorrowingsDatabase getInstance(){
		return instance;
	}
	
	private BorrowingsDatabase(){
		//Do nothing in constructor
	}

	public void load(String path){
		try(	Scanner s = new Scanner(new File(path))){
			StringBuilder sb = new StringBuilder();
			while(s.hasNext()){
				sb.append(s.next());
			}
			
			borrowings.clear();
			
			JSONObject obj = new JSONObject(sb.toString());
			long max = obj.getLong("maxBorrowing");
			
			for(long current = 0; current < max; current++){
				JSONArray arr = obj.getJSONArray("borrowing" + current);
				borrowings.add(new BorrowingEntry((Date)arr.get(2), (Date)arr.get(3), arr.getString(4), arr.getString(1), arr.getString(0)));
				
			}
			
		} catch (FileNotFoundException e) {
			TermUtils.printerr("Cannot load borrowings database");
		}
		
	}

	public void save(String path){
		JSONObject obj = new JSONObject();
		if(borrowings.size() != 0){
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
			
			try(FileWriter file = new FileWriter(path)){
				
			}catch(IOException e){
				TermUtils.printerr("Cannot save borrowings database");
			}
			
		}else{
			TermUtils.println("Borrowings database empty, not saving");
		}
		
	}

	public void reset(){
		
	}
	
}
