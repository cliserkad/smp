package com.xarql.util;

import java.io.Serializable;
import java.util.Arrays;

public class Path implements Serializable {

	private static final long serialVersionUID = 5596584287964181971L;
	public static final char PATH_SEPARATOR = '/';

	public static void checkArray(final String[] array) {
		if(array == null) {
			throw new IllegalArgumentException("String[] must not be null");
		}
		if(array.length == 0) {
			throw new IllegalArgumentException("String[] must not be empty");
		}
	}

	public static boolean isArrayEmpty(final String[] array) {
		return array == null || array.length == 0;
	}

	private final String[] parts;

	public Path() {
		this(new String[0]);
	}

	public Path(final String... parts) {
		if(isArrayEmpty(parts)) {
			this.parts = new String[0];
		} else {
			for(var i = 0; i < parts.length; i++) {
				if(parts[i].contains(PATH_SEPARATOR + "")) {
					throw new IllegalArgumentException("The part \"" + parts[i] + "\" @ " + i + " contains " + PATH_SEPARATOR);
				}
			}
			this.parts = parts;
		}
	}

	public Path(final String raw) {
		this(raw.split("" + PATH_SEPARATOR));
	}

	public Path append(final Path p) {
		final var newParts = getParts(-p.size());
		for(var i = 0; i < p.size(); i++) {
			newParts[i + size()] = p.part(i);
		}
		return new Path(newParts);
	}

	public Path append(final String s) {
		return append(new Path(s));
	}

	public Path append(final String... parts) {
		checkArray(parts);
		return append(new Path(parts));
	}

	public Path copy() {
		return new Path(getParts());
	}

	public Path delete() {
		return delete(1);
	}

	public Path delete(final int amount) {
		return new Path(getParts(0, size() - amount));
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o) {
			return true;
		} else if(o instanceof Path) {
			final var that = (Path) o;
			return Arrays.equals(getParts(), that.getParts());
		} else if(o instanceof String) {
			final var that = new Path((String) o);
			return equals(that);
		} else {
			return false;
		}
	}

	public String first() {
		return parts[0];
	}

	public String[] getParts() {
		return getParts(0);
	}

	public String[] getParts(final int offset) {
		return getParts(offset, size());
	}

	/**
	 * Creates a copy of this StringPath's parts.
	 *
	 * @param offset Amount of null elements to prepend
	 */
	public String[] getParts(int offset, final int amount) {
		final var diff = Math.abs(offset);
		offset = Math.max(0, offset);
		final var parts = new String[amount + diff];
		System.arraycopy(this.parts, 0, parts, offset, amount);
		return parts;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(getParts());
	}

	public String last() {
		return parts[size() - 1];
	}

	public String part(final int index) {
		return parts[index];
	}

	public Path prepend(final Path p) {
		final var newParts = getParts(p.size());
		for(var i = 0; i < p.size(); i++) {
			newParts[i] = p.part(i);
		}
		return new Path(newParts);
	}

	public Path prepend(final String s) {
		return prepend(new Path(s));
	}

	public Path prepend(final String... parts) {
		checkArray(parts);
		return prepend(new Path(parts));
	}

	public int size() {
		return parts.length;
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		for(var i = 0; i < parts.length; i++) {
			builder.append(parts[i]);
			if(i < parts.length - 1) {
				builder.append(PATH_SEPARATOR);
			}
		}
		return builder.toString();
	}

}
