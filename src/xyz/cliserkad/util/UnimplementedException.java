package xyz.cliserkad.util;

import java.io.Serial;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

public class UnimplementedException extends Exception {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(UnimplementedException.class);

	public static final String SWITCH_MISS = "Missing critical branch of switch statement";

	public UnimplementedException(final String msg) {
		super(msg);
	}

}
