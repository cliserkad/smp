package test.java;

public class Engine {

	public final int cylinders;
	public final String name;
	public final float mpg;

	public Engine() {
		cylinders = 6;
		name = "VTEC";
		mpg = 14.834f;
	}

	public String toString() {
		final var builder = new StringBuilder();
		builder.append("\tcylinders:").append(cylinders).append("\n");
		builder.append("\tname:").append(name).append("\n");
		builder.append("\tmpg:").append(mpg).append("\n");
		return builder.toString();
	}

}
