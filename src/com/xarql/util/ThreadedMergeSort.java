package com.xarql.util;

public class ThreadedMergeSort {

	public static void main(String[] args) {

	}

	public static void mergeSort(int[] source) {

		int mid = source.length / 2;

		int[] leftPart = new int[mid];
		System.arraycopy(source, 0, leftPart, 0, mid);
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
				mergeSort(leftPart);
			}
		}

		int[] rightPart = new int[source.length - mid];
		System.arraycopy(source, mid, rightPart, 0, source.length - mid);
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
				mergeSort(rightPart);
			}
		}

		merge(source, leftPart, rightPart);
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

}
