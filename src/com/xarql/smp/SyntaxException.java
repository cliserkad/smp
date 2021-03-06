package com.xarql.smp;

public class SyntaxException extends Exception {

	// increment when this file is modified
	public static final long serialVersionUID = 1;

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
