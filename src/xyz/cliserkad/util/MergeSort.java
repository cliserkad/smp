package xyz.cliserkad.util;

import test.java.MergeSortTest;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/** Threaded Merge Sort */
public class MergeSort<Sortable extends Comparable<? super Sortable>> extends RecursiveTask<Sortable[]> {

	/** the number of cores available to the JVM. Informs the default number of threads */
	public static final int AMT_CORES = Runtime.getRuntime().availableProcessors();

	/** prevent a potential stack overflow by limiting the number of threads. */
	public static final int MAX_THREADS_MAX = 1024;

	private final Sortable[] array;
	public final int depth;
	public final int maxThreads;

	public static void main(String[] args) {
		new MergeSortTest().testArray();
	}

	public MergeSort(Sortable[] array) {
		this(array, 0, AMT_CORES);
	}

	public MergeSort(Sortable[] array, final int maxThreads) {
		this(array, 0, maxThreads);
	}

	private MergeSort(Sortable[] array, final int depth, final int maxThreads) {
		this.array = array;
		this.depth = depth;
		this.maxThreads = Math.min(maxThreads, MAX_THREADS_MAX);
	}

	/**
	 * Sorts an array of Comparable using a Threaded Merge Sort
	 *
	 * @param array The array to sort
	 * @return The sorted array
	 */
	public static <LocalSortable extends Comparable<? super LocalSortable>> LocalSortable[] sort(LocalSortable[] array) {
		return new MergeSort<>(array).compute();
	}

	/**
	 * Sorts an array of Comparable using a Threaded Merge Sort
	 *
	 * @param array      The array to sort
	 * @param maxThreads The maximum number of threads to use
	 * @return The sorted array
	 */
	public static <LocalSortable extends Comparable<? super LocalSortable>> LocalSortable[] sort(LocalSortable[] array, final int maxThreads) {
		return new MergeSort<>(array, maxThreads).compute();
	}

	private static <LocalSortable extends Comparable<? super LocalSortable>> void merge(LocalSortable[] source, LocalSortable[] leftPart, LocalSortable[] rightPart) {
		int leftIndex = 0;
		int rightIndex = 0;
		int sourceIndex = 0;
		while(leftIndex < leftPart.length && rightIndex < rightPart.length) {
			if(leftPart[leftIndex].compareTo(rightPart[rightIndex]) <= 0) {
				source[sourceIndex++] = leftPart[leftIndex++];
			} else {
				source[sourceIndex++] = rightPart[rightIndex++];
			}
		}
		while(leftIndex < leftPart.length) {
			source[sourceIndex++] = leftPart[leftIndex++];
		}
		while(rightIndex < rightPart.length) {
			source[sourceIndex++] = rightPart[rightIndex++];
		}
	}

	/**
	 * Sorts an array of Comparable using an Insertion Sort
	 *
	 * @param array The array to sort
	 * @return The sorted array
	 */
	public static <LocalSortable extends Comparable<? super LocalSortable>> LocalSortable[] insertionSort(LocalSortable[] array) {
		for(int keyIndex = 1; keyIndex < array.length; ++keyIndex) {
			// key to test targets against
			LocalSortable key = array[keyIndex];
			// index to target for insertion
			int targetIndex = keyIndex - 1;

            /* Move elements of array[0..keyIndex-1], that are
               greater than key, to one position ahead
               of their current position */
			/* unchecked cast here, I'm purposefully letting this error out if the elements are incompatible */
			while(targetIndex >= 0 && array[targetIndex].compareTo(key) > 0) {
				array[targetIndex + 1] = array[targetIndex];
				targetIndex--;
			}
			array[targetIndex + 1] = key;
		}
		return array;
	}

	@Override
	protected Sortable[] compute() {
		// divide the array by 2 until we maximize cpu usage
		if(Math.pow(2, depth + 1) > maxThreads) {
			Arrays.sort(array);
			return array;
		}

		int mid = array.length / 2;

		// numbers to the left of the mid point
		@SuppressWarnings("unchecked") // Object[] will contain only nulls and be filled with Sortables from this.array
		Sortable[] leftPart = (Sortable[]) new Comparable[mid];
		System.arraycopy(array, 0, leftPart, 0, mid);
		MergeSort<Sortable> leftSorter = new MergeSort<>(leftPart, depth + 1, maxThreads);

		// numbers to the right of the mid point
		@SuppressWarnings("unchecked") // Object[] will contain only nulls and be filled with Sortables from this.array
		Sortable[] rightPart = (Sortable[]) new Comparable[array.length - mid];
		System.arraycopy(array, mid, rightPart, 0, array.length - mid);
		MergeSort<Sortable> rightSorter = new MergeSort<>(rightPart, depth + 1, maxThreads);

		// dispatch threads
		leftSorter.fork();
		rightSorter.fork();

		// wait for thread completion
		leftPart = leftSorter.join();
		rightPart = rightSorter.join();

		merge(array, leftPart, rightPart);

		return array;
	}

}
