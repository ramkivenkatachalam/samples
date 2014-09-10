package org.ramki.samples;

/**
 * Implementation of a search(lookup) algorithm for a Binary Search Tree.
 * 
 * @author ramki
 *
 */
public class BSearch {
	class Node {
		Node left, right;
		int data;
                Node(int newData) {
			left = right = null;
			data = newData;
		}
	}

	private static int isPresent(Node root, int element){
		if (root == null) return 0;
		Node current = root;
		while (current != null) {
			if (current.data == element) return 1;
			if (element < current.data) current = current.left;
			else current = current.right;
		}
		
		return 0;
	}
}
