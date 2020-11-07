package com.xarql.smp;

import test.java.Car;

import java.lang.reflect.Field;

public class SimpleEncoder {

	public static final char PAIR_END = ';';
	public static final char ASSIGN = ':';
	public static final char QUOTE = '"';
	public static final char CHAR_QUOTE = '\'';
	public static final char OBJ_START = '{';
	public static final char OBJ_END = '}';
	public static final char LIST_START = '[';
	public static final char LIST_END = ']';
	public static final char SEPARATOR = ',';
	public static final char BACKSLASH = '\\';

	public static void main(String[] args) throws IllegalAccessException {
		System.out.println(encode(new Car()));
		System.out.println(prettyPrint(encode(new Car())));
		System.out.println(prettyPrint(encode(new SyntaxErrorHandler())));
		System.out.println(prettyPrint(encode(new SimpleEncoder())));
	}

	public static String prettyPrint(String smp) {
		final StringBuilder builder = new StringBuilder();
		boolean inString = false;
		boolean inChar = false;
		int indent = 0;
		for(int i = 0; i < smp.length(); i++) {
			// increase indent when starting an object


			builder.append(smp.charAt(i));
			// if the current char is a backslash, don't check the next char; just add the next char
			if(smp.charAt(i) == BACKSLASH) {
				i++;
				builder.append(smp.charAt(i));
			} else {
				if(smp.charAt(i) == QUOTE && !inChar)
					inString = !inString;
				if(smp.charAt(i) == CHAR_QUOTE && !inString)
					inChar = !inChar;
			}

			// if the current char is outside of a literal char or string, then check the current char for formatting
			// updates and add in whitespace
			if(!inChar && !inString) {
				if(smp.charAt(i) == OBJ_START)
					indent++;
				if(smp.charAt(i) == OBJ_START || smp.charAt(i) == PAIR_END) {
					builder.append('\n');
					// decrease indent when ending an object
					if (i < smp.length() - 1 && smp.charAt(i + 1) == OBJ_END)
						indent--;
					int b = indent;
					while (b-- > 0)
						builder.append('\t');
				} else if(smp.charAt(i) == ASSIGN)
					builder.append(' ');
			}
		}
		return builder.toString();
	}

	public static String encode(Object obj) throws IllegalAccessException {
		final StringBuilder output = new StringBuilder();

		if(obj == null)
			return "";
		else if(obj.getClass() == String.class)
			output.append(QUOTE).append(filterString((String) obj)).append(QUOTE);
		else if(obj.getClass() == Character.class)
			output.append(CHAR_QUOTE).append(filterChar((char) obj)).append(CHAR_QUOTE);
		else if(isBasic(obj.getClass()))
			output.append(obj.toString());
		else if(obj.getClass().isArray() || obj instanceof Iterable<?>) {
			output.append(LIST_START);
			if(obj.getClass().isArray()) {
				if(obj.getClass().getComponentType().isPrimitive()) {
					if(obj.getClass().getComponentType() == char.class) {
						for(char c : (char[]) obj)
							output.append(c).append(SEPARATOR);
					} else if(obj.getClass().getComponentType() == boolean.class) {
						for(boolean b : (boolean[]) obj)
							output.append(b).append(SEPARATOR);
					} else if(obj.getClass().getComponentType() == int.class) {
						for(int i : (int[]) obj)
							output.append(i).append(SEPARATOR);
					} else if(obj.getClass().getComponentType() == long.class) {
						for(long l : (long[]) obj)
							output.append(l).append(SEPARATOR);
					} else if(obj.getClass().getComponentType() == float.class) {
						for(float f : (float[]) obj)
							output.append(f).append(SEPARATOR);
					} else if(obj.getClass().getComponentType() == double.class) {
						for(double d : (double[]) obj)
							output.append(d).append(SEPARATOR);
					}
				}
				else
					for(Object element : (Object[]) obj)
					output.append(encode(element)).append(SEPARATOR);
			} else {
				for (Object element : (Iterable<?>) obj)
					output.append(encode(element)).append(SEPARATOR);
			}
			output.append(LIST_END);
		} else {
			output.append(OBJ_START);
			for(Field f : obj.getClass().getFields())
				output.append(f.getName()).append(ASSIGN).append(encode(f.get(obj))).append(PAIR_END);
			output.append(OBJ_END);
		}

		return output.toString();
	}

	public static String filterString(String string) {
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) == QUOTE)
				builder.append(BACKSLASH);
			builder.append(string.charAt(i));
		}
		return builder.toString();
	}

	public static String filterChar(char c) {
		if(c == CHAR_QUOTE)
			return "" + BACKSLASH + CHAR_QUOTE;
		else if(c == BACKSLASH)
			return "" + BACKSLASH + BACKSLASH;
		else
			return "" + c;
	}

	public static boolean isBasic(Class<?> type) {
		return (type.isPrimitive() && type != void.class) ||
				type == Double.class || type == Float.class || type == Long.class ||
				type == Integer.class || type == Short.class || type == Character.class ||
				type == Byte.class || type == Boolean.class || type == String.class;
	}

}
