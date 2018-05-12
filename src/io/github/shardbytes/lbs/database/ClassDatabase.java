package io.github.shardbytes.lbs.database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Group;

public class ClassDatabase{
	
	private static ClassDatabase instance;
	
	private HashMap<String, ArrayList<Group>> classList;
	
	static{
		instance = new ClassDatabase();
	}
	
	private ClassDatabase(){
		classList = new HashMap<>();
	}
	
	public static ClassDatabase getInstance(){
		return instance;
	}
	
	public HashMap<String, ArrayList<Group>> getClassList(){
		return classList;
	}
	
	public void load(){
		StringBuilder sb = new StringBuilder();
		try{
			Files.lines(new File("data" + File.separator + "groupList.json").toPath()).forEach(sb::append);
			JSONObject obj = new JSONObject(sb.toString());
			JSONArray namesArr = obj.names();
			ArrayList<String> names = new ArrayList<>();
			
			namesArr.forEach((name) -> names.add(name.toString()));
			
			classList.clear();
			
			names.forEach((name) -> {
				ArrayList<Group> arg = new ArrayList<>();
				obj.getJSONArray(name).forEach((group) -> arg.add(new Group(group.toString())));
				classList.put(name, arg);
				
			});
			TermUtils.println("Class database loaded");
			
		}catch(Exception e){
			TermUtils.printerr("Cannot load class database");
		}
		
	}
	
	public void save(){
		JSONObject obj = new JSONObject();
		
		if(!classList.isEmpty()){
			classList.forEach((name, arl) -> {
				if(!arl.isEmpty()){
					JSONArray arr = new JSONArray();
					arl.forEach((group) -> {
						if(!group.getName().equals("null")){
							arr.put(group.getName());
							
						}
						
					});
					obj.put(name, arr);
					
				}
				
			});
			
		}
		
		try(PrintWriter writer = new PrintWriter("data" + File.separator + "groupList.json")){
			writer.println(obj.toString());
		}catch(IOException e){
			TermUtils.printerr("Cannot save class database");
			e.printStackTrace();
		}
		TermUtils.println("Class database saved");
		
	}
	
}
