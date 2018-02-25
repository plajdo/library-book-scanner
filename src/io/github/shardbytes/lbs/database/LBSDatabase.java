package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Book;
import io.github.shardbytes.lbs.objects.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/* Simple database for library-book-scanner*/

public class LBSDatabase
{

	// ! Everything will be parsed when needed, wont keep common document or anything, this is pure converter from xml to Map


	// FIELDS
	private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder dBuilder;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private Transformer transformer;

	// THE MAIN DATABASE MAP OBJECT REFERENCES, TO BE USED EVERYWHERE WHERE DATABASE IS NEEDED
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
			
			// set indentation so it doesn't look like shit
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		}
		catch (Exception e) {
			TermUtils.printerr("Problem building factories");
			e.printStackTrace();
			System.exit(-1);
		}

	}


	// create new map, parse xml to it and pass reference to singleton fields
	public void load(String path)
	{
		reset(); // on load, reset ~ create objects for maps ( because we only had pointers till now )
		
		File file = new File(path);
		
		Document doc;
		
		try
		{
			doc = dBuilder.parse(file);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "S\u00FAbor sa nena\u0161iel, bola vytvoren\u00E1 nov\u00E1 datab\u00E1za.");
			return;
		}
		catch(SAXException e)
		{
			JOptionPane.showMessageDialog(null, "Datab\u00E1za je po\u0161koden\u00E1 (SAXException).");
			System.exit(0);
			return; // unnecessary but it will help with IDE errors below (doc not initialized)
		}
		
		doc.getDocumentElement().normalize();
		
		
		// using simple element lookup
		NodeList bookElements = doc.getElementsByTagName("book");
		NodeList personElements = doc.getElementsByTagName("person");
		
		// process books
		for (int i = 0; i < bookElements.getLength(); i++)
		{
			Node node = bookElements.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) continue; // skips non-element nodes
			Element e = (Element) node;

			String bookid = e.getAttribute("id");

			Book b = new Book(bookid); // new book object, reference passed to db maps
			b.setAuthor(e.getAttribute("author"));
			b.setName(e.getAttribute("name"));
			try
			{
				b.setBorrowedTime(Long.valueOf(e.getAttribute("borrowedTime")));
				b.setBorrowedUntilTime(Long.valueOf(e.getAttribute("borrowedUntilTime")));
			}
			catch (NumberFormatException exception) { // if empty strings or sum shit
				b.setBorrowedTime(0);
				b.setBorrowedUntilTime(0);
			}
			
			b.setTakerID(e.getAttribute("takerID"));
			
			books.put(bookid, b); // pass ref to map
		}

		// process persons
		for (int i = 0; i < personElements.getLength(); i++)
		{
			Node node = personElements.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE ) continue; // skips non-element nodes
			Element e = (Element) node;

			String personid = e.getAttribute("id");

			Person p = new Person(personid); // new person object, reference passed to db maps
			
			p.setID(personid);
			p.setGroup(e.getAttribute("group"));
			p.setName(e.getAttribute("name"));
			p.setBookCount(e.getAttribute("books"));
			
			persons.put(personid, p); // pass ref to map
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
			el_b.setAttribute("takerID", b.getTakerID());
			el_b.setAttribute("borrowedTime", Long.toString(b.getBorrowedTime()));
			el_b.setAttribute("borrowedUntilTime", Long.toString(b.getBorrowedUntilTime()));
		}
		
		// parse person map into document
		for (String key : persons.keySet())
		{
			Person p = persons.get(key);
			Element el_p = doc.createElement("person");
			el_persons.appendChild(el_p);
			
			el_p.setAttribute("id", p.getID());
			el_p.setAttribute("name", p.getName());
			el_p.setAttribute("group", p.getGroup());
			el_p.setAttribute("books", p.getBookCount());
		}
		
		DOMSource src = new DOMSource(doc);
		StreamResult result = new StreamResult(file);

		try
		{
			transformer.transform(src, result);
		}
		catch (TransformerException e) {
			TermUtils.printerr("Save parse error");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	public void reset(){
		Comparator<String> numberComparator = new Comparator<String>(){

			@Override
			public int compare(String o1, String o2) {
				int i1 = Integer.parseInt(o1.split("/")[0]);
				int i2 = Integer.parseInt(o2.split("/")[0]);
				return i1 - i2;
			}
			
		};
		
		books = new TreeMap<>(numberComparator);
		persons = new TreeMap<>(numberComparator);
	}


}
