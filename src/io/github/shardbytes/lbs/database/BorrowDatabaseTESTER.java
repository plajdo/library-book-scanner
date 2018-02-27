package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.document.BorrowEntry;

public class BorrowDatabaseTESTER {

	private static BorrowDatabase bdb = BorrowDatabase.getInstance();

	public static void main(String[] args) {

		bdb.reset();

		bdb.load("testdb.xml");

		System.out.print("ITERATOR = ");System.out.println(bdb.iterator);
		for (String group : bdb.borrowings.keySet()) {
			System.out.println(group);
			for (Long borrowDate : bdb.borrowings.get(group).keySet()) {
				BorrowEntry e = bdb.borrowings.get(group).get(borrowDate);
				System.out.println("	--- ENTRY --- ");
				System.out.print("	ID: "); System.out.println(e.getId());
				System.out.print("	borrowDate: "); System.out.println(e.getBorrowDate());
				System.out.print("	returnDate: "); System.out.println(e.getReturnDate());
				System.out.print("	bookID: "); System.out.println(e.getBookID());
				System.out.print("	bookName: "); System.out.println(e.getBookName());
				System.out.print("	borrowerCompleteName: "); System.out.println(e.getBorrowerCompleteName());
			}
		}

	}
}
