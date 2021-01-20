package com.xarql.smp;

import java.util.Arrays;

public class ExceptionPack extends Exception {

	public static final String PACK_MSG = "The following exceptions were packed together:\n";

	// increment when this file is modified
	public static final long serialVersionUID = 1;

	private final Exception[] exceptions;

	public ExceptionPack(final Exception[] exceptions) {
		this.exceptions = Arrays.copyOf(exceptions, exceptions.length);
	}

	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		}
		if(object == null || !(object instanceof ExceptionPack)) {
			return false;
		}
		final var other = (ExceptionPack) object;
		return Arrays.equals(exceptions, other.exceptions);
	}

	public Exception[] getExceptions() {
		return Arrays.copyOf(exceptions, exceptions.length);
	}

	@Override
	public String getMessage() {
		final var builder = new StringBuilder().append(PACK_MSG);
		for(final Exception e : exceptions) {
			builder.append(e.getMessage()).append('\n');
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(exceptions);
	}

}
