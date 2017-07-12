package model;

import java.io.Serializable;
import java.time.LocalDate;

public interface ClientTemplate extends Serializable {
	
	String getIdentyfier();
	void setIdentyfier(String identyfier);
	int getNumber();
	void setNumber(int number);
	LocalDate getTimeConnection();
	void setTimeConnection(LocalDate timeConnection);

}
