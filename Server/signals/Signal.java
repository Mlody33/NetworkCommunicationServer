package signals;

import java.io.Serializable;

import application.ServerMain;

public abstract class Signal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ServerMain main;
	
	public void setMain(ServerMain main) {
		this.main = main;
	}
	
	public Signal(SignalName signalName) {
		this.name = signalName.get();
	}
	
	public void performSignal() {}
	
	public String name() {
		return this.name;
	}
	
}