package com.xarql.util;

import java.io.*;

public class SerialCopier<T extends Serializable> implements Copier<T> {
	public static final BestList<Class<?>> SIMPLE_TYPES = new BestList<>(Integer.class, Boolean.class, String.class, Long.class, Double.class, Character.class);

	private final T object;

	public static void main(String[] args) throws Exception {
		Path p1 = new Path("dev/jokes/funny");
		Path p2 = copyAndCheck(p1);
		System.out.println(p1);
		System.out.println(p2);
	}

	public SerialCopier(final T object) {
		this.object = object;
	}

	public static <T extends Serializable> T copy(T input) throws IOException, ClassNotFoundException {
		// Create byte buffer
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// write input to byte buffer
		new ObjectOutputStream(bos).writeObject(input);
		// read byte buffer to create the copy
		return (T) new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray())).readObject();
	}

	public static <T extends Serializable> T copyAndCheck(T input) throws IOException, ClassNotFoundException {
		final T copy = copy(input);
		if(copy == input)
			throw new IllegalStateException("The generated copy shares the memory address of the input");
		if(!copy.equals(input))
			throw new IllegalStateException("The generated copy reported that it isn't equal to the input");
		return copy;
	}

	public static boolean isSimple(Object obj) {
		return obj.getClass().isPrimitive() || SIMPLE_TYPES.contains(obj.getClass());
	}

	@Override
	public Copy<T> copy() {
		try {
			return new Copy<>(object, copy(object));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public T self() {
		return object;
	}
}
