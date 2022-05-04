package test.java;

import com.xarql.util.ThreadedMergeSort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ThreadedMergeSortTest {

	@Test
	public void testArray() {
		int[] actual = { 5, 1, 6, 2, 3, 4 };
		int[] expected = { 1, 2, 3, 4, 5, 6 };
		ThreadedMergeSort.mergeSort(actual);
		assertArrayEquals(expected, actual);
	}

}
