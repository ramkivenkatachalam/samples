package org.ramki.samples;

/**
 * Compute minimum edit distance for two input strings (a and b). Cost of transform/delete and insert 
 * all 1 unit.
 * 
 * This is a dynamic programming solution that computes MED for progressively longer prefixes of a and b
 * until we have the MED for full string a and b. 

 * @author ramki
 *
 */
public class EditDistance {
	public static int computeMinEditDistance(String a, String b) {
		if (a == null && b == null) return 0;
		if (a == null) return b.length();
		if (b == null) return a.length();
		
		final int m = a.length() +1;
		final int n = b.length() + 1;
		int distances[][] = new int[m][n];
		for (int i = 0; i < m; i++) distances[i][0] = i;
		for (int i = 0; i < n; i++) distances[0][i] = i;
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				int min = Math.min(distances[i][j-1]+1, distances[i-1][j]+1);
				min = Math.min(min, distances[i-1][j-1] + 
						((a.charAt(i-1) == b.charAt(j-1))? 0: 1));
				distances[i][j] = min;
			}
		}
		return distances[m-1][n-1];
		
	}
	
	public static void main(String[] args) {
		System.out.println("Edit distance: " +  computeMinEditDistance("helllo", "helLLlo"));
	}

}
