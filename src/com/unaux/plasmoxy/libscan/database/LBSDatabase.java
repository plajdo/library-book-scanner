package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.util.Map;

/* Simple database for libary-book-scanner*/

public class LBSDatabase
{

	// ! Everything will be parsed when needed, wont keep common document or anything, this is pure converter from xml to HashMap


	// FIELDS
	private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder dBuilder;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private Transformer transformer;

	// THE MAIN DATABASE MAP OBJECTS, TO BE USED EVERYWHERE WHERE DATABASE IS NEEDED
	public volatile Map<String, Book> books; 
	public volatile Map<String, Person> persons;
	
	// singleton pattern CONSTRUCTOR
	private static LBSDatabase instance = new LBSDatabase();

	public static LBSDatabase getInstance()
	{
		return instance;
	}

	private LBSDatabase()
	{
		// initialize factories and similar stuff required for parsing
		try
		{
			dBuilder = dbFactory.newDocumentBuilder();
			transformer = transformerFactory.newTransformer();
		} catch (Exception e)
		{
			System.out.println("PROBLEM BUILDING FACTORIES");
			e.printStackTrace();
			System.exit(-1);
		}

	}


	// create new map, parse xml to it and pass reference to singleton field "books"
	public void loadBooks(String path)
	{
		File papersFile = new File(path);

		try
		{
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e)
		{
			System.out.println("PARSE ERROR");
			System.exit(-1);
		}

	}

	// parse map contents to xml and save to file
	public void saveBooks(String path)
	{
		
	}
    
    public void reset()
    {
        
    }
    
    
    
}
