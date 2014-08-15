package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class QSort {
	private static void swap(int input[], int index1, int index2) {
		int t = input[index1];
		input[index1] = input[index2];
		input[index2] = t;
	}
	
	public static int split(int input[], int start, int end) {
		if(start > end) throw new InvalidParameterException("start less than end");
		int pivot = input[start]; 
		int i = start+1, j = end;
		while ( i < j ) {
			if (input[i] <= pivot) i++;
			else if (input[j] > pivot) j--;
			else {
				swap(input, i, j);
			}
		}
		swap(input, start, i-1);
		System.out.println("split: " + Arrays.toString(input) +", " + start + ", " + end + ", " + (i-1));
		return i-1;
	}
	
	public static void sort(int input[], int start, int end) {
		System.out.println("Sort: " + Arrays.toString(input) + ", " + start + ", " + end);
		if (start >= end) return;
	    int pivot = split(input, start, end);
	    sort(input, start, pivot-1);
	    sort(input, pivot+1, end);
	
	}
	
	public static void sort(int input[]) {
		sort(input, 0, input.length-1);
	}
	
	public static void main(String args[]) {
		int input[] = {-1, 10, 9, 6, -20, 45, 19};
		sort(input);
		System.out.println("output: " + Arrays.toString(input));
	}

}
