package com.xarql.util;

public class ThreadedMergeSort {

	public static void main(String[] args) {

	}

	public static void mergeSort(int[] source) {
		// if array length is 2
		if(source.length == 2) {
			// if indexes 1 & 2 are not sorted
			if(source[0] > source[1]) {
				// swap indexes 1 & 2
				int tmp = source[0];
				source[0] = source[1];
				source[1] = tmp;
			}
			return;
		}
		int mid = source.length / 2;

		int[] leftPart = new int[mid];
		System.arraycopy(source, 0, leftPart, 0, mid);
		// only sort if array has more than 1 element
		if(leftPart.length > 1)
			mergeSort(leftPart);

		int[] rightPart = new int[source.length - mid];
		System.arraycopy(source, mid, rightPart, 0, source.length - mid);
		// only sort if array has more than 1 element
		if(rightPart.length > 1)
			mergeSort(rightPart);

		merge(source, leftPart, rightPart, mid, source.length - mid);
	}

	public static void merge(int[] source, int[] leftPart, int[] rightPart, int left, int right) {
		int leftCounter = 0;
		int rightCounter = 0;
		int destinationCounter = 0;
		while(leftCounter < left && rightCounter < right) {
			if(leftPart[leftCounter] <= rightPart[rightCounter]) {
				source[destinationCounter++] = leftPart[leftCounter++];
			} else {
				source[destinationCounter++] = rightPart[rightCounter++];
			}
		}
		while(leftCounter < left) {
			source[destinationCounter++] = leftPart[leftCounter++];
		}
		while(rightCounter < right) {
			source[destinationCounter++] = rightPart[rightCounter++];
		}
	}

}
