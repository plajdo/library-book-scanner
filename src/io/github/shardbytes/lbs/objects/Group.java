package io.github.shardbytes.lbs.objects;

public class Group{
	
	private String groupName;
	
	public Group(String name){
		this.groupName = name;
	}
	
	public String getName(){
		return groupName;
	}

	@Override
	public String toString() {
		return groupName;
	}
}
