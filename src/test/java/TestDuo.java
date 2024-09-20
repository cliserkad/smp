package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Duo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDuo {

	@Test
	public void testPrinting() {
		Duo<String, String> duo = new Duo<>("Hello", "World");
		assertEquals(duo.toString(), "{\n a: \"Hello\";\n b: \"World\";\n}");
	}

	@Test
	public void testEquality() {
		Duo<String, String> duo1 = new Duo<>("Hello", "World");
		Duo<String, String> duo2 = new Duo<>("Hello", "World");
		assertEquals(duo1, duo2);
	}

	@Test
	public void testInequality() {
		Duo<String, String> duo1 = new Duo<>("Hello", "World");
		Duo<String, String> duo2 = new Duo<>("Hello", "World!");
		assertEquals(duo1.equals(duo2), false);
	}

	@Test
	public void testHashing() {
		Duo<String, String> duo1 = new Duo<>("Hello", "World");
		Duo<String, String> duo2 = new Duo<>("Hello", "World");
		assertEquals(duo1.hashCode(), duo2.hashCode());
	}

}
