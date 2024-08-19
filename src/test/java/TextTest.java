package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextTest {

	public static void main(String[] args) {
		TextTest tester = new TextTest();
		tester.test_a_z();
		tester.test_a_zInvalid();
		tester.testA_Z();
		tester.testA_ZInvalid();
		tester.testTypable();
		tester.testTypableInvalid();
	}

	@Test
	public void test_a_z() {
		assertTrue(Text.a_z("abcdefghijklmnopqrstuvwxyz"));
	}

	@Test
	public void test_a_zInvalid() {
		assertFalse(Text.a_z("ABC"));
	}

	@Test
	public void testA_Z() {
		assertTrue(Text.A_Z("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
	}

	@Test
	public void testA_ZInvalid() {
		assertFalse(Text.A_Z("abc"));
	}

	@Test
	public void testTypable() {
		assertTrue(Text.isTypable("!1@2#3$4%5^6&7*8(9)0_-+=QqWwEeRrTtYyUuIiOoPp{[}]|\\AaSsDdFfGgHhJjKkLl:;\"'ZzXxCcVvBbNnMm<,>.?/"));
	}

	@Test
	public void testTypableInvalid() {
		assertFalse(Text.isTypable("💩"));
	}

}
