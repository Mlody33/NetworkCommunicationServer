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
		} catch (IOException e) {
			System.err.println("Error while creating socket");
			closeConnection();
			e.printStackTrace();
		}
		while(true) {
			
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Error while accepting connection");
				e.printStackTrace();
				closeConnection();
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
