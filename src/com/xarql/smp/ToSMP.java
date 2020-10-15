package com.xarql.smp;

import test.java.Car;

import java.lang.reflect.Field;

public class ToSMP {

	public static final char PAIR_END = ';';
	public static final char ASSIGN = ':';
	public static final char QUOTE = '"';
	public static final char CHAR_QUOTE = '\'';
	public static final char OBJ_START = '{';
	public static final char OBJ_END = '}';
	public static final char LIST_START = '[';
	public static final char LIST_END = ']';

	public static void main(String[] args) throws IllegalAccessException {
		System.out.println(encode(new Car()));
		System.out.println(prettyPrint(encode(new Car())));
	}

	public static String prettyPrint(String smp) {
		final StringBuilder builder = new StringBuilder();
		int indent = 0;
		for(int i = 0; i < smp.length(); i++) {
			// increase indent when starting an object
			if(smp.charAt(i) == OBJ_START)
				indent++;

			builder.append(smp.charAt(i));
			if(smp.charAt(i) == OBJ_START || smp.charAt(i) == PAIR_END) {
				builder.append('\n');
				// decrease indent when ending an object
				if(i < smp.length() - 1 && smp.charAt(i + 1) == OBJ_END)
					indent--;
				int b = indent;
				while(b --> 0)
					builder.append('\t');
			}
			else if(smp.charAt(i) == ASSIGN)
				builder.append(' ');
		}
		return builder.toString();
	}

	public static String encode(Object obj) throws IllegalAccessException {
		final StringBuilder output = new StringBuilder();
		final Class<?> objClass = obj.getClass();

		output.append(OBJ_START);
		for(Field f : objClass.getFields()) {
			output.append(f.getName());

			// if a value exists
			if(isBasic(f.getType()) || f.get(obj) != null) {
				output.append(ASSIGN);

				if(f.getType() == String.class)
					output.append(QUOTE).append(f.get(obj)).append(QUOTE);
				else if(f.getType() == char.class || f.getType() == Character.class)
					output.append(CHAR_QUOTE).append(f.get(obj)).append(CHAR_QUOTE);
				else if(isBasic(f.getType()))
					output.append(f.get(obj).toString());
				else if(f.get(obj) != null)
					output.append(encode(f.get(obj)));
			}
			output.append(PAIR_END);
		}
		output.append(OBJ_END);

		return output.toString();
	}

	public static boolean isBasic(Class<?> type) {
		return (type.isPrimitive() && type != void.class) ||
				type == Double.class || type == Float.class || type == Long.class ||
				type == Integer.class || type == Short.class || type == Character.class ||
				type == Byte.class || type == Boolean.class || type == String.class;
	}

}
