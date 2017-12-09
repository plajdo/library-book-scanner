package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Paper;
import es.esy.playdotv.objects.Person;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SebuLink
{
	@SuppressWarnings("unchecked")
	public static Map<String, Paper> load(String fileName)
	throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, Paper> map = (HashMap<String, Paper>) ois.readObject();
		ois.close();
		fis.close();
		return map;
	}
	
	public static void save(String fileName, Map<String, Paper> map)
	throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
		fos.close();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Person> loadStudent(String fileName)
	throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, Person> map = (HashMap<String, Person>) ois.readObject();
		ois.close();
		fis.close();
		return map;
	}
	
	public static void saveStudent(String fileName, Map<String, Person> map)
	throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
		fos.close();
	}
	
}
