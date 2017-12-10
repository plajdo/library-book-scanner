package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
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
			
			// set indentation so it doesnt look like shit
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		}
		catch (Exception e) {
			System.out.println("PROBLEM BUILDING FACTORIES");
			e.printStackTrace();
			System.exit(-1);
		}

	}


	// create new map, parse xml to it and pass reference to singleton fields
	public void load(String path)
	{
		File file = new File(path);

		try
		{
			
		}
		catch (Exception e) {
			System.out.println("LOAD PARSE ERROR");
			e.printStackTrace();
			System.exit(-1);
		}

	}

	// parse map contents to xml and save to file
	public void save(String path)
	{
		File file = new File(path);
		
		Document doc = dBuilder.newDocument();
		
		Element database = doc.createElement("database");
		doc.appendChild(database);
		
		Element el_books = doc.createElement("books");
		database.appendChild(el_books);
		
		Element el_persons = doc.createElement("persons");
		database.appendChild(el_persons);
		
		// parse book map into document
		for (String key : books.keySet())
		{
			Book b = books.get(key);
			Element el_b = doc.createElement("book");
			el_books.appendChild(el_b);
			
			el_b.setAttribute("name", b.getName());
			el_b.setAttribute("id", b.getID());
			el_b.setAttribute("author", b.getAuthor());
			el_b.setAttribute("takerid", b.getTakerID());
			el_b.setAttribute("borrowedTime", Long.toString(b.getBorrowedTime()));
			el_b.setAttribute("borrowedTimeUntil", Long.toString(b.getBorrowedUntilTime()));
		}
		
		// parse person map into document
		for (String key : persons.keySet())
		{
			Person p = persons.get(key);
			Element el_p = doc.createElement("person");
			el_persons.appendChild(el_p);
			
			Element el_p_borrowedIDs = doc.createElement("borrowedIDs");
			el_p.appendChild(el_p_borrowedIDs);
			
			el_persons.setAttribute("id", p.getID());
			el_persons.setAttribute("name", p.getName());
			el_persons.setAttribute("group", p.getGroup());
		}
		
		DOMSource src = new DOMSource(doc);
		StreamResult result = new StreamResult(file);

		try
		{
			transformer.transform(src, result);
		}
		catch (TransformerException e) {
			System.out.println("SAVE PARSE ERROR"); // TODO - ked neje subor
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
    
    public void reset()
    {
        books = new HashMap<>();
		persons = new HashMap<>();
    }


}
