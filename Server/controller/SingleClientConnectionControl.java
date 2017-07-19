package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Client;

public class SingleClientConnectionControl extends Thread {
	
	private Socket clientSocket;
	private ObjectOutputStream outcomeClientData;
	private ObjectInputStream incomeClientData;
	
	private Client clientData;
	private boolean connected;
	private boolean serverStatus;
	
	public SingleClientConnectionControl(Socket clientSocket, boolean connected, boolean serverStatus) {
		this.clientSocket = clientSocket;
		this.connected = connected;
		this.serverStatus = serverStatus;
	}
	
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	@Override
	public void run() {
		createInputOutputStream();
		
		System.out.println("[S2]WHILE-"+connected+serverStatus);
		while(connected&serverStatus) {
			try {
				clientData = null;
				clientData = (Client)incomeClientData.readObject();
				System.out.println("[S2]Data received");
				System.out.println(clientData.toString());
				
				clientData.setAuthorized(true);
				
				System.out.println("[S2]Object send");
				System.out.println(clientData.toString());
				outcomeClientData.writeObject(clientData);
				outcomeClientData.flush();
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error while receiving data");
				closeConnection();
				e.printStackTrace();
			}
		}
	}

	private void createInputOutputStream() {
		try {
			incomeClientData = new ObjectInputStream(clientSocket.getInputStream());
			outcomeClientData = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("[S2]Created income and outcome stream");
		} catch (IOException e) {
			System.err.println("Error while creating input stream");
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			clientSocket.close();
			incomeClientData.close();
			connected = false;
		} catch (IOException e) {
			System.err.println("Error while close connection");
			e.printStackTrace();
		}
	}

}
