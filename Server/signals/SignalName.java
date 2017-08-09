package signals;

public enum SignalName {
	NONE("None"),
	CONNECT("Conenct"),
	DISCONNECT("Disconnect"),
	AUTHORIZE("Authorize"),
	UPDATE("Update");
	
	private String name;
	
	SignalName(String name){
		this.name = name;
	}
	
	public String get() {
		return this.name;
	}
	
}
