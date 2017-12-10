package com.unaux.plasmoxy.libscan.database;

import es.esy.playdotv.objects.Book;
import es.esy.playdotv.objects.Person;

public class DatabaseReadTester
{

    public static void main(String[] args)
    {
        LBSDatabase db = LBSDatabase.getInstance();

        db.load("testDatabase.xml");

        System.out.println("--BOOK MAP--");
        for (String key : db.books.keySet())
        {
            Book b = db.books.get(key);
            System.out.printf("book id=%s, name=%s, author=%s\n", b.getID(), b.getName(), b.getAuthor());
        }

        System.out.println();

        System.out.println("--PERSON MAP--");
        for (String key : db.persons.keySet())
        {
            Person p = db.persons.get(key);
            System.out.printf("person id=%s, group=%s, name=%s\n", p.getID(), p.getGroup(), p.getName());
        }

    }
}

