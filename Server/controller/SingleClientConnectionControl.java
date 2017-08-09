package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import application.ServerMain;
import model.Client;

public class SingleClientConnectionControl implements Runnable {
	
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
		}
	}
	
	private void createInputOutputStream() {
		try {
			incomeStream = new ObjectInputStream(clientSocket.getInputStream());
			outcomeStream = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			log.warning("Can't create stream");
		}
	}
	
	private void readDataFromClient() {
		Client clientDataToRead = new Client();
		try {
			clientDataToRead = (Client)incomeStream.readObject();
			clientData.setClientData(clientDataToRead);
		} catch (ClassNotFoundException | IOException e) {
			closeConnection();
		}
	}
	
	private void sendDataToClient() {
		Client clientDataToSend = new Client();
		if(!main.getServerDate().isServerOnline())
			clientData.setNotConnected();
		clientDataToSend.setClientData(clientData);
		try {
			outcomeStream.writeObject(clientDataToSend);
			outcomeStream.flush();
		} catch (IOException e) {
			closeConnection();
		}
	}
	
	private void checkClientStatus() {
		clientData.getSignalToCommunicationWithServer().performSignal();
	}

	private void connectClient() {
		if(!clientData.isAuthorized()) {
			main.getServerDate().getConnectedClients().add(clientData);
			main.getServerController().setUINewClientConnection();
		}
	}

	private void disconnectClient() {
		main.getServerDate().getConnectedClients().remove(clientData);
		main.getServerController().setUINewClientDisconnection();
		clientData.setNotConnected();
		clientData.setNotAuthorized();
	}
	
	private void checkAuthorization() {
		if(clientData.getAuthorizationCode() == main.getServerDate().getAuthorizationCode()) {
			clientData.setAuthorized();
			main.getServerController().setUINewClientAuthorization();
		} else
			clientData.setNotAuthorized();
		updateClientDataInTable();
	}
	
	private void updateConnection() {
		updateClientDataInTable();
		main.getServerController().setUINewClientUpdate();
	}

	private void updateClientDataInTable() {
		int clientIndex = main.getServerDate().getConnectedClients().indexOf(clientData);
		main.getServerDate().getConnectedClients().set(clientIndex, clientData);
	}
	
	public void closeConnection() {
		try {
			incomeStream.close();
			outcomeStream.close();
			clientSocket.close();
		} catch (IOException e) {
			log.warning("Can't close connection with client");
		}
	}
	
}