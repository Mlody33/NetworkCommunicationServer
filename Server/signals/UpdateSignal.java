package signals;

public class UpdateSignal extends Signal {
	
	private static final long serialVersionUID = 1L;

	public UpdateSignal() {
		super(SignalName.UPDATE);
	}

	public void performSignal() {
	}

}
