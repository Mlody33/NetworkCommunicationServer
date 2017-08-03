package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import application.ServerMain;
import model.Client;
import model.TestCom;

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
		while(main.getServerDate().isServerOnline()) {
			
			Client testCom = new Client();
			try {
				log.warning("_____________________________________________________________________czekam na obiekt");
				testCom = (Client)incomeStream.readObject();
				log.info("[S2]Received client: "+testCom.toString());
			} catch (ClassNotFoundException | IOException e) {
				closeConnection();
				e.printStackTrace();
			}
			
			testCom.setClientNumber(testCom.getClientNumber()+1000);
			testCom.setAuthorized();
			testCom.setConnected();
			
			try {
				outcomeStream.writeObject(testCom);
				outcomeStream.flush();
				log.info("[S2]Send client: "+testCom.toString());
			} catch (IOException e) {
				closeConnection();
				e.printStackTrace();
			}
			
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