package signals;

public class ConnectSignal extends Signal {
	
	public ConnectSignal() {
		super(SignalName.CONNECT);
	}

	public void performSignal() {
		System.out.println("PERFORM CONNECT SIGNAL");
	}

}
