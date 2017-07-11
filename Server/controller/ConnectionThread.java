package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionThread extends Thread {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(5588);
			System.out.println("[S]Opened server socket");
		} catch (IOException e) {
			System.err.println("Error while creating socket");
			e.printStackTrace();
		}
		while(true) {
			
			try {
				clientSocket = serverSocket.accept();
				System.out.println("[S]client socket accepted");
			} catch (IOException e) {
				System.err.println("Error while accepting connection");
				e.printStackTrace();
				closeConnection();
			}
			
			new ClientControlThread(clientSocket);
			System.out.println("[S]Connection forwarded to new thread");
			
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
