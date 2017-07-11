package model;

import java.time.LocalDate;

public interface ClientTemplate {
	
	String getIdentyfier();
	void setIdentyfier(String identyfier);
	LocalDate getTimeConnection();
	void setTimeConnection(LocalDate timeConnection);

}
