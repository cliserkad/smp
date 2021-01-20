package com.xarql.smp;

public class UnsafeException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 7717159791855060651L;
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
