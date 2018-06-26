package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.objects.BorrowEntry;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
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
import java.util.Map;
import java.util.TreeMap;

/* Borrowing database for library-book-scanner*/

public class BorrowDatabase
{

	private DocumentBuilder dBuilder;
	private Transformer transformer;

	// <groupID, <entryID, entry>>
	public volatile Map<String, Map<Long, BorrowEntry>> borrowings;
	private volatile long iterator;

	// singleton pattern CONSTRUCTOR
	private static BorrowDatabase instance = new BorrowDatabase();
	public static BorrowDatabase getInstance()
	{
		return instance;
	}

	private BorrowDatabase()
	{
		// initialize factories and similar stuff required for parsing
		try
		{
			// FIELDS
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			JOptionPane.showMessageDialog(null, "S\u00FAbor datab\u00E1zy v\u00FDpo\u017Ei\u010Diek sa nena\u0161iel, bola vytvoren\u00E1 nov\u00E1 bdb datab\u00E1za.");
			return;
		}
		catch(SAXException e)
		{
			JOptionPane.showMessageDialog(null, "BDB Datab\u00E1za je po\u0161koden\u00E1 (SAXException).");
			System.exit(0);
			return; // unnecessary but it will help with IDE errors below (doc not initialized)
		}
		
		doc.getDocumentElement().normalize();

		NodeList group_nl = doc.getElementsByTagName("group");

		for (int i = 0; i<group_nl.getLength(); i++) {
			Node groupnode = group_nl.item(i);
			if (groupnode.getNodeType() != Node.ELEMENT_NODE) continue; // skips non-element nodes
			Element group_el = (Element) groupnode;

			String groupid = group_el.getAttribute("id");

			Map groupBorrowings = new TreeMap<>();

			borrowings.put(groupid, groupBorrowings); // put a new object for current group and link its map

			NodeList entry_nl = group_el.getElementsByTagName("BorrowEntry");

			for (int j = 0; j<entry_nl.getLength(); j++) {
				Node entrynode = entry_nl.item(j);
				if (entrynode.getNodeType() != Node.ELEMENT_NODE) continue; // skips non-element nodes
				Element entry_el = (Element) entrynode;

				//iterator++; // create new id

				long id = Long.valueOf(entry_el.getAttribute("id"));

				BorrowEntry e = new BorrowEntry(id);
				e.setBookID(entry_el.getAttribute("bookID"));
				e.setBookName(entry_el.getAttribute("bookName"));
				e.setBorrowerCompleteName(entry_el.getAttribute("borrowerCompleteName"));
				e.setBorrowDate(Long.valueOf(entry_el.getAttribute("borrowDate")));
				e.setReturnDate(Long.valueOf(entry_el.getAttribute("returnDate")));

				groupBorrowings.put(id, e);

			}
		}


		// load iterator after loading
		NodeList metas = doc.getElementsByTagName("meta");

		for (int i = 0; i<metas.getLength(); i++) {
			Node metaNode = metas.item(i);
			if (metaNode.getNodeType() != Node.ELEMENT_NODE) continue; // skips non-element nodes
			Element meta = (Element) metaNode;

			iterator = Long.valueOf(meta.getAttribute("iterator"));
		}




	}

	// parse map contents to xml and save to file
	public void save(String path)
	{
		File file = new File(path);

		Document doc = dBuilder.newDocument();

		Element borrowdatabase = doc.createElement("borrowdatabase");
		doc.appendChild(borrowdatabase);

		// set iterator meta
		Element meta = doc.createElement("meta");
		borrowdatabase.appendChild(meta);
		meta.setAttribute("iterator", String.valueOf(iterator));

		for (String groupKey : borrowings.keySet()) {
			Element group_el = doc.createElement("group");
			borrowdatabase.appendChild(group_el);

			group_el.setAttribute("id", groupKey);

			for (Long entryKey : borrowings.get(groupKey).keySet()) {

				BorrowEntry entry = borrowings.get(groupKey).get(entryKey);

				Element entry_el = doc.createElement("BorrowEntry");
				group_el.appendChild(entry_el);

				entry_el.setAttribute("id", String.valueOf(entry.getId()));
				entry_el.setAttribute("borrowDate", String.valueOf(entry.getBorrowDate()));
				entry_el.setAttribute("returnDate", String.valueOf(entry.getReturnDate()));
				entry_el.setAttribute("bookID", entry.getBookID());
				entry_el.setAttribute("bookName", entry.getBookName());
				entry_el.setAttribute("borrowerCompleteName", entry.getBorrowerCompleteName());
			}

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

	public synchronized BorrowEntry safeAdd(String groupID) { // use this to add entries , returns the id of entry
		iterator++;

		BorrowEntry e = new BorrowEntry(iterator);

		if (!borrowings.containsKey(groupID)) {
			borrowings.put(groupID, new TreeMap<>());
		}

		borrowings.get(groupID).put(iterator, e);

		return e;
	}

	public void reset(){
		borrowings = new TreeMap<>();
		iterator = 0;
	}


}
