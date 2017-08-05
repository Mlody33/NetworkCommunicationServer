package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import application.ServerMain;

public class AcceptanceOfClientsConnection extends Thread { //TODO implements Runnable instead extend Thread
	
	private Logger log = Logger.getLogger("Server "+this.getClass().getName());
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ServerMain main;
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	@Override
	public void run(){
		createServerSocket();
		while(main.getServerDate().isServerOnline()) {
			if(acceptClientSocket())
				forwardClientConnectionToNewThread();
		}
		log.warning("OUT OF LOOP");
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(5588);
		} catch (IOException e) {
			log.warning("Error while creating socket");
			e.printStackTrace();
		}
	}
	
	private boolean acceptClientSocket() {
		try {
			clientSocket = serverSocket.accept();
			return true;
		} catch (IOException e) {
			log.warning("Error while accepting connection");
			e.printStackTrace();
			return false;
		}
	}
	
	private void forwardClientConnectionToNewThread() {
		SingleClientConnectionControl singleClientConnectionControl = new SingleClientConnectionControl(clientSocket);
		singleClientConnectionControl.setClientStatusConnected();
		singleClientConnectionControl.setMain(main);
		singleClientConnectionControl.start();
	}

	public void closeConnection() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			log.warning("Error while close connection");
		}
	}
}