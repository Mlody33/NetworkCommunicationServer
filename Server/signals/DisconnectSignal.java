package signals;

public class DisconnectSignal extends Signal {
	
	private static final long serialVersionUID = 1L;

	public DisconnectSignal() {
		super(SignalName.DISCONNECT);
	}

	public void performSignal() {
	}

}
