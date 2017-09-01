package model;

import java.io.Serializable;
import java.time.LocalDateTime;

import controller.Signal;

public interface ClientTemplate extends Serializable {
	
	int getClientNumber();
	void setClientNumber(int number);
	
	boolean isConnected();
	void setConnected();
	void setNotConnected();
	
	int getAuthorizationCode();
	void setAuthorizationCode(int number);
	
	boolean isAuthorized();
	void setAuthorized();
	void setNotAuthorized();
	
	Signal getSignalToCommunicationWithServer();
	void setSignalToCommunicationWithServer(Signal signal);
	
	LocalDateTime getTimeConnection();
	void setTimeConnection(LocalDateTime timeConnection);
}
