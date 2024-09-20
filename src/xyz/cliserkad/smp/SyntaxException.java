package xyz.cliserkad.smp;

import java.io.Serial;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

public class SyntaxException extends Exception {

	// increment when this file is modified
	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(SyntaxException.class);

	public final String msg;
	public final int line;
	public final int column;

	public SyntaxException(final String msg, final int line, final int column) {
		this.msg = msg;
		this.line = line;
		this.column = column;
	}

	@Override
	public String getMessage() {
		return msg + " on line " + line + " at column " + column;
	}

}
