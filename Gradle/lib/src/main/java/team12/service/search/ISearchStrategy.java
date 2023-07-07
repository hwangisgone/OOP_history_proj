/*
	An interface performs a particular searching algorithms
 */

package team12.service.search;

import java.util.List;


public interface ISearchStrategy {
	/*
		Given a list of string, and a keyword, the method find all strings that match with the keyword
		and return corresponding list of indices of matching strings
		Parameter:
			1. list strings
			2. keyword
		Return:
			list of indices
	 */
	public List<Integer> search(List<String> listField, String keyword);

}	// close ISearchStrategy

