package io.github.shardbytes.lbs.database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Group;

public class ClassDatabase{
	
	private static ClassDatabase instance;
	
	private ArrayList<Group> classList;
	private ArrayList<String> groupNames;
	private int groupNumber;
	
	static{
		instance = new ClassDatabase();
	}
	
	private ClassDatabase(){
		classList = new ArrayList<Group>();
		groupNames = new ArrayList<String>();
		groupNumber = 0;
	}
	
	public static ClassDatabase getInstance(){
		return instance;
	}
	
	public ArrayList<Group> getClassList(){
		return classList;
	}
	
	public ArrayList<String> getGroupNames(){
		return groupNames;
	}
	
	public int getGroupNumber(){
		return groupNumber;
	}
	
	public void load(){
		StringBuilder sb = new StringBuilder();
		try{
			Files.lines(new File("data" + File.separator + "groupList.json").toPath()).forEach((line) -> {
				sb.append(line);
			});
			JSONObject obj = new JSONObject(sb.toString());
			JSONArray arr = obj.getJSONArray("groups");
			
			for(int i = 0; i < arr.length() - 1; i++){
				classList.add(new Group(arr.get(i).toString()));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void save(){
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		if(classList.size() != 0){
			for(Group g : classList){
				arr.put(g.getName());
			}
			
		}
		obj.put("groups", arr);
		
		try(PrintWriter writer = new PrintWriter("data" + File.separator + "groupList.json")){
			writer.println(obj.toString());
		}catch(IOException e){
			TermUtils.printerr("Cannot save class database");
			e.printStackTrace();
		}
		
	}
	
}
