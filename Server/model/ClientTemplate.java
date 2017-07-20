package model;

import java.io.Serializable;
import java.time.LocalDate;

public interface ClientTemplate extends Serializable {
	
	int getClientNumber();
	void setClientNumber(int number);
	
	boolean isConnected();
	void setConnected(boolean connected);
	
	int getAuthorizationCode();
	void setAuthorizationCode(int number);
	
	boolean isAuthorized();
	void setAuthorized(boolean authorized);
	
	LocalDate getTimeConnection();
	void setTimeConnection(LocalDate timeConnection);

}
