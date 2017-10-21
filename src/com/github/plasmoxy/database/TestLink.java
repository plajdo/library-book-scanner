package com.github.plasmoxy.database;
//muhaha

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Paper;

public class TestLink
{
	public static void main(String[] args)
	throws IOException, ClassNotFoundException
	{
		
		Book b = new Book("34");
		b.setTitle("XD");
		b.setAuthor("Sebo");
		b.setBorrowDate(3, 5, 2005, 3, 5, 2006);
		/*
		Map<String, Paper> map = new HashMap<String, Paper>();
		map.put("34", b);
		SebuLink.save("books.ser", map);
		map = null; //deref
		
		
		Map<String, Paper> books = SebuLink.load("books.ser");
		Paper p = books.get("34");
		System.out.println(p.getTitle());
		*/
	}
}
