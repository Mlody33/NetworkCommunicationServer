package model;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Server {
	private int AUTHORIZATION_CODE;
	private boolean serverStatus;
	private ObservableList<Client> connectedClients = FXCollections.observableArrayList();

	public Server() {
		AUTHORIZATION_CODE = new Random().nextInt(8999)+1000;
		setServerStatusOffline();
	}
	
	public void generateAuthorizationCode() {
		AUTHORIZATION_CODE = new Random().nextInt(8999)+1000;
	}
	
	public void setServerStatusOnline() {
		this.serverStatus = true;
	}
	
	public void setServerStatusOffline() {
		this.serverStatus = false;
	}
	
	public boolean isServerOnline() {
		return this.serverStatus;
	}
	
	public int getAuthorizationCode() {
		return this.AUTHORIZATION_CODE;
	}
	
	public ObservableList<Client> getConnectedClients() {
		return this.connectedClients;
	}
	
}
