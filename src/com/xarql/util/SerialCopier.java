package com.xarql.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("ClassCanBeRecord") // Making this class in to a record would make "object" public
public class SerialCopier<T extends Serializable> implements Copier<T> {

	public static final BestList<Class<?>> SIMPLE_TYPES = new BestList<>(Integer.class, Boolean.class, String.class, Long.class, Double.class, Character.class);

	private final T object;

	public static void main(final String[] args) throws Exception {
		final var p1 = new Path("dev/jokes/funny");
		final var p2 = copyAndCheck(p1);
		System.out.println(p1);
		System.out.println(p2);
	}

	public SerialCopier(final T object) {
		this.object = object;
	}

	public static <T extends Serializable> T copy(final T input) throws IOException, ClassNotFoundException {
		// Create byte buffer
		final var bos = new ByteArrayOutputStream();
		// write input to byte buffer
		new ObjectOutputStream(bos).writeObject(input);
		// read byte buffer to create the copy
		return (T) new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray())).readObject();
	}

	public static <T extends Serializable> T copyAndCheck(final T input) throws IOException, ClassNotFoundException {
		final var copy = copy(input);
		if(copy == input) {
			throw new IllegalStateException("The generated copy shares the memory address of the input");
		}
		if(!copy.equals(input)) {
			throw new IllegalStateException("The generated copy reported that it isn't equal to the input");
		}
		return copy;
	}

	public static boolean isSimple(final Object obj) {
		return obj.getClass().isPrimitive() || SIMPLE_TYPES.contains(obj.getClass());
	}

	@Override
	public Copy<T> copy() {
		try {
			return new Copy<>(object, copy(object));
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public T self() {
		return object;
	}

}
