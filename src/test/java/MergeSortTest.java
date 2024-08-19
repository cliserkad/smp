package test.java;

import org.junit.jupiter.api.Test;
import xyz.cliserkad.util.MergeSort;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

	// sort a bunch of numbers. Should work on other Comparables too
	@Test
	public void testArray() {
		Random r = new Random();
		Integer[] randomized = new Integer[32768];
		for(int i = 0; i < randomized.length; i++) {
			randomized[i] = r.nextInt(randomized.length);
		}
		Integer[] sorted = new Integer[randomized.length];
		System.arraycopy(randomized, 0, sorted, 0, randomized.length);

		Arrays.sort(sorted);
		MergeSort.sort(randomized);

		assertArrayEquals(randomized, sorted);
	}

}
