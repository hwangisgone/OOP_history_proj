/*
 *	- SearchLink: is an abstract class used to provide a general tool to find all url which are about specific data
 	- In order to find url of specific data, it's necessary to implement 3 abstract methods: `checkCommonUrl`, `checkSpecifiedUrl` and `validate` 
 	They are made to check and validate urls for specific data
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
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
	// containers
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;



public abstract class SearchLink {

	protected static final String UNICODE = "UTF-8";		// unicode of url page

	static final int CAPACITY = 1000;			// initial capacity of hash map


	/* Used to filter an url for specific webpage 
		NOTE: return false even when unable to read url */
	public abstract boolean checkCommonUrl(String url);


	/* Used to filter an url for specific data class 
		NOTE: return false even when unable to read url */
	public abstract boolean checkSpecifiedUrl(String url);


	/* * To find list of urls given urlSeed, only search links, 
		which are available on the urlSeed page 
		* Parameter:
			- urlSeed: an url where the searching start off */
	public final List<String> getListUrl(String urlSeed) {
		List<String> listUrl = null;
		HashMap<String, String> hashListUrl = null;
		// SEARCHING level 1
		try {
			// read html document
			Document doc = Jsoup.parse(new URL(urlSeed).openStream(), UNICODE, urlSeed);
			// get all links in the current page which have title
			Elements listLink = doc.select("a[href]").select("a[title]");
			// create a hashMap of urls with initial capacity is number of links found
			hashListUrl = new HashMap<String, String>(listLink.size());
			// validate each link of listUrl found
			for (Element link: listLink) {
				if (checkCommonUrl(link.attr("abs:href"))) {
					String key = link.attr("abs:href");
					String value = link.attr("title");
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
		if (hashListUrl != null)
			listUrl = new ArrayList<String>(hashListUrl.keySet());
		return listUrl;
	}	// close getListUrl


	/* * To find list of urls given urlSeed, and search all links 
		with breadth-first-search algorimthm
		* Parameter:
			- urlSeed: the url where the searching start off
			- level: the maximum numbers of searching levels from urlSeed(root: level 0)
			- size: the maximum number of urls found */
	public final List<String> getListUrl(String urlSeed, int level, int size) {
		List<String> listUrl = new ArrayList<String>();
		HashMap<String, Integer> hashListUrl = new HashMap<String, Integer>(CAPACITY);
		int index = 0;	// the index of searching url
		int count = 0;	// number of link matched
		// create a queue using linkedList
		LinkedList<Node> queue = new LinkedList<Node>();
		Node root = new Node(urlSeed, 0);
		queue.addLast(root);
		System.out.println("# START SEARCHING: ...");
		while (!queue.isEmpty()) {
			// break if reach maximum number of links found
			if (count == size) 
				break;
			// DEQUEUE
			Node node = queue.pollFirst();
			index += 1; 		// the index of the current link
			System.out.println("Checking URL #" + index + ": " + node.url);
			// PROCESS: add link to HashMap
			if (checkSpecifiedUrl(node.url)) {
				listUrl.add(node.url);
				count += 1;
			}	// close if
			// check if goesNext level
			if (node.level < level) {
				List<String> listUrlPage = getListUrl(node.url);
				if (listUrlPage == null)		// mean unable to open link
					continue;
												// add its children into queue
				for (String url: listUrlPage) {
					if (!hashListUrl.containsKey(url)) {	// no duplicated child will be added
						hashListUrl.put(url, node.level);
						// ENQUEUE
						Node child = new Node(url, node.level + 1);
						queue.addLast(child);
					}	// close if
				}	// close for
			}	// close if
		}	// close while
		System.out.println("# FINISHED SEARCHING.");
		System.out.println("# FOUND: " + listUrl.size());
		return listUrl;
	}	// close getListUrl

}	// close SearchLink


/* A class to represent a node of url tree for searching */
class Node {

	public String url;	// node content
	public int level;		// level of the node

	public Node(String url, int level) {
		this.url = url;
		this.level = level;
	}	// close constructor

}	// close class Node