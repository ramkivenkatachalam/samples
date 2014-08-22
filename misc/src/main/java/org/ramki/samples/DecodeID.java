package org.ramki.samples;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that decodes an encoded ID that is part of URL. The encoded ID is case sensitive. Because
 * of some URL re-writers in the path, the case of letters in the enccode gets changed. So the decode
 * function should try all possible combinations of lower/uper case for letters in the encoded ID to 
 * find a match. You can assume that at most one valid combination exists.
 *
 * Eg:
 * ID: 848662
 * Encoded ID: kljJJ324hjkS_
 * transformed ID: kljjj324hjks_
 * 
 * @author ramki
 *
 */


class DecodeID {
  
  private static long decodeSimple(String input) {
    /** 
     * Stub implementation for testing 
     * code that does a simple decode with no case transformations
     * and returns positive  long or -1 if it does not exist  
     */
	
    if ("kljjj324hjks_".equals(input))
      return 848662;
    else 
      return -1;
  }
  
  private static long decodeInternal(String input, String prefix) {
    //System.out.println("Decode: "+ input + ", " + prefix);
    final int idx = prefix.length();
    if (idx == input.length()) {
      return decodeSimple(prefix);
    }
    char c = input.charAt(idx);
    // for non-letter case no transformation
    //System.out.println("Current char: " + c);
    if (!Character.isLetter(c)) 
      return decodeInternal(input, prefix+c);
    else {
      // for letter case try both upper case and lower case
      long l = decodeInternal(input, prefix+ Character.toUpperCase(c));
      if (l > 0 ) return l;
      l = decodeInternal(input, prefix+Character.toLowerCase(c));
      return l;
    }
    
  }
  
  public static long decode(String input) {
	  if (input == null) throw new InvalidParameterException("input arg to decode() cannot be null");
	  return decodeInternal(input, "");
  }
  
  public static void main(String[] args) {
    long l = decode("KljJJ324hjkS_");
    System.out.println("Result: " + l);
  }
}
