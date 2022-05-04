package test.java;

import com.xarql.util.ElapseTimer;
import com.xarql.util.MergeSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

	// sort 10 million numbers
	@Test
	public void testArray() {
		Random r = new Random();
		int[] randomized = new int[32767];
		for(int i = 0; i < randomized.length; i++) {
			randomized[i] = r.nextInt(32767);
		}
		int[] sorted = new int[randomized.length];
		System.arraycopy(randomized, 0, sorted, 0, randomized.length);

		Arrays.sort(sorted);
		MergeSort.sort(randomized);

		assertArrayEquals(randomized, sorted);
	}



}
