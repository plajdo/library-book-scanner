package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.objects.BorrowEntry;
import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Book;
import io.github.shardbytes.lbs.objects.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Map;

public class WebDB implements Serializable{
	
	private Socket socket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private static WebDB ourInstance = new WebDB();
	
	public static WebDB getInstance(){
		return ourInstance;
	}
	
	private WebDB(){}
	
	/**
	 * @param host Host IP address
	 * @param port Port to connect to
	 * @return True when connection was succesful, otherwise false
	 */
	public boolean connect(String host, int port){
		try{
			socket = new Socket(host, port);
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());
			
			TermUtils.println("Request: 1");
			toServer.writeObject("1");
			return fromServer.readObject().equals("1");
		}catch(IOException | ClassNotFoundException e){
			TermUtils.printerr(e.getMessage());
		}
		return false;
		
	}
	
	public void close(){
		try{
			fromServer.close();
			toServer.close();
			socket.close();
		}catch(IOException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
	public Map<String, Book> getBooks(){
		try{
			TermUtils.println("Request: 2");
			toServer.writeObject("2");
			return (Map<String, Book>)fromServer.readObject();
			
		}catch(IOException | ClassNotFoundException e){
			TermUtils.printerr(e.getMessage());
		}
		return null;
		
	}
	
	public Map<String, Person> getPersons(){
		try{
			TermUtils.println("Request: 3");
			toServer.writeObject("3");
			return (Map<String, Person>)fromServer.readObject();
			
		}catch(IOException | ClassNotFoundException e){
			TermUtils.printerr(e.getMessage());
		}
		return null;
		
	}
	
	public Map<String, Map<Long, BorrowEntry>> getBorrowings(){
		try{
			TermUtils.println("Request: 4");
			toServer.writeObject("4");
			return (Map<String, Map<Long, BorrowEntry>>)fromServer.readObject();
			
		}catch(IOException | ClassNotFoundException e){
			TermUtils.printerr(e.getMessage());
		}
		return null;
		
	}
	
	
	
}
