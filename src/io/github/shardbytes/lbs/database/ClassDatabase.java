package io.github.shardbytes.lbs.database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.TreeMap;

import io.github.shardbytes.lbs.Load;
import org.json.JSONArray;
import org.json.JSONObject;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Group;

public class ClassDatabase{
	
	private static ClassDatabase instance;
	
	private TreeMap<String, ArrayList<Group>> classList;
	
	static{
		instance = new ClassDatabase();
	}
	
	private ClassDatabase(){
		classList = new TreeMap<>();
	}
	
	public static ClassDatabase getInstance(){
		return instance;
	}
	
	public TreeMap<String, ArrayList<Group>> getClassList(){
		return classList;
	}
	
	public void load(){
		StringBuilder sb = new StringBuilder();
		try{
			Files.lines(new File(Load.C_DATABASE_PATH).toPath()).forEach(sb::append);
			JSONObject obj = new JSONObject(sb.toString());
			JSONArray namesArr = obj.names();
			ArrayList<String> names = new ArrayList<>();
			
			namesArr.forEach((name) -> names.add(name.toString()));
			
			classList.clear();
			
			names.forEach((name) -> {
				ArrayList<Group> arg = new ArrayList<>();
				obj.getJSONArray(name).forEach((group) -> arg.add(new Group(group.toString(), name)));
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
					if(!arl.stream().allMatch(ClassDatabase::isGroupNullOrEmpty)){
						JSONArray arr = new JSONArray();
						arl.forEach((group) -> {
							if(!(group.getName() == null)){
								if(!group.getName().isEmpty()){
									arr.put(group.getName());
									
								}
								
							}
						});
						obj.put(name, arr);
						
					}
				
				}
				
			});
			
		}
		
		try(PrintWriter writer = new PrintWriter(Load.C_DATABASE_PATH)){
			writer.println(obj.toString());
		}catch(IOException e){
			TermUtils.printerr("Cannot save class database");
			e.printStackTrace();
		}
		TermUtils.println("Class database saved");
		
	}
	
	private static boolean isGroupNullOrEmpty(Object o){
		Group test = (Group)o;
		return test.getName() == null || test.getName().isEmpty();
	}
	
}
