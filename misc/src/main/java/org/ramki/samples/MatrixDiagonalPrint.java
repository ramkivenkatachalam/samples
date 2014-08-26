package org.ramki.samples;

/**
 * prints a n x m matrix diagonally
 * 
 * @author ramki
 *
 */
public class MatrixDiagonalPrint {

	public static void diagPrint(int matrix[][], int n, int m) {
		for(int j = 0; j < m; j++) {
			for (int i = 0; i < n; i++) {
				if (i+j >= m) break;
				System.out.print(matrix[i][i+j] + ", ");
			}
			System.out.println();
		}
		for(int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i+j >= n) break;
				System.out.print(matrix[i+j][j] + ", ");
			}
			System.out.println();
		}

	}
	public static void main(String[] args) {
		final int n = 4;
		final int m = 3;
		
		int matrix[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				matrix[i][j] = i*m+j;
			}
		}
		diagPrint(matrix, n, m);

	}

}
