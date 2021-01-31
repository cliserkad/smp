package com.xarql.util;

public class UnimplementedException extends Exception {
	private static final long serialVersionUID = 1L;

	private static final String SWITCH_MISS = "A switch statement is missing a critical branch";

	public UnimplementedException(String msg) {
		super(msg);
	}

}
