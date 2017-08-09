package application;

public enum ServerStatuses {
	SERVER_TITLE("SERVER"),
	ACCEPTANCE_THREAD("ACCEPTANCE_CLIENTS_THREAD"),
	CONNECTION_THREAD("SINGLE_CLIENT_CONNECTION_THREAD"),
	OFFLINE("Server is offline"),
	ONLINE("Server is online"),
	SET_SERVER_ONLINE("Switch ON"),
	SET_SERVER_OFFLINE("Switch OFF"),
	SERVER_NOT_EMPTY("Server has connected clients"),
	NEW_CONNECTION("New client connected"),
	NEW_AUTHORIZATION("Client authorized"),
	NEW_DISCONNECTION("Client disconnected"),
	NEW_UPDATE("Client updated own status");
	
	private String statusText;
	
	private ServerStatuses(String text) {
		this.statusText = text;
	}
	
	public String get() {
		return this.statusText;
	}
}
