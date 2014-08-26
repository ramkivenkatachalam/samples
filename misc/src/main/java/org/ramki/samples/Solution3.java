package org.ramki.samples;

import java.util.Scanner;
import java.io.*;

public class Solution3 {
    public static void main(String args[] ) throws Exception {
	    /* Enter your code here. Read input from STDIN. Print output to STDOUT */
	    /*
    	Scanner sc = new Scanner(System.in);
	    int n = sc.nextInt();
	    sc.skip(",");
	    int m = sc.nextInt();
	    int a[][] = new int[n][m];
	    for (int i = 0; i < n; i ++) {
	    	for (int j =0; i < m; j++) {
	    		a[i][j] = sc.nextInt();
	    	}
	    }
	    */
	    int n = 3;
	    int m = 3;
	    int a[][] = new int[n][m];
	    for (int i = 0; i < n; i ++) {
	    	for (int j =0; j < m; j++) {
	    		a[i][j] = i*m+j+1;
	    	}
	    }
    	
	    
	    int srow = 0, erow = n-1, scol = 0, ecol = m-1;
	    int direction = 0;
	    while (srow <= erow && scol <= ecol) {
	    	switch (direction) {
	    	case 0:
	    		{
		    		int i = srow;
		    		for (int j = scol; j <= ecol; j++) { 
		    			System.out.print(a[i][j] + ",");
		    		}
		    		srow++;
	    		}
	    		break;
	    	case 1:
	    		{
		    		int j = ecol;
		    		for (int i = srow; i <= erow; i++) { 
		    			System.out.print(a[i][j] + ",");
		    		}
		    		ecol--;
	    		}
	    		break;
	    	case 2:
	    		{
		    		int i = erow;
		    		for (int j = ecol; j >= scol; j--) { 
		    			System.out.print(a[i][j] + ",");
		    		}
		    		erow--;
	    		}

	    		break;
	    	case 3:
	    		{
		    		int j = scol;
		    		for (int i = erow; i >= srow; i--) { 
		    			System.out.print(a[i][j] + ",");
		    		}
		    		scol++;
	    		}
	    		break;
	    	}
	    	direction = (direction+1)%4;
	    }
	    System.out.println("");
    
    }
}