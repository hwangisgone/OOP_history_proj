/*
	Perform searching of SearchStrategy interface by using longest common searching algorithm
 */

package service.search;

import service.search.ISearchStrategy;
import java.util.*;


public class LcsSearchStrategy implements ISearchStrategy {

	/*
		- Used to get the matching degree of 2 string, the hiher number, the higher similarity
		- Comlexity: O(n x m)
		Given 2 strings, return the length of the longest subsequence of 2 strings
		by using dynamic approach
	 */
	public static int lcs(String firstSequence, String secondSequence) {
		int rowN = firstSequence.length() + 1;
		int colN = secondSequence.length() + 1;
		int[][] table = new int[ rowN ][ colN ];
		// initialize base case
		for (int row = 0; row < rowN; row ++ ) 
			table[row][0] = 0;
		for (int col = 0; col < colN; col ++ )
			table[0][col] = 0;
		// update the table
		for (int row = 1; row < rowN; row ++ ) 
			for (int col = 1; col < colN; col ++ ) {
				if (firstSequence.charAt(row - 1) == secondSequence.charAt(col - 1))
					table[row][col] = table[row -1][col - 1] + 1;
				else
					table[row][col] = Math.max(table[row][col - 1], table[row - 1][col]);
			}	// close for
		// return
		return table[rowN - 1][colN - 1];
	}	// close lcs


	@Override
	public List<Integer> search(List<String> listField, String keyword) {
		// get the similarity degree with the keyword of all fields in the list fields
		int similarityDegree[] = new int[listField.size()];
		int max = -1;
		for (int i = 0; i < similarityDegree.length; i ++ ) {
			similarityDegree[i] = LcsSearchStrategy.lcs(keyword, listField.get(i));
			if (max < similarityDegree[i])		// find max degree
				max = similarityDegree[i];
		}	// close for
		// find the list of fields with the highest sililarity
		List<Integer> listIndexMatch = new ArrayList<Integer>();
		for (int i = 0; i < similarityDegree.length; i ++ )		// find max degree
			if (similarityDegree[i] == max)
				listIndexMatch.add(i);
		return listIndexMatch;
	}	// close search
}