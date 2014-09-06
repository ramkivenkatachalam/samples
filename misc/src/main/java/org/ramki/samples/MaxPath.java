package org.ramki.samples;

/**
 * PROBLEM:
 * Given a tree where each node has an integer weight (can be negative) find a path that has the 
 * biggest sum of weights. Note that you can have a path of zero length (no nodes) so the max value
 * is never less than zero.
 * 
 * SOLUTION:
 * It is a Dynamic Programming solution, where we compute the solution recursively. We compute solutions
 * for Left and Right sub trees for any node and use the solutions to compute the solution for the node.
 * For each sub tree, you can have a path that is extensible but connecting that to the node and perhaps 
 * the extensible path on the other sub tree. We also compute a max path that is not extensible - these paths 
 * don't end in the root of the sub tree.
 * 
 * @author ramki
 *
 */
public class MaxPath {
	
	public static class Node {
		final private int weight;
		private Node left, right;
		public Node(int weight) {
			super();
			this.weight = weight;
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
		public int getWeight() {
			return weight;
		}
		
		
	}
	
	private static  class PathLengths { 
		  public final int extensible; 
		  public final int nonextensible; 
		  public PathLengths(int extensible, int nonextensible) { 
		    this.extensible = extensible; 
		    this.nonextensible = nonextensible; 
		  } 
	}
	public static int maxPath(Node root) {
		PathLengths p = maxPathInternal(root);
		return Math.max(p.extensible, p.nonextensible);
	}

	private static PathLengths maxPathInternal(Node n) {
		if (n == null) return new PathLengths(0, 0);
		PathLengths pLeft = maxPathInternal(n.getLeft());
		PathLengths pRight = maxPathInternal(n.getRight());
		
		int extensible = Math.max(0, n.getWeight() + Math.max (pLeft.extensible, pRight.extensible));

		int nonextensible = Math.max(
								Math.max(
										Math.max(extensible,  pLeft.nonextensible),
										pRight.nonextensible),
								n.getWeight() + pLeft.extensible + pRight.extensible);
		return new PathLengths(extensible, nonextensible);
	}

	public static void main(String[] args) {
		System.out.println("Max Length for null tree: " + maxPath(null));
		System.out.println("Max Length for null tree: " + maxPath(new Node(10)));
		System.out.println("Max Length for single node tree: " + maxPath(new Node(-10)));
		Node A = new Node(1);
		Node B = new Node(2);
		Node C = new Node(-3);
		Node D = new Node(4);
		Node E = new Node(5);
		Node F = new Node(6);
		Node G = new Node(7);
		A.setLeft(B);
		A.setRight(C);
		B.setLeft(D);
		B.setRight(E);
		C.setLeft(F);
		C.setRight(G);
		System.out.println("Max Length for single node tree: " + maxPath(A));
		
		
		
		
	}

}
