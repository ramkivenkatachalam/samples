package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * check if there are n elements in an input array "numbers" that add up to zero.
 * @author ramki
 *
 */
public class ZeroSum {

	public static boolean sumTwoZero_secondarySet(int[] numbers) {
		if (numbers == null)
			throw new InvalidParameterException("input array cannot be null");

		Set<Integer> numberMap = new HashSet<Integer>(numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			int current = numbers[i];
			if (numberMap.contains(-current))
				return true;
			numberMap.add(current);
		}
		return false;

	}

	public static boolean sumTwoZero_sort(int[] numbers) {
		Arrays.sort(numbers);
		for (int i = 0; i < numbers.length; i++) {
			int current = numbers[i];
			if (lookup(numbers, 0, numbers.length - 1, -current))
				return true;
		}
		return false;
	}


	public static boolean sumTwoZero_sort2(int[] numbers) {
		Arrays.sort(numbers);
		for (int i = 0, j = numbers.length-1; i < j; ) {
			if (numbers[i] + numbers[j] == 0) return true;
			else if (numbers[i] + numbers[j] < 0) i++;
			else j--;
		}
		return false;
	}

	public static boolean sumThreeZero(int[] numbers) {
		// sort first
		Arrays.sort(numbers);

		// for each pair compute sum - s (n2) (i, j)
		for (int i = 0; i < numbers.length; i++) {
			for (int j = i + 1; j < numbers.length; j++) {
				int sum = numbers[i] + numbers[j];
				if (lookup(numbers, j + 1, numbers.length - 1, -sum))
					return true;
			}
		}
		return false;

	}

	private static boolean lookup(int[] numbers, int start, int end, int val) {
		if (start > end)
			return false;
		int mid = start + (end - start) / 2;
		if (numbers[mid] == val)
			return true;
		else if (numbers[mid] < val) {
			return lookup(numbers, mid + 1, end, val);
		} else {
			return lookup(numbers, start, mid - 1, val);
		}
	}

	public static void main(String[] args) {
		int input[] = {1, 5, 7, 9, 10, -5};
		System.out.println("Output of sumTwoZero_secondarySet " + sumTwoZero_secondarySet(input));
		System.out.println("Output of sumTwoZero_sort " + sumTwoZero_sort(input));
		System.out.println("Output of sumTwoZero_sort2 " + sumTwoZero_sort2(input));
		System.out.println("Output of sumThreeZero " + sumThreeZero(input));
		
		
	}

}
