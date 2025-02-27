package xyz.cliserkad.util;

import test.java.MergeSortTest;

import java.util.concurrent.RecursiveTask;

/** Threaded Merge Sort */
public class MergeSort<Sortable extends Comparable<? super Sortable>> extends RecursiveTask<Sortable[]> {

	public static final int INSERTION_SORT_THRESHOLD = 256;

	private final Sortable[] array;

	public static void main(String[] args) {
		new MergeSortTest().testArray();
	}

	public MergeSort(Sortable[] array) {
		this.array = array;
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
		// when the array is too small, creating threads isn't worth the overhead
		// this also serves as the base sorting algorithm, instead of a simple 2 element swap
		if(array.length < INSERTION_SORT_THRESHOLD) {
			return insertionSort(array);
		}

		int mid = array.length / 2;

		// numbers to the left of the mid point
		@SuppressWarnings("unchecked") // Object[] will contain only nulls and be filled with Sortables from this.array
		Sortable[] leftPart = (Sortable[]) new Comparable[mid];
		System.arraycopy(array, 0, leftPart, 0, mid);
		MergeSort<Sortable> leftSorter = new MergeSort<>(leftPart);

		// numbers to the right of the mid point
		@SuppressWarnings("unchecked") // Object[] will contain only nulls and be filled with Sortables from this.array
		Sortable[] rightPart = (Sortable[]) new Comparable[array.length - mid];
		System.arraycopy(array, mid, rightPart, 0, array.length - mid);
		MergeSort<Sortable> rightSorter = new MergeSort<>(rightPart);

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
