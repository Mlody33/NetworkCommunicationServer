package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import application.Main;

public class AcceptanceOfClientsConnection extends Thread {
	
	private Logger log = Logger.getLogger("Server "+this.getClass().getName());
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void run(){
		createServerSocket();
		while(main.getServerDate().isServerOnline()) {
			if(acceptClientSocket())
				forwardClientConnectionToNewThread();
		}
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(5588);
			log.info("[S1]Opened server socket");
		} catch (IOException e) {
			log.warning("Error while creating socket");
			e.printStackTrace();
		}
	}
	
	private boolean acceptClientSocket() {
		try {
			clientSocket = serverSocket.accept();
			log.info("[S1]client socket accepted");
			return true;
		} catch (IOException e) {
			log.warning("Error while accepting connection");
			closeConnection();
			e.printStackTrace();
			return false;
		}
	}
	
	private void forwardClientConnectionToNewThread() {
		SingleClientConnectionControl singleClientConnectionControl = new SingleClientConnectionControl(clientSocket);
		singleClientConnectionControl.start();
		singleClientConnectionControl.setMain(main);
		log.info("[S1]Connection forwarded to new thread");
	}

	private void closeConnection() {
		try {
			serverSocket.close();
			clientSocket.close();
		} catch (IOException e) {
			log.warning("Error while close connection");
		}
	}

}
