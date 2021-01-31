package com.xarql.util;

public abstract class NamedObject {

	private String name;

	public NamedObject(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected void setName(final String name) {
		this.name = name;
	}

}
