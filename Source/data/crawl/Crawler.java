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
import java.util.LinkedList;


public class Crawler {

	// my user agent
	private static final String unicode = "UTF-8";
	// guessing number of links found for initializing hashmap capacity
	private static final int NUM_LINK = 1000;	

	

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
	public static List<HistoricalCharacter> crawlListHistoricalCharacter(String urlSeed) {
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


	// check an url if the url is valid
	public static boolean checkUrl(String url) {
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
		return true;
	}	// close checkUrl


	// to get the list of url available in given url page with some constraints
	public static List<String> getListUrl(String urlSeed) {
		HashMap<String, String> hashListUrl = null;
		try {
			// read html document
			Document doc = Jsoup.parse(new URL(urlSeed).openStream(), unicode, urlSeed);
			// print all link in the current page
			Elements list = doc.select("a[href]").select("a[title]");
			// create a hashMap of urls with initial capacity is number of links found
			hashListUrl = new HashMap<String, String>(list.size());
			// validate each link of listUrl found
			for (Element ele: list) {
				if (checkUrl(ele.attr("abs:href"))) {
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
	}	// close getListUrl


	/* 
		- To find all links that are available in the successors links, using
		breadth-first travel with specified number of level and preventing
		duplicated links
	 */
	public static List<String> getListUrlHistoricalCharacter(String urlSeed, int level) {
		List<String> listUrlHistoricalCharacter = null;
		HashMap<String, Integer> hashListUrl = new HashMap<String, Integer>(NUM_LINK);
		int index = 0;
		// create a queue using linkedList
		LinkedList<Node> queue = new LinkedList<Node>();
		Node root = new Node(urlSeed, 0);
		queue.addLast(root);
		while (!queue.isEmpty()) {
			// DEQUEUE
			Node node = queue.pollFirst();
			index += 1; 		// the index of the current link
			System.out.println("URL #" + index + ": " + node.url);
			// PROCESS: add link to HashMap
			if (checkHistoricalCharacterSite(node.url)) {
				if (!hashListUrl.containsKey(node.url)) {
					hashListUrl.put(node.url, node.level);
					System.out.println("====\nAt level " + node.level + ": " + node.url + "\n====");
				}	// close if 1
			}	// close if
			// check if goesNext level
			if (node.level < level) {
				List<String> listUrl = getListUrl(node.url);
				for (String url: listUrl) {
					// ENQUEUE
					Node child = new Node(url, node.level + 1);
					queue.addLast(child);
				}	// close for
			}	// close if
		}	// close while
		listUrlHistoricalCharacter = new ArrayList<String>(hashListUrl.keySet());
		return listUrlHistoricalCharacter;
	}	// close crawlListHistoricalCharacter


	/* 
		- To find all links that are available in the successors links, using
		breadth-first travel with specified number of level and preventing
		duplicated links
		- Parameter 
			- urlSeed: start link
			- level: maximum level traveling
			- size to provide upper bound of number links will found
	 */
	public static List<String> getListUrlHistoricalCharacter(String urlSeed, int level, int size) {
		List<String> listUrlHistoricalCharacter = null;
		HashMap<String, Integer> hashListUrl = new HashMap<String, Integer>(NUM_LINK);
		int index = 0;
		int count = 0;	// number of links found
		// create a queue using linkedList
		LinkedList<Node> queue = new LinkedList<Node>();
		Node root = new Node(urlSeed, 0);
		queue.addLast(root);
		while (!queue.isEmpty()) {
			if (count == size) {
				System.out.println("Already found enough specified number links: " + size);
				break;
			}	// close if
			// DEQUEUE
			Node node = queue.pollFirst();
			index += 1; 		// the index of the current link
			System.out.println("URL #" + index + ": " + node.url);
			// PROCESS: add link to HashMap
			if (checkHistoricalCharacterSite(node.url)) {
				if (!hashListUrl.containsKey(node.url)) {
					hashListUrl.put(node.url, node.level);
					count += 1;
					System.out.println("====\nAt level " + node.level + ", found: " + node.url + "\n====");
				}	// close if 1
			}	// close if
			// check if goesNext level
			if (node.level < level) {
				List<String> listUrl = getListUrl(node.url);
				for (String url: listUrl) {
					// ENQUEUE
					Node child = new Node(url, node.level + 1);
					queue.addLast(child);
				}	// close for
			}	// close if
		}	// close while
		listUrlHistoricalCharacter = new ArrayList<String>(hashListUrl.keySet());
		return listUrlHistoricalCharacter;
	}	// close crawlListHistoricalCharacter


	/* to crawl list of HistoricalCharacter given a seed links, and the level of searching */
	public static List<HistoricalCharacter> crawlListHistoricalCharacter(String urlSeed, int level) {
		List<HistoricalCharacter> listHistoricalCharacter = new ArrayList<HistoricalCharacter>();
		// search all links in the given urlSeed page, which are about HistoricalCharacter
		List<String> listUrl = getListUrlHistoricalCharacter(urlSeed, level);
		// crawl HistoricalCharacter data from each link found
		for (String url: listUrl) {
			System.out.println("Crawling url: " + url);
			HistoricalCharacter entity = crawlHistoricalCharacter(url);
			listHistoricalCharacter.add(entity);
		}	// close for
		return listHistoricalCharacter;
	}	// close crawlListHistoricalCharacter


	// print list of HistoricalCharacter
	public static void printListHistoricalCharacter(List<HistoricalCharacter> list) {
		int count = 0;
		for (HistoricalCharacter entity: list) {
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
	}	// close printListHistoricalCharacter


	public static void main(String[] args) {
		String urlSeed = "https://vi.wikipedia.org/wiki/Ng%C3%B4_X%C6%B0%C6%A1ng_Ng%E1%BA%ADp"; 
		// List<HistoricalCharacter> listHistoricalCharacter = crawlListHistoricalCharacter(urlSeed);
		// List<String> listUrl = getListUrlHistoricalCharacter(urlSeed, 2, 20);
		// int count = 0;
		// int size = listUrl.size();
		// for (String url: listUrl) {
		// 	System.out.println("URL " + (count + 1) + "/" + size + ": " + url);
		// 	count += 1;
		// }	// close for

		String link = "https://vi.wikipedia.org/wiki/%C4%90%E1%BA%B7c_bi%E1%BB%87t:Thay_%C4%91%E1%BB%95i_li%C3%AAn_quan/B%E1%BA%A3n_m%E1%BA%ABu:Vua_nh%C3%A0_M%E1%BA%A1c";
		System.out.println(checkHistoricalCharacterSite(link));
	}	// close main
}	// close Crawler


// A class with 2 fields: String and Int
//  to support finding links
class Node {
	public String url;	// node content
	public int level;		// level of the node

	public Node(String url, int level) {
		this.url = url;
		this.level = level;
	}	// close constructor
}	// close class Node