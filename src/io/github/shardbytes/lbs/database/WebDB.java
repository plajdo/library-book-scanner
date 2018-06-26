package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class WebDB{
	
	private ServerSocket serverSocket;
	private ObjectOutputStream toClient;
	private ObjectInputStream fromClient;
	private Socket clientSocket;
	
	private static WebDB ourInstance = new WebDB();
	
	public static WebDB getInstance(){
		return ourInstance;
	}
	
	private WebDB(){}
	
	public void host(int port){
		try{
			serverSocket = new ServerSocket(port, 9, InetAddress.getByName("192.168.100.166"));
			
			clientSocket = serverSocket.accept();
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
			fromClient = new ObjectInputStream(clientSocket.getInputStream());
			TermUtils.println("New socket connected");
			
			String requestString;
			
			inf: while(true){
				requestString = (String)fromClient.readObject();
				TermUtils.println("Request: " + requestString);
				
				switch(requestString){
					case "1": //respond to "ping"
						toClient.writeObject("1");
						break;
					
					case "2": //book list request
						toClient.writeObject(new TreeMap<>(LBSDatabase.getInstance().books));
						break;
					
					case "3": //person list request
						toClient.writeObject(new TreeMap<>(LBSDatabase.getInstance().persons));
						break;
					
					case "4": //borrowings list request
						toClient.writeObject(new TreeMap<>(BorrowDatabase.getInstance().borrowings));
						break;
					
					default:
						toClient.writeObject("1");
						break inf;
					
				}
				
			}
			
		}catch(EOFException e){
			TermUtils.println("Client disconnected");
			tryToCloseAll();
			host(port);
		}catch(ClassNotFoundException | IOException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
	private void tryToCloseAll(){
		try{
			fromClient.close();
			toClient.close();
			clientSocket.close();
			serverSocket.close();	//restart the server completely
		}catch(IOException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
}
