package io.github.shardbytes.lbs.objects;

import java.io.Serializable;
import java.util.Objects;

public class Group implements Serializable{
	
	private String groupName;
	private String groupGroup;
	
	public Group(String name, String category){
		this.groupName = name;
		this.groupGroup = category;
	}
	
	public String getName(){
		return groupName;
	}
	
	public String getCategory(){
		return groupGroup;
	}
	
	@Override
	public String toString(){
		return this.groupName + " (" + this.groupGroup + ")";
	}
	
	public String encodeString(){
		return this.groupName + "\u25A1" + this.groupGroup;
	}
	
	public static Group decodeString(String inputString){
		String[] stuff = inputString.split("\u25A1");
		return new Group(stuff[0], stuff[1]);
		
	}
	
	public static Group fromString(String inputString){
		String replacedString = inputString.replaceAll("\\)", "");
		String[] twoThings = replacedString.split(" \\(");
		return new Group(twoThings[0], twoThings[1]);
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Group group = (Group)o;
		return Objects.equals(groupName, group.groupName) && Objects.equals(groupGroup, group.groupGroup);
		
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(groupName, groupGroup);
		
	}
	
}
