package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client implements ClientTemplate, Externalizable {

	private static final long serialVersionUID = 1L;
	private SimpleStringProperty identyfier;
	private SimpleIntegerProperty number;
	private ObjectProperty<LocalDate> timeConnection;
	
	public Client() {
		this.identyfier = new SimpleStringProperty(null);
		this.number = new SimpleIntegerProperty(0);
		this.timeConnection = new SimpleObjectProperty<LocalDate>(null);
	}
	
	public Client (String identyfier, int number, LocalDate timeConnection) {
		this.identyfier = new SimpleStringProperty(identyfier);
		this.number = new SimpleIntegerProperty(number);
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
	public int getNumber() {
		return number.get();
	}

	@Override
	public void setNumber(int number) {
		this.number = new SimpleIntegerProperty(number);
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
		return "ID: "+identyfier.get()+" N: "+number.get()+" TC: "+timeConnection.get();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.number = new SimpleIntegerProperty(in.readInt());
		this.identyfier = new SimpleStringProperty(in.readLine());
		this.timeConnection = new SimpleObjectProperty<LocalDate>(LocalDate.now());//TODO change this to read exact date
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(getNumber());
		out.writeBytes(getIdentyfier());
		out.writeBytes(getTimeConnection().toString());
	}

}
