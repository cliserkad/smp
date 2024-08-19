package xyz.cliserkad.util;

import java.util.concurrent.RecursiveTask;

/** Threaded Merge Sort */
public class MergeSort<T> extends RecursiveTask<Comparable<T>[]> {

	public static final int INSERTION_SORT_THRESHOLD = 256;

	private final Comparable<T>[] array;

	public MergeSort(final Comparable<T>[] array) {
		this.array = array;
	}

	/**
	 * Sorts an array of Comparable using a Threaded Merge Sort
	 *
	 * @param array The array to sort
	 * @return The sorted array
	 */
	public static <T> Comparable<T>[] sort(Comparable<T>[] array) {
		return new MergeSort<>(array).compute();
	}

	private static <T> void merge(Comparable<T>[] source, Comparable<T>[] leftPart, Comparable<T>[] rightPart) {
		int leftIndex = 0;
		int rightIndex = 0;
		int sourceIndex = 0;
		while(leftIndex < leftPart.length && rightIndex < rightPart.length) {
			if(leftPart[leftIndex].compareTo((T) rightPart[rightIndex]) <= 0) {
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
	public static <T> Comparable<T>[] insertionSort(Comparable<T>[] array) {
		for(int keyIndex = 1; keyIndex < array.length; ++keyIndex) {
			// key to test targets against
			Comparable<T> key = array[keyIndex];
			// index to target for insertion
			int targetIndex = keyIndex - 1;

            /* Move elements of array[0..keyIndex-1], that are
               greater than key, to one position ahead
               of their current position */
			/* unchecked cast here, I'm purposefully letting this error out if the elements are incompatible */
			while(targetIndex >= 0 && array[targetIndex].compareTo((T) key) > 0) {
				array[targetIndex + 1] = array[targetIndex];
				targetIndex--;
			}
			array[targetIndex + 1] = key;
		}
		return array;
	}

	@Override
	protected Comparable<T>[] compute() {
		// when the array is too small, creating threads isn't worth the overhead
		// this also serves as the base sorting algorithm, instead of a simple 2 element swap
		if(array.length < INSERTION_SORT_THRESHOLD) {
			return insertionSort(array);
		}

		int mid = array.length / 2;

		// numbers to the left of the mid point
		Comparable<T>[] leftPart = new Comparable[mid];
		System.arraycopy(array, 0, leftPart, 0, mid);
		MergeSort<T> leftSorter = new MergeSort<>(leftPart);

		// numbers to the right of the mid point
		Comparable<T>[] rightPart = new Comparable[array.length - mid];
		System.arraycopy(array, mid, rightPart, 0, array.length - mid);
		MergeSort<T> rightSorter = new MergeSort<>(rightPart);

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
