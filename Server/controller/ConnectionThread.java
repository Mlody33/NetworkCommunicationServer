package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionThread extends Thread {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private boolean connected = false;
	private boolean serverStatus;
	
	public ConnectionThread(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(5588);
			System.out.println("[S1]Opened server socket");
		} catch (IOException e) {
			System.err.println("Error while creating socket");
			e.printStackTrace();
		}
		while(serverStatus) {
			System.out.println("WORKING THREAD");
			try {
				clientSocket = serverSocket.accept();
				connected = true;
				System.out.println("[S1]client socket accepted");
			} catch (IOException e) {
				System.err.println("Error while accepting connection");
				connected = false;
				closeConnection();
				e.printStackTrace();
			}
			
			if(connected) {
				new ClientControlThread(clientSocket, connected, serverStatus).start();
				System.out.println("[S1]Connection forwarded to new thread");
				connected = false;
			}
		}
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
