package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;

public class DatabaseTESTER
{ 
    LBSDatabase db = LBSDatabase.getInstance();
    
    public static void main(String[] args)
    {
        LBSDatabase db = LBSDatabase.getInstance();
        
        db.books.put("1", new Book("1", "Seb", "LOLO"));
        Book b2 = new Book("2", "Filip", "TWTTT");
        Book b3 = new Book("3", "Pitu", "THOT");
        Book b4 = new Book("4", "Damian", "asd");
        
    }
}
