package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Paper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.Map;

public class LBSDatabase
{

    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder dBuilder;

    private static LBSDatabase instance = new LBSDatabase();
    private LBSDatabase()
    {
        try {dBuilder = dbFactory.newDocumentBuilder();} catch(ParserConfigurationException e) {
            System.out.println("PARSE ERROR");
            e.printStackTrace();
            System.exit(-1);
        }
        
    }
    
    

    public static LBSDatabase getInstance()
    {
        return instance;
    }

    public Map<String, Paper> loadPapers(String papersPath)
    {
        File papersFile = new File(papersPath);
        
        try { dBuilder = dbFactory.newDocumentBuilder(); } catch(ParserConfigurationException e) {
            System.out.println("PARSE ERROR");
            System.exit(-1);
        }
        
        return null;
        
    }
    
    
    
}
