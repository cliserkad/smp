package xyz.cliserkad.smp;

import xyz.cliserkad.util.Path;

import java.io.Serial;
import java.util.HashMap;

public class PathMap<V> extends HashMap<Path, V> {

	@Serial
	private static final long serialVersionUID = 20240821L;

	/**
	 * Retrieves the V related to specified Path. Returns null if no V is related. Will auto-cast String to Path.
	 *
	 * @param key String or Path
	 * @see PathMap#get(String)
	 */
	@Override
	public V get(final Object key) {
		return switch(key) {
			case String s -> super.get(new Path(s));
			case Path ignored -> super.get(key);
			case String[] strings -> super.get(new Path(strings));
			case null, default -> null;
		};
	}

	/**
	 * Shadows <code>get()</code> for better type checking
	 *
	 * @see HashMap#get(Object)
	 */
	public V get(final Path path) {
		return super.get(path);
	}

	/**
	 * Converts a String to a Path, then retrieves the V related to that Path
	 */
	public V get(final String s) {
		return super.get(new Path(s));
	}

	/**
	 * Converts a list of Strings in to a Path, then retrieves the V related to that Path. Returns null if no V is related.
	 *
	 * @param parts >= 2 Strings, not containing /
	 * @see PathMap#get(Object)
	 */
	public V get(final String... parts) {
		return super.get(new Path(parts));
	}

	/**
	 * Retrieves the V related to specified Path. Returns defaultValue if no V is related. Will auto-cast String to Path.
	 *
	 * @param key          String or Path
	 * @param defaultValue value to be returned instead of null
	 * @see PathMap#get(Object)
	 */
	@Override
	public V getOrDefault(final Object key, final V defaultValue) {
		final var val = get(key);
		if(val == null) {
			return defaultValue;
		} else {
			return val;
		}
	}

	public V getOrDefault(final Path path, final V defaultValue) {
		return getOrDefault((Object) path, defaultValue);
	}

	public V getOrDefault(final String s, final V defaultValue) {
		return getOrDefault((Object) s, defaultValue);
	}

}
