package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.Client;

public class ClientControlThread extends Thread {
	
	private Socket clientSocket;
	private ObjectInputStream incomeClientData;
	
	private Client clientData;
	
	public ClientControlThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try {
			incomeClientData = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			System.err.println("Error while creating input stream");
			e.printStackTrace();
		}
		
		while(true) {
			try {
				clientData = null;
				clientData = (Client)incomeClientData.readObject();
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error while receiving data");
				e.printStackTrace();
				closeConnection();
			}
			
			System.out.println(clientData.toString());
		}
	}
	
	
	public void closeConnection() {
		try {
			clientSocket.close();
			incomeClientData.close();
		} catch (IOException e) {
			System.err.println("Error while close connection");
			e.printStackTrace();
		}
	}

}
