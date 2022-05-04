package test.java;

import java.util.Arrays;

public class Car {

	public final int year;
	public final String model;
	public final Engine engine;
	public final char type;
	public final char[] driveModes;

	public Car() {
		year = 1998;
		model = "Honda Accord";
		engine = new Engine();
		type = 'c';
		driveModes = new char[] { 'p', 'r', 'n', 'd', 'l' };
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj == null) {
			return false;
		} else if(obj == this) {
			return true;
		} else if(obj instanceof final Car other) {
			return other.year == year && other.model.equals(model) && other.engine.equals(engine) && other.type == type && Arrays.equals(other.driveModes, driveModes);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
		builder.append("year:").append(year).append("\n");
		builder.append("model:").append(model).append("\n");
		builder.append("engine:").append(engine).append("\n");
		builder.append("type:").append(type).append("\n");
		builder.append("driveModes:").append(driveModes).append("\n");
		return builder.toString();
	}

}
