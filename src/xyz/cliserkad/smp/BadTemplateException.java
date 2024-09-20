package xyz.cliserkad.smp;

import java.io.Serial;

import static xyz.cliserkad.util.SerialVersionUIDGenerator.generateSerialVersionUID;

public class BadTemplateException extends Exception {

	@Serial
	private static final long serialVersionUID = generateSerialVersionUID(BadTemplateException.class);
	private final Class<?> template;
	private final ReflectiveOperationException cause;

	public BadTemplateException(final Class<?> template, final ReflectiveOperationException cause) {
		this.template = template;
		this.cause = cause;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public String getMessage() {
		return template + " was unsuitable for use by TemplateParser";
	}

}
