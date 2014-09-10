package org.ramki.samples;

/**
 * PROBLEM:
 * Write a function, that take an amount and denomination (array of values) and returns the minimum
 * number of coins that sums to the amount. amount is 0 - 100, denomination is a set of positive integers
 * that always contains 1 (making it possible to provide change for any amount). Set size is less than 10.
 * 
 * SOLUTIONS:
 * 1. Greedy solution (very fast but works only for some denominations like the US currency system)
 * 2. Dynamic programming solution ( non-linear polynomial solution but works for all denominations)
 * 
 * @author ramki
 *
 */
public class ChangeMaker {
	public static int makeChangeGreedy(int amount, int denominations[]) {
		int nCoins = 0;
		for (int i = 0; amount >0 && i < denominations.length; i++) {
			 
			while (amount >= denominations[i]) {
				amount -= denominations[i];
				nCoins++;
			}
		}
		return nCoins;
	}

	public static int makeChangeDP(int amount, int denominations[]) {
		int minCoins[][] = new int[denominations.length][amount+1];
		for(int j = 0; j <= amount; j++) {
			minCoins[0][j] = j;
		}
		for (int i = 1; i < denominations.length; i++) {
			for (int j = 0; j <= amount; j++) {
				if (j < denominations[i])
					minCoins[i][j] = minCoins[i-1][j];
				else {
					minCoins[i][j] = Math.min(minCoins[i-1][j],
							1+minCoins[i][j-denominations[i]]);
										
				}
			}
		}
		return minCoins[denominations.length-1][amount];
	}
	
	public static void main(String[] args) {
		int denominationsUS[] = {25, 10, 5, 1};
		int denominationsMadeup[] = {1, 3, 4};
		
		for (int i = 0; i <= 100; i++) {
			//System.out.println("Min coins for " + i + " : " + makeChangeGreedy(i, denominationsUS));
			System.out.println("Min coins for " + i + " : " + makeChangeDP(i, denominationsMadeup));
		}
	}

}
