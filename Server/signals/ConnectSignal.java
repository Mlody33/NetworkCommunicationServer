package signals;

public class ConnectSignal extends Signal {
	
	private static final long serialVersionUID = 1L;

	public ConnectSignal() {
		super(SignalName.CONNECT);
	}

	public void performSignal() {
		System.out.println("PERFORM CONNECT SIGNAL");
	}

}
