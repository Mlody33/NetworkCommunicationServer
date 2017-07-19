package model;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.SimpleBooleanProperty;

public interface ClientTemplate extends Serializable {
	
	String getIdentyfier();
	void setIdentyfier(String identyfier);
	int getAuthorizationCode();
	void setAuthorizationCode(int number);
	int getClientNumber();
	void setClientNumber(int number);
	LocalDate getTimeConnection();
	void setTimeConnection(LocalDate timeConnection);
	boolean isAuthorized();
	void setAuthorized(boolean authorized);

}
