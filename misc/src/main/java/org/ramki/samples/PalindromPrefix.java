package org.ramki.samples;

/**
 * Program that takes a string and converts it to a palindrome by adding the smallest length prefix.
 * @author ramki
 *
 */
public class PalindromPrefix {
	private static boolean isPalindome(String input) {
		for (int i = 0, j = input.length()-1; i < j; i++, j--) {
			if (input.charAt(i) != input.charAt(j)) return false;
		}
		return true;
	}
	
	public static String convertToPalindrome(String input) {
		final int len = input.length();
		String prefix = "";
		for (int i = len-1; i >= 0; i--) {
			if (isPalindome(prefix+ input)) return prefix+input;
			else {
				prefix = prefix + input.charAt(i);
			}
				
		}
		assert isPalindome(prefix+input);
		return prefix+input;
	}
	
	
	public static void main(String args[]) {
		System.out.println("output for hello: " + convertToPalindrome("hello"));
		System.out.println("output for bbbb: " + convertToPalindrome("bbbb"));
		
	}

}
