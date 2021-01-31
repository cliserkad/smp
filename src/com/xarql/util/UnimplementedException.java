package com.xarql.util;

public class UnimplementedException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String SWITCH_MISS = "Missing critical branch of switch statement";

	public UnimplementedException(final String msg) {
		super(msg);
	}

}
