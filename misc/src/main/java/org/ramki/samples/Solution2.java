package org.ramki.samples;

import java.io.*;
import java.util.Scanner;

public class Solution2 {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
    	// read N from stdIN
    	Scanner sc = new Scanner(System.in);
    	
    	int N = sc.nextInt();
    	for (int i = 0; i <= N; i++) {
    		if (i % 15 == 0) {
    			System.out.println("FizzBuzz");
    		}
    		else if (i % 3 == 0) {
    			System.out.println("Fizz");
    		}
    		else if (i % 5 == 0) {
    			System.out.println("Buzz");
    		}
    		else {
    			System.out.println("" + i);
    		}
    		
    	}
    	
    	
    }
}