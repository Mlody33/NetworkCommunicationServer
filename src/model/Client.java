package model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client implements ClientTemplate {

	private SimpleStringProperty identyfier;
	private ObjectProperty<LocalDate> timeConnection;
	
	public Client() {
		this.identyfier = new SimpleStringProperty(null);
		this.timeConnection = new SimpleObjectProperty<LocalDate>(null);
	}
	
	public Client (String identyfier, LocalDate timeConnection) {
		this.identyfier = new SimpleStringProperty(identyfier);
		this.timeConnection = new SimpleObjectProperty<LocalDate>(timeConnection);
	}
	
	@Override
	public String getIdentyfier() {
		return identyfier.get();
	}
	@Override
	public void setIdentyfier(String identyfier) {
		this.identyfier = new SimpleStringProperty(identyfier);
	}

	@Override
	public LocalDate getTimeConnection() {
		return timeConnection.get();
	}
	@Override
	public void setTimeConnection(LocalDate timeConnection) {
		this.timeConnection = new SimpleObjectProperty<LocalDate>(timeConnection);
	}
	
	
	@Override
	public String toString() {
		return "ID: "+identyfier.get()+" TC: "+timeConnection.get();
	}

}
