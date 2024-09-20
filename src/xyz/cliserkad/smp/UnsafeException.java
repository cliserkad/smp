package xyz.cliserkad.smp;

import java.io.Serial;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

public class UnsafeException extends Exception {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(UnsafeException.class);
	private final Exception cause;

	public UnsafeException(final Exception cause) {
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public String getMessage() {
		return "was trying to do something the bad way";
	}

}
