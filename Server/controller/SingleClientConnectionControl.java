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
			readDataFromClient();
			checkClientStatus();
			sendDataToClient();
			log.warning("_____________________________________________________________________");
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
	
	private void readDataFromClient() {
		Client clientDataToRead = new Client();
		try {
			clientDataToRead = (Client)incomeStream.readObject();
			clientData.setClientData(clientDataToRead);
			log.info("[S2]Received client: "+clientData.toString());
		} catch (ClassNotFoundException | IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	private void sendDataToClient() {
		Client clientDataToSend = new Client();
		clientDataToSend.setClientData(clientData);
		try {
			outcomeStream.writeObject(clientDataToSend);
			outcomeStream.flush();
			log.info("[S2]Send client: "+clientDataToSend.toString());
		} catch (IOException e) {
			closeConnection();
			e.printStackTrace();
		}
	}
	
	private void checkClientStatus() { //FIXME eliminate switch statement
		switch(clientData.getSignalToCommunicationWithServer()) {
		case 1:
			log.warning("SIGNAL 1");
			connectClient();
			break;
		case 2:
			log.warning("SIGNAL 2");
			disconnectClient();
			break;
		case 3:
			log.warning("SIGNAL 3");
			checkAuthorization();
			break;
		case 4:
			log.warning("SIGNAL 4");
			updateConnection();
		default:
			log.info("UNRECOGNIZED SINGLA MESSAGE");
			break;
		}
	}

	private void connectClient() {
		if(!clientData.isAuthorized())
			main.getConnectedClients().add(clientData);
	}

	private void disconnectClient() {
		main.getConnectedClients().remove(clientData);
		clientData.setNotConnected();
		clientData.setNotAuthorized();
	}
	
	private void checkAuthorization() {
		if(clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode()) {
			clientData.setAuthorized();
			int clientIndex = main.getConnectedClients().indexOf(clientData);
			main.getConnectedClients().set(clientIndex, clientData);
		} else {
			clientData.setNotAuthorized();
		}
		log.info("check authorization: " + clientData.toString());
	}
	
	
	private void updateConnection() {
		
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