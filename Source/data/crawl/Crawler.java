/*
	This class is used to provide tools for crawling specific
	data:
		- HistoricalCharacter
 */

package data.crawl;

// Entiry classes
import data.entity.HistoricalCharacter;
// libary for crawling
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Attribute;
// Net class
import java.net.URL;
// File class
import java.io.File;
import java.io.IOException;
// Utils class
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Crawler {

	// my user agent
	private static final String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36";
	private static final String referrer = "http://www.google.com";
	private static final String unicode = "UTF-8";
	

	// This func crawl for a single website for HistoricalCharacter infomation, given the url
	public static HistoricalCharacter crawlHistoricalCharacter(String url) {
		HistoricalCharacter entity = new HistoricalCharacter();
		// Crawl data
		try {
			// get html document
			Document doc = Jsoup.parse(new URL(url).openStream(), unicode, url);
			// get table info
			Element infobox = doc.getElementsByClass("infobox").first();
			// get attribute #1: name
			Element row = infobox.getElementsByTag("tr").first();
			String name = row.getElementsByTag("th").first().text();
			// get attribute #2: dateOfBirth
			String dateOfBirth = "";
			Element header = infobox.getElementsMatchingOwnText("Sinh").first();
			if (header != null)
				dateOfBirth = header.parent().getElementsByTag("td").first().text();
			// get attribute #3: dateofDeath
			String dateOfDeath = "";
			header = infobox.getElementsMatchingOwnText("Mất").first();
			if (header != null) 
				dateOfDeath = header.nextElementSibling().text();
			// get attribute #4: biography
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
			// get attribute #5: Career
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
			// set attributes for HistoricalCharacter entity
			entity.setProperty("url", url);
			entity.setProperty("name", name);
			entity.setProperty("dateOfBirth", dateOfBirth);
			entity.setProperty("dateOfDeath", dateOfDeath);
			entity.setProperty("biography", biography);
			entity.setProperty("career", career);
		} catch (IOException e) {
			System.out.println("Unable to crawl HistoricalCharacter from: " + url);
			System.out.println("Error: " + e.getMessage());
		}	// close try
		// return HistoricalCharacter
		return entity;
	}	// close crawlHistoricalCharacter


	// To check the given website is about HistoricalCharacter
	private static boolean checkHistoricalCharacterSite(String url) {
		// list words that a valid Url should not contain
		String listAvoidWord[] = {
			"index.php"
		};
			// check if link contains any word in listAvoidWord
		for (String word: listAvoidWord) {
			if (url.contains(word))
				return false;
		}	// close for
		// list of words that a valid url must contain
		String listCompulsoryWord[] = {
			"/wiki/"
		};
			// check if the link does not contain any words of compulsory list
		for (String word: listCompulsoryWord) 
			if (!url.contains(word))
				return false;
		// given list of keywords that a HistoricalCharacter site may have
		String keywordList[] = {
								"Thể loại:Nhân vật quân sự Việt Nam",
								"Thể loại:Vương tước Việt Nam",
								"Thể loại:Anh hùng dân tộc Việt Nam",
								"Thể loại:Vua",
								"Thể loại:Sinh",
								"Thể loại:Hoàng hậu",
								"Thể loại:Hoàng thái hậu Việt Nam",
							};
		// get html doc
		try {
			Document doc = Jsoup.parse(new URL(url).openStream(), unicode, url);
			boolean isMatch = false;
			for (String keyword: keywordList) {
				Elements titleList = doc.getElementsByAttributeValueContaining("title", keyword);
				if (!titleList.isEmpty()) { 	// There is a match with a topic
					isMatch = true;
					break; 	
				}	// close if
			}	// close for
			if (isMatch == false)
				return false;
			// check if the website has an infobox
			Elements infoboxs = doc.getElementsByClass("infobox");
			if (infoboxs.isEmpty())
				return false;
			// check if the infobox has this section: Thông tin chung
			Elements section = infoboxs.first().getElementsMatchingOwnText("Thông tin chung");
			if (section.isEmpty())
				return false;
		} catch (IOException e) {
			System.out.println("Unable to read url: " + url);
			System.out.println("Error: " + e.getMessage());
		}	// close try
		return true;
	}	// close checkHistoricalCharacterSite


	// Given a url, Breadth search of the link, return list of urls that 
	// 		are about HistoricalCharacter data
	private static List<String> getListUrlHistoricalCharacter(String urlSeed) {
		HashMap<String, String> hashListUrl = null;
		try {
			// read html document
			Document doc = Jsoup.parse(new URL(urlSeed).openStream(), unicode, urlSeed);
			// print all link in the current page
			Elements list = doc.select("a[href]").select("a[title]");
			System.out.println("number of links = " + list.size());
			// create a hashMap of urls with initial capacity is number of links found
			hashListUrl = new HashMap<String, String>(list.size());
			// validate each link of listUrl found
			for (Element ele: list) {
				if (checkHistoricalCharacterSite(ele.attr("abs:href"))) {
					String key = ele.attr("abs:href");
					String value = ele.attr("title");
					// check if hashListUrl already contained above link
					if (!hashListUrl.containsKey(key)) 
						hashListUrl.put(key, value);
				}	// close if
			}	// close for
		} catch (IOException e) {
			System.out.println("Unable to read html from url: " + urlSeed);
			System.out.println("Error: " + e.getMessage());
		}	// close
		// return list of links found
		List<String> listUrl = null;
		if (hashListUrl != null)
			listUrl = new ArrayList<String>(hashListUrl.keySet());
		return listUrl;
	}	// close getListUrlHistoricalCharacter


	// To crawl for list of HistoricalCharacter info given a seed url
	// 		only crawl links available in the urlSeed page
	public static List<HistoricalCharacter> crawlListShallowHistoricalCharacter(String urlSeed) {
		List<HistoricalCharacter> listHistoricalCharacter = new ArrayList<HistoricalCharacter>();
		// search all links in the given urlSeed page, which are about HistoricalCharacter
		List<String> listUrl = getListUrlHistoricalCharacter(urlSeed);
		// crawl HistoricalCharacter data from each link found
		for (String url: listUrl) {
			System.out.println("Crawling url: " + url);
			HistoricalCharacter entity = crawlHistoricalCharacter(url);
			listHistoricalCharacter.add(entity);
		}	// close for
		return listHistoricalCharacter;
	}	// close crawlHistoricalCharacter


	public static void main(String[] args) {
		String urlSeed = "https://vi.wikipedia.org/wiki/Ng%C3%B4_X%C6%B0%C6%A1ng_Ng%E1%BA%ADp"; 
		List<HistoricalCharacter> listHistoricalCharacter = crawlListShallowHistoricalCharacter(urlSeed);
		int count = 0;
		for (HistoricalCharacter entity: listHistoricalCharacter) {
			count += 1;
			String info = "For HistoricalCharacter #" + count + ":\n";
			info += "URL: " + entity.getProperty("url") + "\n";
			info += "Name: " + entity.getProperty("name") + "\n";
			info += "DateOfBirth: " + entity.getProperty("dateOfBirth") + "\n";
			info += "DateOfDeath: " + entity.getProperty("dateOfDeath") + "\n";
			info += "Biography: " + entity.getProperty("biography") + "\n";
			info += "Career: " + entity.getProperty("career") + "\n";
			info += "\t=================\n";
			System.out.println(info);
		}	// close for
	}	// close main
}	// close Crawler