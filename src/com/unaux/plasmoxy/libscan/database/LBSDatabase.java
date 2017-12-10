package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.Map;

/* Simple database for libary-book-scanner*/

public class LBSDatabase
{
    
    // ! Everything will be parsed when needed, wont keep common document or anything, this is pure converter from xml to HashMap

    
    // FIELDS
    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder dBuilder;
    
    private Map<String, Book> books; // THE MAIN DATABASE MAP OBJECT, TO BE USED EVERYWHERE WHERE DATABASE IS NEEDED

    // singleton pattern CONSTRUCTOR
    private static LBSDatabase instance = new LBSDatabase();
    public static LBSDatabase getInstance()
    {
        return instance;
    }
    private LBSDatabase()
    {
        // initialize factories and similar stuff required for parsing
        try {dBuilder = dbFactory.newDocumentBuilder();} catch(ParserConfigurationException e) {
            System.out.println("PARSE ERROR");
            e.printStackTrace();
            System.exit(-1);
        }
        
    }
    
    
    // create new map, parse xml to it and pass reference to singleton field "books"
    public void loadPapers(String papersPath)
    {
        File papersFile = new File(papersPath);
        
        try { dBuilder = dbFactory.newDocumentBuilder(); } catch(ParserConfigurationException e) {
            System.out.println("PARSE ERROR");
            System.exit(-1);
        }
        
    }
    
    public void reset()
    {
        
    }
    
    
    
}
