package test.java;

import com.xarql.util.ThreadedMergeSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ThreadedMergeSortTest {

	// sort 10 million numbers
	@Test
	public void testArray() {
		Random r = new Random();
		int[] randomized = new int[10000000];
		for(int i = 0; i < randomized.length; i++) {
			randomized[i] = r.nextInt(10000);
		}
		int[] sorted = new int[randomized.length];
		System.arraycopy(randomized, 0, sorted, 0, randomized.length);
		Arrays.sort(sorted);

		ThreadedMergeSort.mergeSort(randomized);
		assertArrayEquals(randomized, sorted);
	}



}
