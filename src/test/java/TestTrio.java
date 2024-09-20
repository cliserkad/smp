package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Trio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTrio {

	@Test
	public void testPrinting() {
		Trio<String, String, String> trio = new Trio<>("Hello", "World", "!");
		assertEquals(trio.toString(), "{\n a: \"Hello\";\n b: \"World\";\n c: \"!\";\n}");
	}

	@Test
	public void testEquality() {
		Trio<String, String, String> trio1 = new Trio<>("Hello", "World", "!");
		Trio<String, String, String> trio2 = new Trio<>("Hello", "World", "!");
		assertEquals(trio1, trio2);
	}

	@Test
	public void testInequality() {
		Trio<String, String, String> trio1 = new Trio<>("Hello", "World", "!");
		Trio<String, String, String> trio2 = new Trio<>("Hello", "World", "?");
		assertFalse(trio1.equals(trio2));
	}

	@Test
	public void testHashing() {
		Trio<String, String, String> trio1 = new Trio<>("Hello", "World", "!");
		Trio<String, String, String> trio2 = new Trio<>("Hello", "World", "!");
		assertEquals(trio1.hashCode(), trio2.hashCode());
	}

}
