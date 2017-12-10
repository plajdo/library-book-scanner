package es.esy.playdotv.objects;

public class Book {
	
	// FIELDS
	
	private String id, name, author;
	private Person taker; // reference to person object which has the book
	private long borrowedTime = 0, borrowedUntilTime = 0; // milisecond date times, to be used with java date classes externally

	// CONSTRUCTORS
	public Book(String id, String author, String name) // basic book constructor
	{
		this.id = id;
		this.author = author;
		this.name = name;
	}
	
	// ACCESSORS
	public String getId() {return id;}
	public void setId(String id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	public Person getTaker() { return taker; }
	public void setTaker(Person taker) { this.taker = taker; }
	public long getBorrowedTime() { return borrowedTime; }
	public void setBorrowedTime(long borrowedTime) { this.borrowedTime = borrowedTime; }
	public long getBorrowedUntilTime() { return borrowedUntilTime; }
	public void setBorrowedUntilTime(long borrowedUntilTime) { this.borrowedUntilTime = borrowedUntilTime; }
	
	// METHODS
	
	

	

}
