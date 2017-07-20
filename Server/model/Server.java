package model;

import java.util.Random;

public class Server {
	private int AUTHORIZATION_CODE;
	private boolean serverStatus;

	public Server() {
		AUTHORIZATION_CODE = new Random().nextInt(8999)+1000;
		serverStatus = false;
	}
	
	public void setServerStatusOnline() {
		this.serverStatus = true;
	}
	
	public void setServerStatusOffline() {
		this.serverStatus = false;
	}
	
	public boolean getServerStatus() {
		return this.serverStatus;
	}
	
	public int getAuthorizationCode() {
		return this.AUTHORIZATION_CODE;
	}
	
}
