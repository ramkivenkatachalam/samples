package org.ramki.samples;

import java.util.Arrays;

public class Merge {
	
	public static void merge(int a[], int b[], int size) {
		int fillIndex = size*2 -1;
		int aIndex = size-1;
		int bIndex = size-1;
		
		while (fillIndex >= 0 && aIndex >= 0 && bIndex >= 0) {
			int bVal = b[bIndex]; 
			int aVal = a[aIndex];
			if (aVal >= bVal) {
				b[fillIndex] = aVal;
				aIndex--;
			} else {
				b[fillIndex] = bVal;
				bIndex--;
			}
			fillIndex--;
		}
		while (aIndex >= 0) {
			b[fillIndex] = a[aIndex];
			fillIndex--;
			aIndex--;
		}
		
	}

	public static void main(String args[]) {
		int a[] = {1, 5, 7, 10, 20};
		int b[] = {-9, 10, 45, 55, 55, 0, 0, 0, 0, 0};
		merge(a, b, 5);
		System.out.println("B: " + Arrays.toString(b));
	}
}
