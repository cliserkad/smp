package xyz.cliserkad.util;

import java.io.Serial;
import java.util.List;

public class ExceptionPack extends Exception {

	@Serial
	private static final long serialVersionUID = 1939447964468120636L;

	public static final String NO_MSG = "No message provided";

	private final Exception[] exceptions;

	public ExceptionPack(final List<Exception> exceptions) {
		this.exceptions = exceptions.toArray(new Exception[exceptions.size()]);
	}

	public ExceptionPack(final Exception[] exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public String getMessage() {
		final var builder = new StringBuilder();
		builder.append("{\n");
		for(final Exception e : exceptions) {
			builder.append('\t');
			builder.append(e.getClass().getCanonicalName());
			builder.append(" >> ");
			if(e.getMessage() != null) {
				builder.append(e.getMessage().replace("\n", " | "));
			} else {
				builder.append(NO_MSG);
			}
			builder.append("\n");
			builder.append(stackTrace(e.getStackTrace()));
			builder.append("\n");
		}
		builder.append("}");
		return builder.toString();
	}

	public static String stackTrace(final StackTraceElement[] elements) {
		final var builder = new StringBuilder();
		var i = 0;
		for(final StackTraceElement ste : elements) {
			builder.append("\t\tat ");
			builder.append(ste.toString());
			i++;
			if(i < 6) {
				builder.append("\n");
			} else {
				break;
			}
		}
		return builder.toString();
	}

	public Exception[] getExceptions() {
		final var out = new Exception[exceptions.length];
		System.arraycopy(exceptions, 0, out, 0, exceptions.length);
		return out;
	}

	@Override
	public String toString() {
		return "ExceptionPack with " + exceptions.length + " elements " + getMessage();
	}

}
