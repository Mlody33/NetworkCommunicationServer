package application;

public enum StatusTextDB {
	TITLE_OF_APP("SERVER"),
	OFFLINE("Server is offline"),
	ONLINE("Server is online"),
	SET_SERVER_ON("Switch ON"),
	SET_SERVER_OFF("Switch OFF");
	
	private String statusText;
	
	private StatusTextDB(String text) {
		this.statusText = text;
	}
	
	public String get() {
		return this.statusText;
	}
}
