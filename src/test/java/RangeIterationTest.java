package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.Range;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple regression testing for using Range in a for each loop
 */
public class RangeIterationTest {

	public static final String EXPECTED_OUTPUT_1 = "0123456789";

	@Test
	public void testRange() {
		Range range = new Range(0, 10);
		StringBuilder sb = new StringBuilder(EXPECTED_OUTPUT_1.length());
		for(int i : range) {
			sb.append(i);
		}
		assertEquals(EXPECTED_OUTPUT_1, sb.toString());
	}

}
