package com.xarql.util;

import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<int[]> {
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

	@Override
	protected int[] compute() {
		int mid = array.length / 2;

		int[] leftPart = new int[mid];
		MergeSort leftSorter = null;
		System.arraycopy(array, 0, leftPart, 0, mid);
		// only sort if array has more than 1 element
		if(leftPart.length > 1) {
			// if array length is 2
			if(leftPart.length == 2) {
				// if indexes 1 & 2 are not sorted
				if(leftPart[0] > leftPart[1]) {
					// swap indexes 1 & 2
					int tmp = leftPart[0];
					leftPart[0] = leftPart[1];
					leftPart[1] = tmp;
				}
			} else {
				leftSorter = new MergeSort(leftPart);
			}
		}

		int[] rightPart = new int[array.length - mid];
		MergeSort rightSorter = null;
		System.arraycopy(array, mid, rightPart, 0, array.length - mid);
		// only sort if array has more than 1 element
		if(rightPart.length > 1) {
			// if array length is 2
			if(rightPart.length == 2) {
				// if indexes 1 & 2 are not sorted
				if(rightPart[0] > rightPart[1]) {
					// swap indexes 1 & 2
					int tmp = rightPart[0];
					rightPart[0] = rightPart[1];
					rightPart[1] = tmp;
				}
			} else {
				rightSorter = new MergeSort(rightPart);
			}
		}

		// run threads
		if(leftSorter != null) leftSorter.fork();
		if(rightSorter != null) rightSorter.fork();

		// wait for thread completion
		if(leftSorter != null) leftPart = leftSorter.join();
		if(rightSorter != null) rightPart = rightSorter.join();

		merge(array, leftPart, rightPart);

		return array;
	}
}
