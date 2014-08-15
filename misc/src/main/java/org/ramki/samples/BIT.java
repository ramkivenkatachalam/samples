package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

enum BIT {
	ZERO('0'),
	ONE('1');
	
	private char digit;
	private static Map<Character, BIT> map;
	static {
		map = new HashMap<Character, BIT>();
		for (BIT d: BIT.values()) {
			map.put(d.toChar(), d);
		}
	}
	
	BIT(char digit) {
		this.digit = digit;
	}
	
	@Override
	public String toString() {
		return ""+ this.digit;
	}

	public char toChar() {
		return this.digit;
	}

	public static BIT fromChar(final char bit) {
		BIT b = map.get(bit);
		if (b == null) {
			throw new InvalidParameterException(bit + " not a valid binary digit");
		}
		return b;
	}

	public int toNumericalValue() {
		return this.digit - '0';
	}
	
};
