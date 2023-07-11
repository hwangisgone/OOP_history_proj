/*
	* SearchLinkCharacter: a subclass of SearchLink to find links about Historical character
 */

package crawldata.crawler.crawlm.data.crawl.character.wiki;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
// net
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// file:jason
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

// used classes
	// jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import crawldata.crawler.crawlm.data.crawl.SearchLink;
import database.constants.PathConstants;



public class SearchLinkCharacter extends SearchLink {

	private List<String> listAvoid;			// list of avoid words of URL
	private List<String> listMust;				// list of must-have words of URL
	private List<String> listSpecifiedExist;	// list of words that one of them must exist in Catagory section of specified data
	private List<String> listSpecifiedAll;		// list of words that all of them must exist in Catagory section of specified data
	private final String JSON_FILE;			// file path of constraint sets


	// constructor
	public SearchLinkCharacter() {
		listAvoid = new ArrayList<>();
		listMust = new ArrayList<>();
		listSpecifiedExist = new ArrayList<>();
		listSpecifiedAll = new ArrayList<>();
		JSON_FILE = PathConstants.pathCharConstraintWord;
		readConstraint();
	}	// close constructor


	/* To read keyword sets from json file */
	private void readConstraint() {
		try {
			// read constraint object form json file
			InputStream fis = new FileInputStream(JSON_FILE);
			JsonReader reader = Json.createReader(fis);
			JsonObject jsonObject = reader.readObject();
			reader.close();
			fis.close();
			// read historical character constraint
			JsonArray listWebsite = jsonObject.getJsonArray("HistoricalCharacter");
			for (int i = 0; i < listWebsite.size(); i ++ ) {
				JsonObject web = listWebsite.getJsonObject(i);
				String nameWeb = web.getString("website");
				// get avoid array
				JsonArray array = web.getJsonArray("avoid");
				for (int j = 0; j < array.size(); j ++ )
					listAvoid.add(array.getString(j));
				// get must array
				array = web.getJsonArray("must");
				for (int j = 0; j < array.size(); j ++ )
					listMust.add(array.getString(j));
				// For specific data
				JsonObject specifiedObject = web.getJsonObject("specified");
					// get specifiedExit array
				array = specifiedObject.getJsonArray("exist");
				for (int j = 0; j < array.size(); j ++ )
					listSpecifiedExist.add(array.getString(j));
					// get specifiedAll array: mean the content must contain all of these keywords
				array = specifiedObject.getJsonArray("all");
				for (int j = 0; j < array.size(); j ++ )
					listSpecifiedAll.add(array.getString(j));
			}	// close for
			System.out.println("# SUCCESSFULLY READ CONSTRAINT FILE");
		} catch (IOException e) {
			System.out.println("Unable to read constraints file: " + JSON_FILE);
			System.out.println("ERROR: " + e.getMessage());
			System.exit(-1);
		}	// close try
	}	// close readConstraint


	// Implement abstract methods

	/* Used to filter an url for specific webpage */
	@Override
	public boolean checkCommonUrl(String url) {
		// Check if link contains any word in listAvoidWord
		for (String word: listAvoid) {
			if (url.contains(word))
				return false;
		}	// close for
		// Check if the link does not contain any words of compulsory list
		for (String word: listMust)
			if (!url.contains(word))
				return false;
		return true;
	}	// close checkCommonUrl


	/* Used to filter an url for specific data class */
	@Override
	public boolean checkSpecifiedUrl(String url) {
		// get html doc
		try {
			Document doc = Jsoup.parse(new URL(url).openStream(), UNICODE, url);
			boolean isMatch = false;
			// Check if the page contains any keywords
			for (String keyword: listSpecifiedExist) {
				Elements titleList = doc.getElementById("catlinks").getElementsByAttributeValueContaining("title", keyword);
				if (!titleList.isEmpty()) { 	// There is a match with a topic
					isMatch = true;
					break;
				}	// close if
			}	// close for
			if (!isMatch)
				return false;
			// Check if the website contains the must-have keywords
			for (String keyword: listSpecifiedAll) {
				Elements titleList = doc.getElementById("catlinks").getElementsByAttributeValueContaining("title", keyword);
				if (titleList.isEmpty())
					return false;
			}	// close for
			// Check if the website has an infobox
			Elements infoboxs = doc.getElementsByClass("infobox");
			if (infoboxs.isEmpty())
				return false;
			// Check if the infobox has this section: Thông tin chung/Thông tin cá nhân
			Elements sections = infoboxs.first().getElementsMatchingOwnText("Thông tin");
			if (sections.isEmpty())
				return false;
		} catch (IOException e) {
			System.out.println("Unable to read url: " + url);
			System.out.println("Error: " + e.getMessage());
			return false;
		}	// close try
		return true;
	}	// close checkSpecifiedUrl

}	// close SearchLinkCharacter