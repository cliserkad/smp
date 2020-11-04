package test.java;

public class Car {
	public final int year;
	public final String model;
	public final Engine engine;
	public final char type;

	public Car() {
		year = 1998;
		model = "Honda Accord";
		engine = new Engine();
		type = 'c';
	}

}
