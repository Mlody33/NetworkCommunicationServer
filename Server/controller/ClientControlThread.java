package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import application.Main;
import model.Client;

public class ClientControlThread extends Thread {
	
	private Socket clientSocket;
	private ObjectInputStream incomeClientData;
	
	private Client clientData;
	private boolean connected;
	private boolean serverStatus;
	
	private Main main;
	
	public ClientControlThread(Socket clientSocket, boolean connected, boolean serverStatus) {
		this.clientSocket = clientSocket;
		this.connected = connected;
		this.serverStatus = serverStatus;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	@Override
	public void run() {
		createInputStream();
		
		System.out.println("[S2]WHILE-"+connected+serverStatus);
		while(connected&serverStatus) {
			try {
				clientData = null;
				clientData = (Client)incomeClientData.readObject();
				System.out.println("[S2]Data received");
				System.out.println(clientData.toString());
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error while receiving data");
				closeConnection();
				e.printStackTrace();
			}
		}
			
	}

	private void createInputStream() {
		try {
			incomeClientData = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("[S2]Created income stream");
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
