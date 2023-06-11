/* 
	DataControler: to implement the data modular:
		(1) Search list of link for specified data -> listUrl
		(2) From listUrl, extract the data and store it into database.json
 */

package data.crawl;

// used classes
import java.util.*;							// Container
import java.io.*;							
import data.crawl.SearchLink;				// self
import data.crawl.ExtractData;
import data.database.IDatabase;
import data.crawl.character.wiki.SearchLinkCharacter;
import data.crawl.character.wiki.ExtractHistoricalCharacter;


public class DataControler {

	private SearchLink seacher;								// seacher for specific data
	private ExtractData extractor;							// extractor for specific data
	private IDatabase<Map<String, String>> database;		// where to store extracted data

	public DataControler(SearchLink seacher, ExtractData extractor, IDatabase<Map<String, String>> database) {
		this.seacher = seacher;
		this.extractor = extractor;
		this.database = database;
	}	// close constructor


	/*	- write list url into given file
	 	- parameters
	 		(0) listUrl: list of urls
	 		(1) filePath: path of writing file */
	private void writeListUrl(List<String> listUrl, String filePath) {
		try {
			FileWriter writer = new FileWriter(filePath);
			for (String url: listUrl) 
				writer.write(url + "\n");
			writer.close();
			System.out.println("Successfully wrote " + listUrl.size() + " urls into file: " + filePath);
		} catch (IOException e) {
			System.out.println("Unable to write list url to file " + filePath);
			System.out.println(e.getMessage());
		}	// close try
	}	// close writeListUrl


	/*	- read list url from given file
		- parameters
			(1) filePath: file to read from */
	private List<String> readListUrl(String filePath) {
		List<String> listUrl = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		try {
			FileReader reader = new FileReader(filePath);
			char[] charArray = new char[512];
			while (reader.ready()) {
				reader.read(charArray);
				buffer.append(charArray);
			}	// close while
			reader.close();
			String arrayUrl[] = buffer.toString().split("\n");
			for (String url: arrayUrl) 
				listUrl.add(url.trim());
			System.out.println("Successfully read listUrl from file: " + filePath);
		} catch (IOException e) {
			System.out.println("Unable to read list url from file: " + filePath);
			System.out.println(e.getMessage());
		}	// close try
		return listUrl;
	}	// close readListUrl


	/*
	 	To searck all links given url seed, max level, max number
	 	Store the links into given file
	 */
	public void searchLinkIntoFile(String urlSeed, int level, int size, String fileUrl) {
		List<String> listUrl = seacher.getListUrl(urlSeed, level, size);
		writeListUrl(listUrl, fileUrl);
	}	// close searchLinkIntoFile


	/*
		Given a file contains list of URLs, read list url and extract data from the corresponding website
			Then store data into database-file
	 */
	public void extractUrlFile(String fileUrl) {
		List<String> listUrl = readListUrl(fileUrl);
		this.database.store(extractor.extract(listUrl));
		this.database.close();
		System.out.println("Successfully extract " + this.database.size() + " objects!");
	}	// close extractUrlFile

}	// close DataControler