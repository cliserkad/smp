package com.xarql.util;

import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<int[]> {
	public static final int INSERTION_SORT_THRESHOLD = 256;

	int[] array;

	public MergeSort(int[] array) {
		this.array = array;
	}

	public static int[] sort(int[] array) {
		MergeSort sorter = new MergeSort(array);
		return sorter.compute();
	}

	public static void merge(int[] source, int[] leftPart, int[] rightPart) {
		int leftIndex = 0;
		int rightIndex = 0;
		int sourceIndex = 0;
		while(leftIndex < leftPart.length && rightIndex < rightPart.length) {
			if(leftPart[leftIndex] < rightPart[rightIndex]) {
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

	public static int[] insertionSort(int[] array) {
		for(int a = 1; a < array.length; ++a) {
			// key to test targets against
			int key = array[a];
			// index to target for insertion
			int target = a - 1;

            /* Move elements of array[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
			while(target >= 0 && array[target] > key) {
				array[target + 1] = array[target];
				target--;
			}
			array[target + 1] = key;
		}
		return array;
	}

	@Override
	protected int[] compute() {
		// when the array is too small, creating threads isn't worth the overhead
		// this also serves as the base sorting algorithm, instead of a simple 2 element swap
		if(array.length < INSERTION_SORT_THRESHOLD) {
			return insertionSort(array);
		}

		int mid = array.length / 2;

		// numbers to the left of the mid point
		int[] leftPart = new int[mid];
		System.arraycopy(array, 0, leftPart, 0, mid);
		MergeSort leftSorter = new MergeSort(leftPart);

		// numbers to the right of the mid point
		int[] rightPart = new int[array.length - mid];
		System.arraycopy(array, mid, rightPart, 0, array.length - mid);
		MergeSort rightSorter = new MergeSort(rightPart);

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
