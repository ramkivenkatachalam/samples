package org.ramki.samples;

import java.security.InvalidParameterException;

public class simpleRegMatch {
	public static boolean match(String input, String pattern) {
		// todo: validate pattern
		if (pattern == null) 
			throw new InvalidParameterException("pattern arg cannot be null");

		if (input == null) 
			throw new InvalidParameterException("input arg cannot be null");
		
		int i = 0;
		int p = 0;
		char c = '.';
		int state =  0;
		boolean getNext = true;
		while (i < input.length()) {
			if (getNext) {
				if (p >= pattern.length()) return false;
				c = pattern.charAt(p); p++;
				state = 0;
				if (p < pattern.length() ) {
					if (pattern.charAt(p) == '*') {
						state = 1; p++;
					}
					else if (pattern.charAt(p) == '+') {
						state = 2;
						p++;
					}
				}
				getNext = false;
			} // getNext
			
			// input does not match
			if (c != '.' && c != input.charAt(i)) {
				if (state == 1) {
					getNext = true;
					continue;
				} else {
					return false;
				}
			}
			
			// input matches 
			i++;
			if (state == 0) {
				// single char, move forward in pattern
				getNext = true;
			} else if (state == 2) {
				// + case, treat it now as *
				state = 1;
			}
		}
		return (p == pattern.length());
		
	}
	
	public static void main(String args[]) {
		System.out.println("Match: " + match("acccdddefg", "acccdddefg"));
		System.out.println("Match: " + match("acccdddefg", "acccdddefgh"));
		System.out.println("Match: " + match("acccdddefg", ".*"));
		System.out.println("Match: " + match("acccdddefg", "ab*c*d+e+.g"));
	}

}
