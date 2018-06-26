package io.github.shardbytes.lbs.objects;

import java.io.Serializable;

public class Book implements Serializable{
	
	// FIELDS
	
	private String ID, name, author;
	private String takerID=""; // reference to person object which has the book, PRIMARY linker, HAS TO BE STORED IN DATABASE
	private long currentBorrowEntryID=0; // 0 means its not linked to any entry
	private long borrowedTime = 0, borrowedUntilTime = 0; // milisecond date times, to be used with java date classes externally

	// CONSTRUCTORS
	public Book(String id) // basic book constructor, uniqueness represented by id
	{
		this.ID = id;
	}
	
	// ACCESSORS
	public String getID() {return ID;}
	public void setID(String id) { this.ID = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public String getTakerID() { return takerID; }
	public void setTakerID(String taker) { this.takerID = taker; }
	public long getBorrowedTime() { return borrowedTime; }
	public void setBorrowedTime(long borrowedTime) { this.borrowedTime = borrowedTime; }
	public long getBorrowedUntilTime() { return borrowedUntilTime; }
	public void setBorrowedUntilTime(long borrowedUntilTime) { this.borrowedUntilTime = borrowedUntilTime; }

	public long getCurrentBorrowEntryID() {
		return currentBorrowEntryID;
	}

	public void setCurrentBorrowEntryID(long currentBorrowEntryID) {
		this.currentBorrowEntryID = currentBorrowEntryID;
	}
	
	// METHODS
	
	

	

}
