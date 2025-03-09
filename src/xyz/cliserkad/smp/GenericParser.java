package xyz.cliserkad.smp;

import test.java.Car;
import xyz.cliserkad.util.Path;
import xyz.cliserkad.util.PlaceHolder;

import java.util.Map;

import static xyz.cliserkad.smp.SimpleEncoder.*;

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
	 * Creates a Map which contains the data from an smp encoded string. This makes smp values queryable in an easy to understand manner.
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
						if(smp.charAt(a) == LIST_END) {
							final var listSize = Integer.parseInt(currentPath.last());
							final var objArray = new Object[listSize];
							for(var b = 0; b < listSize; b++) {
								objArray[b] = out.get(currentPath.delete().append(b + ""));
							}
							currentPath = currentPath.delete();

							// attempt to coerce Object[] to primitive array
							var first = objArray[0];
							boolean allSameClass = true;
							for(var b = 1; b < objArray.length; b++) {
								if(objArray[b] == null || !objArray[b].getClass().equals(first.getClass())) {
									allSameClass = false;
									break;
								}
							}
							if(allSameClass) {
								switch(first) {
									case Byte b -> {
										byte[] bytes = new byte[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											bytes[i] = (byte) objArray[i];
										}
										out.put(currentPath, bytes);
									}
									case Short s -> {
										short[] shorts = new short[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											shorts[i] = (short) objArray[i];
										}
										out.put(currentPath, shorts);
									}
									case Character c -> {
										char[] chars = new char[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											chars[i] = (char) objArray[i];
										}
										out.put(currentPath, chars);
									}
									case Integer i -> {
										int[] ints = new int[objArray.length];
										for(var i2 = 0; i2 < objArray.length; i2++) {
											ints[i2] = (int) objArray[i2];
										}
										out.put(currentPath, ints);
									}
									case Float f -> {
										float[] floats = new float[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											floats[i] = (float) objArray[i];
										}
										out.put(currentPath, floats);
									}
									case Long l -> {
										long[] longs = new long[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											longs[i] = (long) objArray[i];
										}
										out.put(currentPath, longs);
									}
									case Double d -> {
										double[] doubles = new double[objArray.length];
										for(var i = 0; i < objArray.length; i++) {
											doubles[i] = (double) objArray[i];
										}
										out.put(currentPath, doubles);
									}
									case null, default -> out.put(currentPath, objArray);
								}
							} else {
								out.put(currentPath, objArray);
							}
						} else {
							currentPath = currentPath.delete().append(String.valueOf(depth + 1));
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
