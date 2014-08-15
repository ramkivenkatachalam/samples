package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

enum Decimal {
	ZERO('0'),
	ONE('1'),
	TWO('2'),
	THREE('3'),
	FOUR('4'),
	FIVE('5'),
	SIX('6'),
	SEVEN('7'),
	EIGHT('8'),
	NINE('9');
	
	private char digit;
	private static Map<Character, Decimal> map;
	static {
		map = new HashMap<Character, Decimal>();
		for (Decimal d: Decimal.values()) {
			map.put(d.toChar(), d);
		}
	}
	
	Decimal(char digit) {
		this.digit = digit;
	}
	
	@Override
	public String toString() {
		return ""+ this.digit;
	}
	
	public char toChar() {
		return this.digit;
	}
	
	public int toNumericalValue() {
		return this.digit - '0';
	}

	public static Decimal fromChar(char decimal) {
		Decimal b = map.get(decimal);
		if (b == null) {
			throw new InvalidParameterException(decimal + " not a valid decimal digit");
		}
		return b;
	}
	
};
