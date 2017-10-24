package es.esy.playdotv.objects;

import java.io.Serializable;
import java.util.Date;

public interface Paper extends Serializable {
	String getID();
	String getTitle();
	String getAuthor();
	boolean isBorrowed();
	void setTitle(String title);
	void setAuthor(String author);
	void setBorrowed(boolean borrowed);
	void setBorrowDate(int day, int month, int year, int untilDay, int untilMonth, int untilYear);
	Date getBorrowedDate();
	Date getBorrowedUntilDate();
}
