package com.xarql.smp;

public class ParseData extends PathMap<Object> {
	public static final String DEFAULT_STRING = "";
	public static final char DEFAULT_CHAR = ' ';
	public static final int DEFAULT_INT = 0;
	public static final boolean DEFAULT_BOOLEAN = false;

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
		if(value != null && value.getClass().equals(type))
			return (T) value;
		else
			return null;
	}

	public int getIntOrDefault(final String path, final int defaultValue) {
		final Integer value = getInt(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public int getIntOrDefault(final String path) {
		return getIntOrDefault(path, DEFAULT_INT);
	}

	public boolean getBooleanOrDefault(final String path, final boolean defaultValue) {
		final Boolean value = getBoolean(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public boolean getBooleanOrDefault(final String path) {
		return getBooleanOrDefault(path, DEFAULT_BOOLEAN);
	}

	public char getCharacterOrDefault(final String path, final char defaultValue) {
		final Character value = getCharacter(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public char getCharacterOrDefault(final String path) {
		return getCharacterOrDefault(path, DEFAULT_CHAR);
	}

	public int getIntOrDefault(final Path path, final int defaultValue) {
		final Integer value = getInt(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public int getIntOrDefault(final Path path) {
		return getIntOrDefault(path, DEFAULT_INT);
	}

	public boolean getBooleanOrDefault(final Path path, final boolean defaultValue) {
		final Boolean value = getBoolean(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public boolean getBooleanOrDefault(final Path path) {
		return getBooleanOrDefault(path, DEFAULT_BOOLEAN);
	}

	public char getCharacterOrDefault(final Path path, final char defaultValue) {
		final Character value = getCharacter(path);
		if(value == null)
			return defaultValue;
		else
			return value;
	}

	public char getCharacterOrDefault(final Path path) {
		return getCharacterOrDefault(path, DEFAULT_CHAR);
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

	public String getString(final String path) {
		return getString((Object) path);
	}

	/** String is special because any object can be converted to it */
	public String getString(final Object key) {
		final Object value = get(key);
		if(value instanceof String)
			return (String) value;
		else
			return String.valueOf(value);
	}

	public String getStringOrDefault(final String path, final String defaultValue) {
		final Object value = get(path);
		if(value instanceof String)
			return (String) value;
		else
			return defaultValue;
	}

	public String getStringOrDefault(final String path) {
		return getStringOrDefault(path, DEFAULT_STRING);
	}

	public String getStringOrDefault(final Path path, final String defaultValue) {
		final Object value = get(path);
		if(value instanceof String)
			return (String) value;
		else
			return defaultValue;
	}

	public String getStringOrDefault(final Path path) {
		return getStringOrDefault(path, DEFAULT_STRING);
	}

}
