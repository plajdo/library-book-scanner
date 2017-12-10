package es.esy.playdotv.objects;

import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String ID, name, group; // group is for example class or teachers or sum shit
	private ArrayList<String> borrowedIDs = new ArrayList<>(); // secondary linker, NOT SAVED IN DATABASE, has to be relinked in parser
	
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
	public String getID() { return ID; }
	public List<String> getBorrowedIDs() { return borrowedIDs; }
	
	
	
}
