package org.ramki.samples;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers figure out if there are two integers that have a difference of K
 * 
 * @author ramki
 *
 */
public class  KDiff {
    public static void main(String args[] ) throws Exception {
    	int k = 5;
    	// NOTE: assumes array has no duplicates
    	int array[] = {12, 7, 23, 10, 9, 8, 3, 11};
    	Map<Integer, Boolean> map = new HashMap<Integer, Boolean>(array.length);
    	int npairs = 0;
    	for (int i = 0; i < array.length; i++) {
    		int n = array[i];
    		int m1 = n - k;
    		int m2 = n + k;
    		if (map.get(m1) != null) npairs++; 
    		if (map.get(m2) != null) npairs++; 
    		map.put(n, true);	
    	}
    	System.out.println("No of pairs: " + npairs);
    	
    }
}