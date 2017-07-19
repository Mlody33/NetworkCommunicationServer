package model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client implements ClientTemplate, Externalizable {

	private static final long serialVersionUID = 1L;
	private SimpleStringProperty identyfier;
	private SimpleIntegerProperty clientNumber;
	private SimpleIntegerProperty authorizationCode;
	private SimpleBooleanProperty authorized;
	private ObjectProperty<LocalDate> timeConnection;
	
	public Client() {
		this.identyfier = new SimpleStringProperty(null);
		this.authorizationCode = new SimpleIntegerProperty(0);
		this.clientNumber = new SimpleIntegerProperty(0);
		this.timeConnection = new SimpleObjectProperty<LocalDate>(null);
		this.authorized = new SimpleBooleanProperty(false);
	}
	
	public Client (String identyfier, int authorizationCode, int clientNumber, LocalDate timeConnection, boolean authorized) {
		this.identyfier = new SimpleStringProperty(identyfier);
		this.authorizationCode = new SimpleIntegerProperty(authorizationCode);
		this.clientNumber = new SimpleIntegerProperty(clientNumber);
		this.timeConnection = new SimpleObjectProperty<LocalDate>(timeConnection);
		this.authorized = new SimpleBooleanProperty(authorized);
	}
	
	@Override
	public boolean isAuthorized() {
		return authorized.get();
	}
	
	@Override
	public void setAuthorized(boolean authorized) {
		this.authorized = new SimpleBooleanProperty(authorized);
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
	public int getAuthorizationCode() {
		return authorizationCode.get();
	}

	@Override
	public void setAuthorizationCode(int code) {
		this.authorizationCode = new SimpleIntegerProperty(code);
	}
	
	@Override
	public int getClientNumber() {
		return clientNumber.get();
	}

	@Override
	public void setClientNumber(int number) {
		this.clientNumber = new SimpleIntegerProperty(number);
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
		return "Client [identyfier=" + identyfier + ", clientNumber=" + clientNumber + ", authorizationCode="
				+ authorizationCode + ", authorized=" + authorized + ", timeConnection=" + timeConnection + "]";
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.authorizationCode = new SimpleIntegerProperty(in.readInt());
		this.clientNumber = new SimpleIntegerProperty(in.readInt());
		this.authorized = new SimpleBooleanProperty(in.readBoolean());
		this.identyfier = new SimpleStringProperty(in.readLine());
		this.timeConnection = new SimpleObjectProperty<LocalDate>(LocalDate.now());//TODO change this to read exact date
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(getAuthorizationCode());
		out.writeInt(getClientNumber());
		out.writeBoolean(isAuthorized());
		out.writeBytes(getIdentyfier());
		out.writeBytes(getTimeConnection().toString());
	}

}
