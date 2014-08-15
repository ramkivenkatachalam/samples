package org.ramki.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String input[] = {"ABCD", "YZX", "XXX", "CDAB", "ACBD", "XYZ"}; 
        List<String> result = process(input);
        System.out.println("Result: " + result);
    }
 // Exmamples of anagrams
 // AABC, ABCA, BCAA
 // Counter example: ABC, BBCA, XYZ

 // Write a fucnction, given an array of strings, returns the subset that contains all anagrams. 
 // Example input: AABC, ABCA, XYZ, BCAA, ABC, ZYX
 // Example output: AABC, ABCA, BCAA, XYZ, ZYX   (filters out ABC, which doesn't match anything)

 static boolean isAnagram(String input1, String input2) {
     if (input1 == null && input2 == null) return true;
     if (input1 == null || input2 == null) return false;
     
     Map<Character, Integer> charCount = new HashMap<Character, Integer>();
     for (int i = 0; i < input1.length(); i++) {
        // increment char counts
        Integer currentCount = charCount.get(input1.charAt(i));
        charCount.put(input1.charAt(i), (currentCount == null)? 1: currentCount+1);
     }
     for (int i = 0; i < input2.length(); i++) {
        // decrement char counts
        Integer currentCount = charCount.get(input2.charAt(i));
        charCount.put(input2.charAt(i), (currentCount == null)? -1: currentCount-1);
     }
     for (Integer counts: charCount.values()) {
         if (counts != 0) return false;
     }
     return true;
     

 }

 static List<String> process(String input[]) {
     List<String> result = new ArrayList<String>();
     Map<String, List<String>> anagramCollection = new HashMap();
     if (input.length == 0) return result;
     List<String> firstAnagram = new ArrayList<String>();
     firstAnagram.add(input[0]);
     anagramCollection.put(input[0], firstAnagram);
     for (int i = 1; i < input.length; i++) {
       boolean isAdded = false;
       // find if the string is an anagram of any of the set so far
       for (String key: anagramCollection.keySet()) {
          if (isAnagram(key, input[i]) ) {
             anagramCollection.get(key).add(input[i]);
             isAdded = true;
             break;
          } 
       } 
       if (!isAdded ) {
             List<String> newSet = new ArrayList<String>();
             newSet.add(input[i]);
             anagramCollection.put(input[i], newSet);
      }
     }
     // Go through the sets and include only ones that have more than one string
     for (List<String> anagrams: anagramCollection.values()) {
         if (anagrams.size() > 1) {
            result.addAll(anagrams);  
         }
     }
     return result;
     

 }}
