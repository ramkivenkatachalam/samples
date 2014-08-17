package org.ramki.samples;

import java.util.Stack;

public class TreeTraversal {
	/* Keeps track of what we have done so far with the Node */
	enum ProcessingState {
		NEW,   // first time we are looking at the node
		LDONE, // we have already processed the left sub tree
		RDONE  // we have already processed the right sub tree
	}

	/**
	 * Nodes in the Tree 
	 *
	 */
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

	/**
	 * Genereric function that prints Tree rooted at root in pre, in or post order
	 * @param root
	 * @param st (NEW: pre order, LDONE: in order, RDONE: post order)
	 */
	public static void printTree(Node root, ProcessingState st) {
		/*
		 * we maintain nodes in the stack along with their processing state
		 */
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
			if (n != null ) {
				// seeing a new node push it with NEW state to stack
				stack.push(new StackElement(n, ProcessingState.NEW));
				n = null;
				continue;
			}

			StackElement s = stack.pop();
			if (s.st == st) System.out.print(s.node.getLabel() + " ");
			switch (s.st) { 
				case NEW:
					s.st = ProcessingState.LDONE;
					stack.push(s);
					n = s.node.getLeft();
					break;
				case LDONE:
					s.st = ProcessingState.RDONE;
					stack.push(s);
					n = s.node.getRight();
					break;
				default:
					
			}
		}
        System.out.println();
	}

	public static void printPreOrder(Node root) {
		Stack<Node> stack = new Stack<Node>();
		stack.push(root); 
		while (!stack.empty()) {
			Node n = stack.pop();
    		System.out.print(n.getLabel() + " ");
			if (n.getRight() != null) stack.push(n.getRight());
			if (n.getLeft() != null) stack.push(n.getLeft());
			
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
				if (s.st == ProcessingState.LDONE) { 
					s.st = ProcessingState.RDONE;
					stack.push(s);
					n = s.node.getRight();
				} else {
					System.out.print(s.node.getLabel() + " ");
				}
			} else {
				stack.push(new StackElement(n, ProcessingState.LDONE));
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
		
		printPreOrder(root);
		printInOrder(root);
		printPostOrder(root);
		printTree(root, ProcessingState.NEW);
		printTree(root, ProcessingState.LDONE);
		printTree(root, ProcessingState.RDONE);
	}

}
