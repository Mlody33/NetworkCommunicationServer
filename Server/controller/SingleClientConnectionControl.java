package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import application.ServerMain;
import model.Client;

public class SingleClientConnectionControl extends Thread {
	
	private Logger log = Logger.getLogger("Server"+this.getClass().getName());
	private Socket clientSocket;
	private ObjectOutputStream outcomeClientData;
	private ObjectInputStream incomeClientData;
	
	private Client clientData;
	private ServerMain main;
	
	public SingleClientConnectionControl(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		createInputOutputStream();
		while(main.getServerDate().isServerOnline()) {
			receiveDataFromClient();
			checkAuthorization();
			sendDataToClient();
		}
	}

	private void receiveDataFromClient() {
		try {
			clientData = null;
			clientData = (Client)incomeClientData.readObject();
			main.getConnectedClients().add(clientData);
			log.info("[S2]Client Conencted: "+clientData.toString());
			
		} catch (ClassNotFoundException e) {
			closeConnection();
			e.printStackTrace();
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	private void checkAuthorization() {
		if(clientData.isAuthorized() || clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode())
			clientData.setAuthorized();
		else
			clientData.setNotAuthorized();
	}
	
	private void sendDataToClient() {
		try {
			outcomeClientData.writeObject(clientData);
			outcomeClientData.flush();
			log.info("[S2]Send data: "+clientData.toString());
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}


	private void createInputOutputStream() {
		try {
			incomeClientData = new ObjectInputStream(clientSocket.getInputStream());
			outcomeClientData = new ObjectOutputStream(clientSocket.getOutputStream());
			log.info("[S2]Created income and outcome stream");
		} catch (IOException e) {
			log.warning("Error while creating input stream");
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			clientSocket.close();
			incomeClientData.close();
			outcomeClientData.close();
		} catch (IOException e) {
			log.warning("Error while close connection");
			e.printStackTrace();
		}
	}

}