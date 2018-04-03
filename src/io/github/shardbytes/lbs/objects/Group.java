package io.github.shardbytes.lbs.objects;

import java.util.HashSet;

public class Group{
	
	private String groupName;
	private int groupSize;
	private HashSet<Person> peopleInGroup;
	
	public Group(String name){
		this.groupName = name;
	}
	
	public String getName(){
		return groupName;
	}
	
	public int getSize(){
		return groupSize;
	}
	
	public HashSet<Person> getPersonSet(){
		return peopleInGroup;
	}
	
	public void setName(String name){
		this.groupName = name;
	}
	
	public void setPersonSet(HashSet<Person> set){
		this.peopleInGroup = set;
	}
	
	public void addPerson(Person p){
		peopleInGroup.add(p);
		groupSize++;
	}
	
	public void removePerson(Person p){
		if(peopleInGroup.contains(p)){
			peopleInGroup.remove(p);
			groupSize--;
		}
		
	}
	
}
