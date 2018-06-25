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
			serverSocket = new ServerSocket(port);
			
			Socket clientSocket = serverSocket.accept();
			toClient = new ObjectOutputStream(clientSocket.getOutputStream());
			fromClient = new ObjectInputStream(clientSocket.getInputStream());
			
			byte requestByte;
			
			while((requestByte = fromClient.readByte()) != 0){
				
				/*
				 * Respond to "ping"
				 */
				if(requestByte == 1){
					toClient.writeByte(1);
				}
				
				/*
				 * Book list request
				 */
				if(requestByte == 2){
					toClient.writeObject(LBSDatabase.getInstance().books);
				}
				
			}
			
		}catch(IOException e){
			TermUtils.printerr(e.getMessage());
		}
		
	}
	
}
