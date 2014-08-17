package org.ramki.samples;

import java.util.Stack;

public class TreeTraversal {
	enum ProcessingState {
		LEFT,
		RIGHT
	}

	static class Node {
		private String label;
		private Node left;
		private Node right;
		public Node(String label) {
			this.label = label;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}

		
		
	}
	
	public static void printPreOrder(Node root) {
		Stack<Node> st = new Stack<Node>();
		st.push(root); 
		while (!st.empty()) {
			Node n = st.pop();
    		System.out.print(n.getLabel() + " ");
			if (n.getRight() != null) st.push(n.getRight());
			if (n.getLeft() != null) st.push(n.getLeft());
			
		}
        System.out.println();
	}
	
	public static void printInOrder(Node root) {
		Stack<Node> st = new Stack<Node>();
        Node n = root;
        while (n != null || !st.empty()) {
        	if (n== null) {
        		n = st.pop(); 
        		System.out.print(n.getLabel() + " ");
                n = n.getRight();
        	} else {
        		st.push(n);
        		n = n.getLeft();
        	}
        }
        System.out.println();
	}
	
	public static void printPostOrder(Node root) {
		class StackElement {
			Node node;
			ProcessingState st;
			
			public StackElement(Node node, ProcessingState st) {
				this.node = node;
				this.st = st;
			}
		}
		
		Stack<StackElement> stack = new Stack<StackElement>();
		Node n = root;
		while (n != null || !stack.empty()) {
			if (n == null) {
				StackElement s = stack.pop();
				if (s.st == ProcessingState.LEFT) { 
					s.st = ProcessingState.RIGHT;
					stack.push(s);
					n = s.node.getRight();
				} else {
					System.out.print(s.node.getLabel() + " ");
				}
			} else {
				stack.push(new StackElement(n, ProcessingState.LEFT));
				n = n.getLeft();
			}
		}
        System.out.println();
		
		
	}
	
	public static void main(String args[]) {
		Node root = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		B.setLeft(new Node("D"));
		B.setRight(new Node("E"));
		C.setLeft(new Node("F"));
		C.setRight(new Node("G"));
		root.setLeft(B);
		root.setRight(C);
		
		//printPreOrder(root);
		//printInOrder(root);
		printPostOrder(root);
	}

}
