package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import application.ServerMain;
import application.ServerStatuses;

public class AcceptanceOfClientsConnection implements Runnable{
	
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
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(5588);
		} catch (IOException e) {
			log.warning("Can't create server socket");
		}
	}
	
	private boolean acceptClientSocket() {
		try {
			clientSocket = serverSocket.accept();
			return true;
		} catch (IOException e) {
			log.warning("Can't accept connection");
			return false;
		}
	}
	
	private void forwardClientConnectionToNewThread() {
		SingleClientConnectionControl singleClientConnectionControl = new SingleClientConnectionControl(clientSocket);
		Thread singleConnectionThread = new Thread(singleClientConnectionControl);
		singleClientConnectionControl.setClientStatusConnected();
		singleClientConnectionControl.setMain(main);
		singleConnectionThread.setName(ServerStatuses.CONNECTION_THREAD.get());
		singleConnectionThread.start();
	}

	public void closeConnection() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			log.warning("Can't close connection");
		}
	}
	
	
}