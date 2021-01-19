package com.xarql.smp;

public class UnsafeException extends Exception {

	private final Exception cause;

	public UnsafeException(Exception cause) {
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		return "was trying to do something the bad way";
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

}
