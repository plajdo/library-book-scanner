package io.github.shardbytes.lbs.objects;

public class Person {
	
	private String ID, name, group; // group is for example class or teachers or sum shit
	private int bookCount;
	
	public Person(String id){
		this.ID = id;
	} // basic person constructor, uniqueness represented by id

	// accessors
	public String getName() {
		return name;
	}
	public String getGroup() {
		return group;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public void addBookCount(){
		bookCount++;
	}
	public void subtractBookCount(){
		bookCount--;
	}
	public String getBookCount(){
		return String.valueOf(bookCount);
	}
	public int getNumBookCount(){
		return bookCount;
	}
	public void setBookCount(String bookCount){
		try{
			this.bookCount = Integer.parseInt(bookCount);
		}catch(NumberFormatException e){
			System.err.println("Invalid string - not a number.");
		}
	}
	public void setBookCount(int bookCount){
		this.bookCount = bookCount;
	}
	public String getID() { return ID; }
	public void setID(String id) { ID = id; }
	
}
