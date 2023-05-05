/* 
	* ExtractHistoricalCharacter: to extract all historical character data of wikipedia websites
 */

package data.crawl;

// used classes
	// jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
	// net
import java.net.URL;
	// file
import java.io.File;
import java.io.IOException;
	// containers
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
	// custom
import data.crawl.ExtractData;
import data.crawl.SearchLinkCharacter;



public class ExtractHistoricalCharacter extends ExtractData {

	private final String UNICODE = "utf-8";		// unicode of website
	private final int CAPACITY = 10;				// maximum number of fields

	
	/* to extract data given an url, return an Map of field name and string value */
	public Map<String, String> extract(String url) {
		Map<String, String> instance = null;		// instance data in map format
		try {
			instance = new HashMap<String, String>(CAPACITY);
			Document doc = Jsoup.parse(new URL(url).openStream(), UNICODE, url);
			// Get table info
			Element infobox = doc.getElementsByClass("infobox").first();
			// Get attribute #1: name
			Element row = infobox.getElementsByTag("tr").first();
			String name = row.getElementsByTag("th").first().text();
			// Get attribute #2: dateOfBirth
			String dateOfBirth = "";
			Element header = infobox.getElementsMatchingOwnText("Sinh").first();
			if ((header != null) && (header.nextElementSibling() != null)) 
				dateOfBirth = header.nextElementSibling().text();
			// Get attribute #3: dateofDeath
			String dateOfDeath = "";
			header = infobox.getElementsMatchingOwnText("Mất").first();
			if ((header != null) && (header.nextElementSibling() != null)) 
				dateOfDeath = header.nextElementSibling().text();
			// Get attribute #4: biography
			String biography = "";
			String biographyIdList[] = {"Tiểu_sử", "Thân_thế"};
			for (String id: biographyIdList) {
				header = doc.getElementById(id);
				if (header != null) {
					header = header.parent();
					break;
				}	// close if
			}	// close
			while (header != null) {
				header = header.nextElementSibling();
				if (header == null) 
					break;
				if (header.tagName().equals("h2"))
					break;
				biography += header.text() + "\n";
			}	// close while
			// Get attribute #5: Career
			String career = "";
			header = doc.getElementById("Sự_nghiệp");
			if (header != null)
				header = header.parent();
			while (header != null) {
				header = header.nextElementSibling();
				if (header == null) 
					break;
				if (header.tagName().equals("h2"))
					break;
				career += header.text() + "\n";
			}	// close while
			// Put data into map
			instance.put("name", name);
			instance.put("dateOfBirth", dateOfBirth);
			instance.put("dateOfDeath", dateOfDeath);
			instance.put("biography", biography);
			instance.put("career", career);
		} catch (IOException e) {
			System.out.println("Unable to read URL: " + url);
			System.out.println("Error: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("The structure of URL is uable be to extracted, URL: " + url);
			System.out.println("Error: " + e.getMessage());
		}	// close try
		return instance;
	}	// close extract

}	// close ExtractHistoricalCharacter