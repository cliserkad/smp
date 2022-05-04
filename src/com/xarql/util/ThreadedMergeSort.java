package com.xarql.util;

public class ThreadedMergeSort {

	public static void main(String[] args) {

	}

	public static void mergeSort(int[] array) {
		if (array.length < 2) {
			return;
		}
		int mid = array.length / 2;
		int[] l = new int[mid];
		int[] r = new int[array.length - mid];

		for (int i = 0; i < mid; i++) {
			l[i] = array[i];
		}
		for (int i = mid; i < array.length; i++) {
			r[i - mid] = array[i];
		}
		mergeSort(l);
		mergeSort(r);

		merge(array, l, r, mid, array.length - mid);
	}

	public static void merge(int[] a, int[] l, int[] r, int left, int right) {
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < left && j < right) {
			if (l[i] <= r[j]) {
				a[k++] = l[i++];
			}
			else {
				a[k++] = r[j++];
			}
		}
		while (i < left) {
			a[k++] = l[i++];
		}
		while (j < right) {
			a[k++] = r[j++];
		}
	}

}
