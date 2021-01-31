package com.xarql.util;

/**
 * Holds a Java object
 *
 * @author Bryan Johnson
 * @param <T> Type of object to hold
 */
public final class Wrapper<T> {

	public final T object;

	/**
	 * Constructs with given inputs
	 * 
	 * @param object to be wrapped
	 */
	public Wrapper(final T object) {
		this.object = object;
	}

	public String name() {
		if(object.getClass().isArray()) {
			return object.getClass().getComponentType().getSimpleName() + "[]";
		} else {
			return object.getClass().getSimpleName();
		}
	}

}
