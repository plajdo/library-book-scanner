package com.github.plasmoxy.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import es.esy.playdotv.objects.Paper;

public class SebuLink
{
	@SuppressWarnings("unchecked")
	public static Map<String, Paper> pop(String fileName)
	throws IOException, ClassNotFoundException
	{
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, Paper> map = (HashMap<String, Paper>) ois.readObject();
		ois.close();
		fis.close();
		return map;
	}
	
	public static void push(String fileName, Map<String, Paper> map)
	throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
		fos.close();
	}
	
}
