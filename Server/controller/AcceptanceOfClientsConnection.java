package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AcceptanceOfClientsConnection extends Thread {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private boolean connected = false;
	private boolean serverStatus;
	private boolean isRunning = true;
	
	public AcceptanceOfClientsConnection(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	public void setServerStatus(boolean serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	@Override
	public void run(){
		while(isRunning) {
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
					new SingleClientConnectionControl(clientSocket, connected, serverStatus).start();
					System.out.println("[S1]Connection forwarded to new thread");
					connected = false;
					System.out.println(Thread.currentThread().isInterrupted());
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public void stopThread() {
		this.isRunning = false;
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
