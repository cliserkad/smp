package test.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static xyz.cliserkad.util.Math.pow;

public class MathTest {

	@Test
	public void test0RaisedTo0Is1() {
		assertEquals(1, pow(0, 0));
	}

	@Test
	public void test0RaisedTo1Is0() {
		assertEquals(0, pow(0, 1));
	}

	@Test
	public void test1RaisedTo0Is1() {
		assertEquals(1, pow(1, 0));
	}

	@Test
	public void test1RaisedTo1Is1() {
		assertEquals(1, pow(1, 1));
	}

	@Test
	public void test1RaisedTo2Is1() {
		assertEquals(1, pow(1, 2));
	}

	@Test
	public void test2RaisedTo0Is1() {
		assertEquals(1, pow(2, 0));
	}

	@Test
	public void test2RaisedTo1Is2() {
		assertEquals(2, pow(2, 1));
	}

	@Test
	public void test2RaisedTo2Is4() {
		assertEquals(4, pow(2, 2));
	}

	@Test
	public void test2RaisedTo3Is8() {
		assertEquals(8, pow(2, 3));
	}

	@Test
	public void test2RaisedTo4Is16() {
		assertEquals(16, pow(2, 4));
	}

	@Test
	public void testLargeExponentDoesntCauseStackOverflow() {
		assertEquals(-2659239065858430293L, pow(3, Integer.MAX_VALUE));
	}

}
