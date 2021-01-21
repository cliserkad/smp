package com.xarql.smp;

public class BadTemplateException extends Exception {

	private static final long serialVersionUID = -7085178496879726407L;
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
