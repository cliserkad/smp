package com.xarql.smp;

public class BadTemplateException extends Exception {

	private final Class<?> template;
	private final ReflectiveOperationException cause;

	public BadTemplateException(Class<?> template, ReflectiveOperationException cause) {
		this.template = template;
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		return template + " was unsuitable for use by TemplateParser";
	}

	public Throwable getCause() {
		return cause;
	}

}
