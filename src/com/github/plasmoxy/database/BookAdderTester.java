package com.github.plasmoxy.database;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Paper;

public class BookAdderTester
{
	public static void main(String[] args)
	throws IOException, ClassNotFoundException
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("- BOOK ADDER -");
		
		System.out.print("ID : ");
		String id = sc.nextLine();
		
		System.out.print("Author : ");
		String author = sc.nextLine();
		
		System.out.print("Title : ");
		String title = sc.nextLine();
		
		Paper book = new Book(id, author, title);
		
		Map<String, Paper> papers = SebuLink.load("papers.ser");
		papers.put(id, book);
		SebuLink.save("papers.ser", papers);
		
	}
}
