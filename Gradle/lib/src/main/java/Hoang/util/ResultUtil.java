package Hoang.util;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ResultUtil {

	public static List<String> filterString(Collection<String> collection, List<String> wordList, boolean caseSensitive) {	
    	// String[] wordsFilter = {":Thời kỳ", "Nhà", "Triều đại"};
    	
		Pattern pattern;
		if (caseSensitive) {
			pattern = Pattern.compile(String.join("|", wordList));
		} else {
			pattern = Pattern.compile(String.join("|", wordList), Pattern.CASE_INSENSITIVE);
		}

    	List<String> filteredList = collection.stream()
    			.filter(pattern.asPredicate())
    			.collect(Collectors.toList());
    	
    	return filteredList;
    }
}
