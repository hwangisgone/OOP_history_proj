package util;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ExtraStringUtil {

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
	
	public static String addComma(List<String> strings) {
		return String.join(", ", strings);
	}
	
	public static void appendNotNull(StringBuilder sb, String str, String val) {
		if (val != null) {
			sb.append(str).append(val);
		}
	}

	public static void appendNotNull(StringBuilder sb, String str, List<String> val) {
		if (val != null) {
			sb.append(str).append(addComma(val));
		}
	}
	
	public static void appendNotNull(StringBuilder sb, String str, List<String> val, String delimiter) {
		if (val != null) {
			sb.append(str).append(String.join(delimiter, val));
		}
	}

}
