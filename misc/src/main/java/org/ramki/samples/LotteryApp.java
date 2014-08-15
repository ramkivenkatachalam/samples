package org.ramki.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LotteryApp  {
	private static class Lottery implements Comparable<Lottery> {
		public final String name;
		public final int choices;
		public final int blanks;
		public final boolean sorted;
		public final boolean unique;
		private Long numValidCombinations = null;
		
		public Lottery(String name, int choices, int blanks, boolean sorted,
				boolean unique) {
			super();
			this.name = name;
			this.choices = choices;
			this.blanks = blanks;
			this.sorted = sorted;
			this.unique = unique;
		}
		
		private static long scomb(int choices, int blanks, boolean unique) {
			if (choices < 0) return 0;
			if (blanks == 1) return choices;
			long r = 0;
			for (int i = 0; i <= choices; i++) {
				r += scomb(unique? (i-1): i, blanks-1, unique);
			}
			return r;
		}
		
		public long numValidCombinations() {
			if (numValidCombinations != null) return numValidCombinations;
			
			if (!sorted ) {
				if (! unique) {
					numValidCombinations =  (long) Math.pow(choices, blanks);
				} else {
					long r = 1; int c = choices;
					for (int i = 0; i < blanks; i++) {
						r = r*c;
						c--;
					}
					numValidCombinations =  r;
				}
			}
			else {
				numValidCombinations =   scomb(choices, blanks, unique);
			}
			return numValidCombinations;
		}

		public int compareTo(Lottery o) {
			long n1 = this.numValidCombinations();
			long n2 = o.numValidCombinations();
			if (n1 == n2) return 0;
			if (n1 < n2) return -1;
			else return +1;
		}
		
	}


	static String[] sortByOdds(List<Lottery> lotteryList) {
		Collections.sort(lotteryList);
		String output[] = new String[lotteryList.size()];
		for (int i = 0; i < lotteryList.size(); i++) {
			output[i] = lotteryList.get(i).name;
		}
		return output;
	}
	
	public static void main(String args[]) {
		List<Lottery> lotteryList = new ArrayList<Lottery>();
		lotteryList.add(new Lottery("PICK ANY TWO", 10, 2, false, false));
		lotteryList.add(new Lottery("PICK TWO IN ORDER", 10, 2, true, false));
		lotteryList.add(new Lottery("PICK TWO DIFFERENT", 10, 2, false, true));
		lotteryList.add(new Lottery("PICK TWO LIMITED", 10, 2, true, true));
        String result[] = sortByOdds(lotteryList);
        System.out.println("Result: " + Arrays.toString(result));
 		
        lotteryList = new ArrayList<Lottery>();
        lotteryList.add(new Lottery("INDIGO",93,7,true,false));
        lotteryList.add(new Lottery("ORANGE",29,8,false,true));
        result = sortByOdds(lotteryList);
        System.out.println("Result: " + Arrays.toString(result));
        
	}

}
