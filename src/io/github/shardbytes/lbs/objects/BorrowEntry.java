package io.github.shardbytes.lbs.objects;

import java.io.Serializable;

public class BorrowEntry implements Serializable{

	// let the indentification be done by borrowDate !

	private long borrowDate;

	private String borrowerCompleteName = "";
	private String bookID = "";
	private String bookName = "";

	private long id;

	private long returnDate;

	// METHODS: CONSTRUCTOR

	public BorrowEntry(long id) {
		this.id = id;
	}


	// METHODS: ACCESORS

	public long getId() {
		return id;
	}



	public long getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(long borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getBorrowerCompleteName() {
		return borrowerCompleteName;
	}

	public void setBorrowerCompleteName(String borrowerCompleteName) {
		this.borrowerCompleteName = borrowerCompleteName;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(long returnDate) {
		this.returnDate = returnDate;
	}

}
