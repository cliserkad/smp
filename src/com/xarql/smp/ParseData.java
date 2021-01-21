package com.xarql.smp;

public class ParseData extends PathMap<Object> {

	public Integer getInt(final String key) {
		return getType(key, Integer.class);
	}

	public Boolean getBoolean(final String key) {
		return getType(key, Boolean.class);
	}

	public Character getCharacter(final String key) {
		return getType(key, Character.class);
	}

	public Integer getInt(final Path key) {
		return getType(key, Integer.class);
	}

	public Boolean getBoolean(final Path key) {
		return getType(key, Boolean.class);
	}

	public Character getCharacter(final Path key) {
		return getType(key, Character.class);
	}

	/**
	 * Attempts to retrieve a value and cast it to the given type.
	 * Returns null if the value is incompatible with the given type.
	 */
	private <T> T getType(final Object key, final Class<T> type) {
		final Object value = get(key);
		if(value.getClass().equals(type))
			return (T) value;
		else
			return null;
	}

	public int getIntOrDefault(final String s, final int defaultValue) {
		final Integer value = getInt(s);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public boolean getBooleanOrDefault(final String s, final boolean defaultValue) {
		final Boolean value = getBoolean(s);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public char getCharacterOrDefault(final String s, final char defaultValue) {
		final Character value = getCharacter(s);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public int getIntOrDefault(final Path path, final int defaultValue) {
		final Integer value = getInt(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public boolean getBooleanOrDefault(final Path path, final boolean defaultValue) {
		final Boolean value = getBoolean(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public char getCharacterOrDefault(final Path path, final char defaultValue) {
		final Character value = getCharacter(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	private <T> T getTypeOrDefault(final Object key, final Class<T> type, final T defaultValue) {
		final T val = getType(key, type);
		if(val == null)
			return defaultValue;
		else
			return val;
	}

	public String getString(final String... parts) {
		return getString(new Path(parts));
	}

	public String getString(final Path path) {
		return getString((Object) path);
	}

	public String getString(final String s) {
		return getString((Object) s);
	}

	/** String is special because any object can be converted to it */
	public String getString(final Object key) {
		final Object value = get(key);
		if(value instanceof String)
			return (String) value;
		else
			return String.valueOf(value);
	}

	public String getStringOrDefault(final String s, final String defaultValue) {
		final Object value = get(s);
		if(value instanceof String)
			return (String) value;
		else
			return defaultValue;
	}

	public String getStringOrDefault(final Path path, final String defaultValue) {
		final Object value = get(path);
		if(value instanceof String)
			return (String) value;
		else
			return defaultValue;
	}

}
