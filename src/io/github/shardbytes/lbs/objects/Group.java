package io.github.shardbytes.lbs.objects;

import java.util.Base64;

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
	
}
