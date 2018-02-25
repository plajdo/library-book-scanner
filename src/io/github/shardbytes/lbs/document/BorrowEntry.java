package io.github.shardbytes.lbs.document;

public class BorrowEntry {

	// let the indentification be done by borrowDate !

	private long borrowDate;

	private String borrowerCompleteName = "";
	private String bookID = "";
	private String bookName = "";

	private long returnDate;

	// METHODS: CONSTRUCTOR

	public BorrowEntry(long borrowDate) {
		this.borrowDate = borrowDate;
	}


	// METHODS: ACCESORS

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
