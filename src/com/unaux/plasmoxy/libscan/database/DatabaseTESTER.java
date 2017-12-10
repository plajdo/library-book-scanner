package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

public class DatabaseTESTER
{
    
    public static void main(String[] args)
    {
        LBSDatabase db = LBSDatabase.getInstance();
        
        db.reset();
        
        for (int i = 1; i<=3; i++)
        {
            String is = Integer.toString(i);
            Book b = new Book(is);
            b.setName("BOOKNAME_"+is);
            b.setAuthor("AUTHOR_"+is);
            db.books.put(is, b);
        }

        for (int i = 1; i<=3; i++)
        {
            String is = Integer.toString(i);
            Person b = new Person(is);
            b.setName("PERSONNAME_" + is);
            b.setGroup("studenti");
            db.persons.put(is, b);
        }
        
        db.save("testDatabase.xml");
        
    }
}
