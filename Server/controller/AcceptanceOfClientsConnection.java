package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import application.Main;

public class AcceptanceOfClientsConnection extends Thread {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void run(){
		createServerSocket();
		while(main.getServerDate().getServerStatus()) {
			if(acceptClientSocket())
				forwardClientConnectionToNewThread();
		}
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(5588);
			System.out.println("[S1]Opened server socket");
		} catch (IOException e) {
			System.err.println("Error while creating socket");
			e.printStackTrace();
		}
	}
	
	private boolean acceptClientSocket() {
		try {
			clientSocket = serverSocket.accept();
			System.out.println("[S1]client socket accepted");
			return true;
		} catch (IOException e) {
			System.err.println("Error while accepting connection");
			closeConnection();
			e.printStackTrace();
			return false;
		}
	}
	
	private void forwardClientConnectionToNewThread() {
		SingleClientConnectionControl singleClientConnectionControl = new SingleClientConnectionControl(clientSocket);
		singleClientConnectionControl.start();
		singleClientConnectionControl.setMain(main);
		System.out.println("[S1]Connection forwarded to new thread");
	}

	private void closeConnection() {
		try {
			serverSocket.close();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Error while close connection");
		}
	}

}
