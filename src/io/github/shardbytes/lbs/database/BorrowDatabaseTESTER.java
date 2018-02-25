package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.Load;
import io.github.shardbytes.lbs.document.BorrowEntry;

public class BorrowDatabaseTESTER {

	private static BorrowDatabase bdb = BorrowDatabase.getInstance();

	public static void main(String[] args) {

		bdb.load(Load.B_DATABASE_PATH);

		/*

		bdb.reset();

		BorrowEntry entry = new BorrowEntry(3);
		entry.setReturnDate(55);
		entry.setBookID("83/23");
		entry.setBorrowerCompleteName("Filip Šašala - 323");
		entry.setBookName("Chalupa");

		bdb.safeAdd("kvinta", entry);

		bdb.save("testbdb.xml");

		*/


		for (String group : bdb.borrowings.keySet()) {
			for (Long borrowDate : bdb.borrowings.get(group).keySet()) {
				BorrowEntry e = bdb.borrowings.get(group).get(borrowDate);
				System.out.println(" --- ENTRY --- ");
				System.out.print("	borrowDate: "); System.out.println(e.getBorrowDate());
				System.out.print("	returnDate: "); System.out.println(e.getReturnDate());
				System.out.print("	bookID: "); System.out.println(e.getBookID());
				System.out.print("	bookName: "); System.out.println(e.getBookName());
				System.out.print("	borrowerCompleteName: "); System.out.println(e.getBorrowerCompleteName());
			}
		}

	}
}
