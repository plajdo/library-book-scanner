package es.esy.playdotv.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import es.esy.playdotv.objects.Book;

public class BorrowingsDatabase{
	
	private Map<Long, BorrowingEntry> borrowings = new HashMap<Long, BorrowingEntry>();
	
	private String finalPath;
	private long maxBorrowing;
	
	public BorrowingsDatabase(String filepath, String group){
		finalPath = filepath + "_" + group + ".ser";
	}
	
	public void add(BorrowingEntry e){
		open();
		borrowings.put(maxBorrowing + 1, e);
		close();
	}
	
	public void addDate(Book b){
		borrowings.forEach((id, borrowing) -> {
			if(b.getID().equals(borrowings.get(id).getBookID())){
				if(borrowings.get(id).getReturnDate() == null){
					borrowings.get(id).setReturnDate(new Date());
					
				}
				
			}
			
		});
		
	}
	
	public void forEachBorrowing(BiConsumer<? super Long, ? super BorrowingEntry> action){
		borrowings.forEach(action);
	}
	
	@Override
	public String toString(){
		StringBuilder b = new StringBuilder();
		open();
		forEachBorrowing((id, borrowing) -> {
			b.append("Book ID: " + borrowing.getBookID() + " Book name: " + borrowing.getBookname() + " Username: " + borrowing.getUsername() + " Borrow date: " + borrowing.getBorrowDate().toString() + " Return date: " + borrowing.getReturnDate().toString() + "\n");
		});
		return b.toString();
	}
	
	public void create(){
		close();
	}
	
	@SuppressWarnings("unchecked")
	private void open(){
		try{
			FileInputStream fis = new FileInputStream(finalPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			borrowings = (Map<Long, BorrowingEntry>)ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}
	

	private void close(){
		try{
			FileOutputStream fos = new FileOutputStream(finalPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(borrowings);
			oos.close();
			fos.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param filepath Path including file name, but without extension
	 * @param group Group/Class name to check
	 * @return True if database for specified group exists, false if database for specified group was not found
	 */
	public static boolean groupExists(String filepath, String group){
		return new File(filepath + "_" + group + ".ser").exists();
	}
	
}
