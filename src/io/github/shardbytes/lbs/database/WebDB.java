package io.github.shardbytes.lbs.database;

import io.github.shardbytes.lbs.gui.terminal.TermUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebDB{
	
	private ServerSocket serverSocket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	
	private static WebDB ourInstance = new WebDB();
	
	public static WebDB getInstance(){
		return ourInstance;
	}
	
	private WebDB(){}
	
	public void host(int port){
		try{
			out: while(true){
				serverSocket = new ServerSocket(port);
				
				Socket clientSocket = serverSocket.accept();
				toClient = new ObjectOutputStream(clientSocket.getOutputStream());
				fromClient = new ObjectInputStream(clientSocket.getInputStream());
				TermUtils.println("New socket connected");
				
				byte requestByte;
				
				in: while(true){
					requestByte = fromClient.readByte();
					TermUtils.println("Request: " + requestByte);
					
					switch(requestByte){
						case 1: //respond to "ping"
							toClient.writeByte(1);
							
						case 2: //book list request
							toClient.writeObject(LBSDatabase.getInstance().books);
							
						case 126: //disconnect client
							break in;
							
						case 127: //stop the server
							break out;
					}
					
				}
				
			}
			
		}catch(IOException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
}
