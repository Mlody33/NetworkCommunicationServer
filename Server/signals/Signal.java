package signals;

public abstract class Signal {
	
	private String name;
	
	public Signal(SignalName signalName) {
		this.name = signalName.get();
	}
	
	public void performSignal() {}
	
	public String name() {
		return this.name;
	}
	
}