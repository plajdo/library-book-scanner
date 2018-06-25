package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;
import io.github.shardbytes.lbs.objects.Book;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class WebDB implements AutoCloseable{
	
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
	public boolean connect(String host, int port) throws IOException{
		socket = new Socket(host, port);
		toServer = new ObjectOutputStream(socket.getOutputStream());
		fromServer = new ObjectInputStream(socket.getInputStream());
		
		toServer.writeByte(1);
		return fromServer.readByte() == 1;
		
	}
	
	@Override
	public void close() throws IOException{
		fromServer.close();
		toServer.close();
		socket.close();
	}
	
	public Map<String, Book> getBooks(){
		try{
			toServer.writeByte(2);
			return (Map<String, Book>) fromServer.readObject();
			
		}catch(IOException | ClassNotFoundException e){
			TermUtils.printerr(e.getMessage());
		}
		return null;
		
	}
	
}
