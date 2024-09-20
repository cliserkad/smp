package xyz.cliserkad.util;

import java.util.Objects;

public class SerialVersionUIDGenerator {

	public static long generateSerialVersionUID(final Class<?> clazz) {
		BestList<Object> differentiators = new BestList<>();
		differentiators.add(clazz.getName());
		differentiators.add(clazz.getFields());
		return Objects.hash(differentiators.toArray());
	}

}
