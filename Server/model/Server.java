package model;

import java.util.Random;

public class Server {
	private int AUTHORIZATION_CODE;
	private boolean serverStatus;
	//TODO Move here list of connected client

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
	
}
