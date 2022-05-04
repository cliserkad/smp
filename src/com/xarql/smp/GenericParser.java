package com.xarql.smp;

import static com.xarql.smp.SimpleEncoder.ASSIGN;
import static com.xarql.smp.SimpleEncoder.BACKSLASH;
import static com.xarql.smp.SimpleEncoder.CHAR_QUOTE;
import static com.xarql.smp.SimpleEncoder.FALSE;
import static com.xarql.smp.SimpleEncoder.LIST_END;
import static com.xarql.smp.SimpleEncoder.LIST_START;
import static com.xarql.smp.SimpleEncoder.OBJ_END;
import static com.xarql.smp.SimpleEncoder.OBJ_START;
import static com.xarql.smp.SimpleEncoder.PAIR_END;
import static com.xarql.smp.SimpleEncoder.QUOTE;
import static com.xarql.smp.SimpleEncoder.SEPARATOR;
import static com.xarql.smp.SimpleEncoder.TRUE;
import static com.xarql.smp.SimpleEncoder.encode;
import static com.xarql.smp.SimpleEncoder.prettyPrint;
import java.util.Map;
import com.xarql.util.Path;
import com.xarql.util.PlaceHolder;
import test.java.Car;

public class GenericParser {

	/**
	 * Simple test against Car
	 *
	 * @param args ignored
	 * @throws IllegalAccessException when ToSMP.encode() fails
	 */
	public static void main(final String[] args) throws IllegalAccessException {
		final var smp = encode(new Car());
		if(Verifier.verifyOrPrint(smp)) {
			final Map<Path, Object> data = parse(smp);
			System.out.println(data.get(new Path("year")));
			System.out.println(data.get(new Path("model")));
			System.out.println(data.get(new Path("driveModes")));
		}
		final var smp2 = encode(new SimpleEncoder());
		System.out.println(prettyPrint(smp2));
		final Map<Path, Object> data2 = parse(smp2);
		System.out.println(data2.get(new Path("CHAR_QUOTE")));

		System.out.println();
		System.out.println();

		System.out.println(prettyPrint(encode(new Car())));
	}

	/**
	 * Creates a Map which contains the data from an smp encoded string. This makes
	 * smp values queryable in an easy to understand manner.
	 *
	 * @param smp A string representing any data encoded in smp
	 * @return a Map with keys and values from smp
	 */
	public static ParseData parse(Path currentPath, final String smp) {
		final var out = new ParseData();

		var inStringLit = false;
		var inCharLit = false;
		final var builder = new StringBuilder();
		for(var a = 0; a < smp.length(); a++) {
			if(smp.charAt(a) == BACKSLASH) {
				a++;
				builder.append(smp.charAt(a));
			} else {
				if(smp.charAt(a) == CHAR_QUOTE && !inStringLit) {
					inCharLit = !inCharLit;
				} else if(smp.charAt(a) == QUOTE && !inCharLit) {
					inStringLit = !inStringLit;
				}
				if(!inCharLit && !inStringLit) {
					if(smp.charAt(a) == ASSIGN) {
						currentPath = currentPath.append(builder.toString());
						builder.setLength(0);
					} else if(smp.charAt(a) == SEPARATOR || smp.charAt(a) == LIST_END) {
						out.putAll(parse(currentPath.delete(), currentPath.last() + ":" + builder + ";"));
						builder.setLength(0);
						final var depth = Integer.parseInt(currentPath.last());
						currentPath = currentPath.delete().append(depth + 1 + "");
						if(smp.charAt(a) == LIST_END) {
							final var listSize = Integer.parseInt(currentPath.last());
							final var array = new Object[listSize];
							for(var b = 0; b < listSize; b++) {
								array[b] = out.get(currentPath.delete().append(b + ""));
							}
							currentPath = currentPath.delete();
							out.put(currentPath, array);
						}
					} else if(smp.charAt(a) == PAIR_END) {
						if(out.get(currentPath) == null) {
							out.put(currentPath.copy(), parsePrimitive(builder.toString()));
						}
						builder.setLength(0);
						currentPath = currentPath.delete();
					} else if(smp.charAt(a) == LIST_START) {
						currentPath = currentPath.append("" + 0);
					} else if(!Character.isWhitespace(smp.charAt(a)) && smp.charAt(a) != OBJ_START) {
						builder.append(smp.charAt(a));
					}
				} else {
					builder.append(smp.charAt(a));
				}
			}
		}

		return out;
	}

	public static ParseData parse(final String smp) {
		return parse(new Path(), smp);
	}

	/**
	 * Attempts to match value text to its appropriate Java data type.
	 *
	 * @param primitive A string representing a primitive data structure
	 * @return An object with appropriate type and value
	 */
	public static Object parsePrimitive(final String primitive) {
		if(primitive.equals("")) {
			return null;
		}
		if(primitive.equals("" + OBJ_END)) {
			return new PlaceHolder();
		}
		if(primitive.charAt(0) == CHAR_QUOTE) {
			if(primitive.charAt(1) == BACKSLASH) {
				return primitive.charAt(2);
			} else {
				return primitive.charAt(1);
			}
		}
		if(primitive.trim().equalsIgnoreCase(TRUE)) {
			return true;
		} else if(primitive.trim().equalsIgnoreCase(FALSE)) {
			return false;
		} else {
			try {
				return Integer.parseInt(primitive);
			} catch(final NumberFormatException e) {
				// do nothing
			}
			try {
				return Float.parseFloat(primitive);
			} catch(final NumberFormatException e) {
				// do nothing
			}
			try {
				return Long.parseLong(primitive);
			} catch(final NumberFormatException e) {
				// do nothing
			}
			try {
				return Double.parseDouble(primitive);
			} catch(final NumberFormatException e) {
				// do nothing
			}
			return primitive.substring(1, primitive.length() - 1);
		}
	}

}
