package org.ramki.samples;

/**
 * Given a string like ATCGTVCT, find the length of the longest sub sequence of 
 * characters that is a palindrome.
 * For the example above. the longest sub-sequence is TCGCT and the answer is therefore 5
 *
 * @author ramki
 *
 */
public class MaxPalindromeSubSequence {

	public static int maxPSS(String input) {
		final int len = input.length();
		int maxLengths[][] = new int[len][len];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				maxLengths[i][j] = -1;
			}
		}
		return maxPSSInternal(input, 0, input.length()-1, maxLengths);
	}
	
	private static int maxPSSInternal(String input, int start, int end, int maxLengths[][]) {
		if (start > end) return 0;
		if (start == end) return 1;
		if (maxLengths[start][end] != -1) return maxLengths[start][end];
		int retVal;
		if (input.charAt(start) == input.charAt(end)) 
			retVal =  2 + maxPSSInternal(input, start+1, end-1, maxLengths);
		else {
			int m1 = maxPSSInternal(input, start+1, end, maxLengths);
			int m2 = maxPSSInternal(input, start, end-1, maxLengths);
			retVal = Math.max(m1, m2);
		}
		maxLengths[start][end] = retVal;
		return retVal;
	}
	
	public static void main(String[] args) {
		System.out.println("MAX PSS :" + maxPSS("ATXCABAACT"));
		System.out.println("MAX PSS :" + maxPSS("ZZZZZZZZZ"));
		System.out.println("MAX PSS :" + maxPSS("ABCDEFGH"));

		
	}

}
