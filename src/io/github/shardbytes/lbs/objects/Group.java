package io.github.shardbytes.lbs.objects;

import java.util.Base64;
import java.util.Objects;

public class Group{
	
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
		String str = this.groupName + "\u25A1" + this.groupGroup;
		byte[] encoded = Base64.getEncoder().encode(str.getBytes());
		return new String(encoded);
	}
	
	/*
	 * TODO: Opraviť celé toto s Base64 lebo for some reason to nefunguje
	 */
	public static Group decodeString(String inputString){
		byte[] decoded = Base64.getDecoder().decode(inputString.getBytes());
		String str = new String(decoded);
		String[] stuff = str.split("\u25A1");
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
