package es.esy.playdotv.objects;

import java.util.Date;

public interface Paper {
	public int getID();
	public String getTitle();
	public String getAuthor();
	public boolean isBorrowed();
	public void setTitle(String title);
	public void setAuthor(String author);
	public void setBorrowed(boolean borrowed);
	public void setBorrowDate(int day, int month, int year, int untilDay, int untilMonth, int untilYear);
	public Date borrowedUntil();
	
}
