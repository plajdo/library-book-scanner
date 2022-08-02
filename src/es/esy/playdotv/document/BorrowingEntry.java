package es.esy.playdotv.document;

import java.util.Date;

public class BorrowingEntry{
	
	private Date borrowDate;
	private Date returnDate;
	private String username;
	private String bookname;
	private String bookID;
	private long borrowingNum;
	
	private static long maxBorrowingNum;
	
	
	/**
	 * Creates a new borrowing entry. Can be added to a BorrowingsDatabase.
	 * @see BorrowingsDatabase.java
	 * @param borrowDate Date, when the book was borrowed
	 * @param returnDate Date, when the book was returned (or null if not returned yet)
	 * @param username Name of person who borrowed the book
	 * @param bookname Book writer's name
	 * @param bookID ID of the book
	 * @since 1.1.0
	 */
	public BorrowingEntry(Date borrowDate, Date returnDate, String username, String bookname, String bookID){
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.username = username;
		this.bookname = bookname;
		this.bookID = bookID;
		this.borrowingNum = maxBorrowingNum + 1;
		maxBorrowingNum++;
	}
	
	public static long getMaxBorrowingNum(){
		return maxBorrowingNum;
	}
	
	public static void setMaxBorrowingNum(long number){
		maxBorrowingNum = number;
	}
	
	public long getBorrowingNum(){
		return borrowingNum;
	}
	
	public void setBorrowingNum(long number){
		borrowingNum = number;
	}
	
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	
}
