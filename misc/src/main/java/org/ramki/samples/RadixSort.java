package org.ramki.samples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RadixSort {
	final static int BASE = 10;
	private static int ndigits() {
		int n = Integer.MAX_VALUE;
		int d =  0;
		while (n > 0) {
			n = n / BASE;
			d++;
		}
		return d;
	}
	private static int getDigit(int n, int pos) {
		n = Math.abs(n);
		for (int i = 0; i < pos; i++) {
			n = n / BASE;
		}
		return n % BASE;
	}
	public static void radixSort(int input[]) {
		final int ndigits = ndigits();
		final int length = input.length;
		Map<Integer, List<Integer>> buckets = new HashMap<Integer, List<Integer>>(length);
		for (int d = 0; d < ndigits; d++ ) {
			for (int n = 0; n < input.length; n++) {
				int digit = getDigit(input[n], d);
				List<Integer> l = buckets.get(digit);
				if (l == null) { 
					l = new LinkedList<Integer>();
					buckets.put(digit, l);
				}
				l.add(input[n]);
				
			}
			// rewrite element in the input array 
			int pos = 0;
			for (int i = 0; i < BASE; i++) {
				List<Integer> l = buckets.get(i);
				if (l == null) continue;
				for (Integer n: l) {
					input[pos++] = n;
				}
			}
			buckets.clear();
		}
	}
	
	public static void main(String[] args) {
		int input[] = { 1, 15, 9, -10, 7, 4 };
		radixSort(input);
		System.out.println("Sorted output: " + Arrays.toString(input));
	}

}
