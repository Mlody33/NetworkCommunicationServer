package application;

public enum ServerStatuses {
	SERVER_TITLE("SERVER"),
	OFFLINE("Server is offline"),
	ONLINE("Server is online"),
	SET_SERVER_ONLINE("Switch ON"),
	SET_SERVER_OFFLINE("Switch OFF"),
	SERVER_NOT_EMPTY("Server has connected clients"),
	ACCEPTANCE_THREAD("ACCEPTANCE_CLIENTS_THREAD"),
	CONNECTION_THREAD("SINGLE_CLIENT_CONNECTION_THREAD");
	
	private String statusText;
	
	private ServerStatuses(String text) {
		this.statusText = text;
	}
	
	public String get() {
		return this.statusText;
	}
}
