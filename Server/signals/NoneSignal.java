package signals;

public class NoneSignal extends Signal {
	
	private static final long serialVersionUID = 1L;

	public NoneSignal() {
		super(SignalName.NONE);
	}

	public void performSignal() {
	}

}
