package org.ramki.samples;

import java.util.List;
import java.util.Arrays;

public class Tree {
	public Tree() {
		
	}
	public List<Integer> methodArgs(Integer... integers) {
		System.out.println(integers.length);
		return Arrays.asList(integers);
	}
	
	public void method() {
		
	}

}
