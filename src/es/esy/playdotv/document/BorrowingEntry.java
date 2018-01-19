package es.esy.playdotv.document;

import java.util.Date;

public class BorrowingEntry{
	
	private Date borrowDate;
	private Date returnDate;
	private String username;
	private String bookname;
	private String bookID;
	
	public BorrowingEntry(Date borrowDate, Date returnDate, String username, String bookname, String bookID){
		this.borrowDate = borrowDate;
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
