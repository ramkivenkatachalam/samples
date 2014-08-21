package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.PriorityQueue;

public class SortAlgos {
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
	
	public static void quickSort(int input[], int start, int end) {
		System.out.println("Sort: " + Arrays.toString(input) + ", " + start + ", " + end);
		if (start >= end) return;
	    int pivot = split(input, start, end);
	    quickSort(input, start, pivot-1);
	    quickSort(input, pivot+1, end);
	
	}
	
	public static void quickSort(int input[]) {
		quickSort(input, 0, input.length-1);
	}
	
	/**
	 * Sort input array of integers, where each element in the initial array is worst case
	 * k positions away from it final position in sorted array.
	 * 
	 * This implementation is a modified insertion sort. We start finding the smallest and putting 
	 * it in input[0], then the next smallest and so on. One thing to note is that after swapping the smallest
	 * and input[0], input[1, n-1] still has the same property.
	 * @param input
	 * @param k
	 */
	public static void kSort(int input[], int k) {
		for (int i = 0; i < input.length-1; i++) {
			int m = (i+k < input.length)? i+k: input.length-1;
			int minIndex  = i;
			for (int j = i+1 ; j <= m; j++  ) {
				if (input[minIndex] > input[j]) minIndex = j;
			}
			swap(input, minIndex, i);
		}
	}
	/**
	 * Sort input array of integers, where each element in the initial array is worst case
	 * k positions away from it final position in sorted array.
	 * 
	 * This implementation uses a heap of K elements. Does n inserts and n removes where each operation 
	 * is O(k). If k happens to be n-1 this becomes a normal heap sort.
	 * @param input
	 * @param k
	 */
	public static void kSort2(int input[], int k) {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>(k);
		for (int i = 0; i < input.length && i < k+1; i++ ) 
			queue.add(input[i]);
		
		for (int i = 0; i < input.length; i++) {
			input[i] = queue.remove();
			if (i+k+1 < input.length) queue.add(input[i+k+1]);
		}
	}
	public static void main(String args[]) {

/*		int input[] = {-1, 10, 9, 6, -20, 45, 19};
		quickSort(input);
*/		
		int input[] = {2, 1, 4, 3, 5, 7, 6};
		kSort2(input, 1);
		System.out.println("output: " + Arrays.toString(input));
	}

}
