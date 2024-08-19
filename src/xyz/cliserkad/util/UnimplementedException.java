package xyz.cliserkad.util;

import java.io.Serial;

public class UnimplementedException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public static final String SWITCH_MISS = "Missing critical branch of switch statement";

	public UnimplementedException(final String msg) {
		super(msg);
	}

}
