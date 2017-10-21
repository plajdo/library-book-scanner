package com.github.plasmoxy.database;

import java.io.IOException;
import java.util.Map;

import es.esy.playdotv.objects.Paper;

public class DumpBooksTester
{
	public static void main(String[] args)
	throws ClassNotFoundException, IOException
	{
		Map<String, Paper> papers = SebuLink.load("papers.ser");
		
		for (String key : papers.keySet())
		{
			Paper p = papers.get(key);
			System.out.printf("%s : %s - %s", p.getID(), p.getAuthor(), p.getTitle());
			
		}
	}
}
