package org.ramki.samples;

import java.util.List;
import java.util.ArrayList;

// 0 1111 0 -> 2332 -> 0 1111 0
public class BinaryCode {
	
    private static BIT[] decodeInternal(Decimal input[], BIT digit) {
    	 int prev = 0;    	
    	 int current = digit.toNumericalValue();
    	 List<BIT> output = new ArrayList<BIT>(input.length);
    	 for (int i = 0; i < input.length; i++ ) {
    		 output.add(BIT.fromChar((current == 0)? '0': '1'));
    		 int next = input[i].toNumericalValue() - current - prev;
    		 if (next != 0 && next != 1) return null;
    		 prev = current;
    		 current = next;
    	}
    	return output.toArray(new BIT[output.size()]);
    }
	
    private static String BITArrayToString(BIT bits[]) {
    	if (bits == null) return "NONE";
    	StringBuffer buf = new StringBuffer();
    	for (int i = 0; i < bits.length; i++) {
    		buf.append(bits[i].toString());
    	}
    	return new String(buf);
    }
    
    private static Decimal[] StringToDecimalArray(String input) {
    	List<Decimal> d = new ArrayList<Decimal>(input.length());
    	for (int i = 0; i < input.length(); i++ ) {
    		d.add(Decimal.fromChar(input.charAt(i)));
    	}
    	return  d.toArray(new Decimal[d.size()]);
    }
    
	public static String[] decode(String input) {
		String result[] = new String[2];
		Decimal[] inputDigits = StringToDecimalArray(input);
		BIT d1[] = decodeInternal(inputDigits, BIT.ZERO);
		BIT d2[] = decodeInternal(inputDigits, BIT.ONE);
		result[0] = BITArrayToString(d1);
		result[1] = BITArrayToString(d2);
		return result;
	}
	
	public static void main(String args[]) {
		String[] o = decode("2332");
		System.out.println("output: " + o[0] + ", " + o[1]);
	}
}
