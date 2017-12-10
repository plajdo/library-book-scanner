package es.esy.playdotv.objects;

import java.util.ArrayList;

public class Person {
	
	private String ID, name, group; // group is for example class or teachers or sum shit
	private ArrayList<Book> borrowedBooks = new ArrayList<Book>();
	
	public Person(String id){
		this.ID = id;
	}

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
	public String getID() {
		return ID;
	}
	
	
	
}
