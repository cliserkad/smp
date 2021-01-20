package com.xarql.smp;

import java.util.HashMap;

public class PathMap<V> extends HashMap<Path, V> {

	/**
	 * Retrieves the V related to specified Path.
	 * Returns null if no V is related.
	 * Will auto-cast String to Path.
	 * @param key String or Path
	 * @see PathMap#get(String)
	 */
	@Override
	public V get(Object key) {
		if(key instanceof String) {
			return get((String) key);
		} else if(key instanceof Path) {
			return super.get(key);
		} else {
			return null;
		}
	}

	/**
	 * Shadows <code>get()</code> for better type checking
	 * @see HashMap#get(Object)
	 */
	public V get(Path path) {
		return super.get(path);
	}

	/**
	 * Converts a String to a Path,
	 * then retrieves the V related to that Path
	 */
	public V get(String s) {
		return super.get(new Path(s));
	}

	/**
	 * Converts a list of Strings in to a Path,
	 * then retrieves the V related to that Path.
	 * Returns null if no V is related.
	 * @param parts >= 2 Strings, not containing /
	 * @see PathMap#get(Object) 
	 */
	public V get(String...parts) {
		return get(new Path(parts));
	}

	/**
	 * Retrieves the V related to specified Path.
	 * Returns defaultValue if no V is related.
	 * Will auto-cast String to Path.
	 * @param key String or Path
	 * @param defaultValue value to be returned instead of null
	 * @see PathMap#get(Object)    
	 */
	@Override
	public V getOrDefault(Object key, V defaultValue) {
		V val = get(key);
		if(val == null)
			return defaultValue;
		else
			return val;
	}
}
