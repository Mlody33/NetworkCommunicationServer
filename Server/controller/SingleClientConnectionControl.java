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
	private ObjectOutputStream outcomeStream;
	private ObjectInputStream incomeStream;
	
	private Client clientData = new Client();
	private ServerMain main;
	
	public SingleClientConnectionControl(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	public void setClientStatusConnected() {
		clientData.setConnected();
	}
	
	@Override
	public void run() {
		createInputOutputStream();
		while(main.getServerDate().isServerOnline() & clientData.isConnected()) {
			receiveDataFromClient();
			checkAuthorization();
			checkClientStatuses();
			sendDataToClient();
		}
	}
	
	private void receiveDataFromClient() {
		log.warning("_____________________________________________________________________");
		Client clientDataRead = new Client();
		try {
			clientDataRead = (Client)incomeStream.readObject();
			clientData.setClientData(clientDataRead);
			if(!clientData.isAuthorized() && clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode())
				main.getConnectedClients().add(clientData);
			log.info("[S2]Received client: "+clientData.toString());
		} catch (ClassNotFoundException | IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	private void checkAuthorization() {
		if(clientData.isAuthorized() || clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode())
			clientData.setAuthorized();
		else
			clientData.setNotAuthorized();
		log.info("check authorization: " + clientData.toString());
	}
	
	private void checkClientStatuses() {
		if(clientData.getSignalToCommunicationWithServer() == 2) { //FIXME change static int to signal enum
			System.out.println("USUWAM Z LISTY");
		}else {
			System.out.println("Inne");
		}
	}
	
	private void sendDataToClient() {
		Client clientDataWrite = new Client();
		clientDataWrite.setClientData(clientData);
		try {
			outcomeStream.writeObject(clientDataWrite);
			outcomeStream.flush();
			log.info("[S2]Send client: "+clientDataWrite.toString());
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}


	private void createInputOutputStream() {
		try {
			incomeStream = new ObjectInputStream(clientSocket.getInputStream());
			outcomeStream = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			log.warning("Error while creating input stream");
			e.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			clientSocket.close();
			incomeStream.close();
			outcomeStream.close();
		} catch (IOException e) {
			log.warning("Error while close connection");
			e.printStackTrace();
		}
	}

}