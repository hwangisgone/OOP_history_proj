/*
	* ExtractData is an abstract class, providing tools for extract data from urls.
	It's neccessay to implement the abstract method: `extract`, which extracts specified data given an url
 */

package crawldata.crawlm.data.crawl;
import java.util.ArrayList;
import java.util.List;
// containers
import java.util.Map;



public abstract class ExtractData {


	/* to extract data given an url, return an Map of field name and string value
		return null when unable to extract data */
	public abstract Map<String, String> extract(String url);


	/* 	* to extract data from list of urls and return list of Map<String, String>
		* Parameter
			- listUrl: list of links to be extracted
			- filePath: a json file path, where the extract data will be stored */
	public final List<Map<String, String>> extract(List<String> listUrl) {
		List<Map<String, String>> list = new ArrayList<>();
		for (String url: listUrl) {
			list.add(extract(url));
		}	// close for
		return list;
	}	// close extract
}	// close ExtractData