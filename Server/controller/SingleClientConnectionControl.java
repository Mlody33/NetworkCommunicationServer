package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import application.Main;
import model.Client;

public class SingleClientConnectionControl extends Thread {
	
	private Socket clientSocket;
	private ObjectOutputStream outcomeClientData;
	private ObjectInputStream incomeClientData;
	
	private Client clientData;
	private Main main;
	
	public SingleClientConnectionControl(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		createInputOutputStream();
		while(main.getServerDate().getServerStatus()) {
			receiveDataFromClient();
			checkAuthorization();
			sendDataToClient();
				
		}
	}

	private void receiveDataFromClient() {
		try {
			clientData = null;
			clientData = (Client)incomeClientData.readObject();
			System.out.println("[S2]Data received");
			System.out.println(clientData.toString());
		} catch (ClassNotFoundException e) {
			closeConnection();
			e.printStackTrace();
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	private void checkAuthorization() {
		if(clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode())
			clientData.setAuthorized(true);
		else
			clientData.setAuthorized(false);
	}
	
	private void sendDataToClient() {
		try {
			System.out.println("[S2]Object send");
			System.out.println(clientData.toString());
			outcomeClientData.writeObject(clientData);
			outcomeClientData.flush();
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
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
			outcomeClientData.close();
		} catch (IOException e) {
			System.err.println("Error while close connection");
			e.printStackTrace();
		}
	}

}