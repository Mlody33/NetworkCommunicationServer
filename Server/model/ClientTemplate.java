package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
	
	LocalDateTime getTimeConnection();
	void setTimeConnection(LocalDateTime timeConnection);
}
