/* 
	DataControler: to implement the data modular:
		(1) Search list of link for specified data -> listUrl
		(2) From listUrl, extract the data and store it into database.json
 */

package data.crawl;

// used classes
import java.util.*;							// Container
import javax.json.*;							
import java.io.*;							
import data.crawl.SearchLink;				// self
import data.crawl.ExtractData;
import data.crawl.character.wiki.SearchLinkCharacter;
import data.crawl.character.wiki.ExtractHistoricalCharacter;


public class DataControler {

	private SearchLink seacher;			// seacher for specific data
	private ExtractData extractor;		// extractor for specific data

	public DataControler(SearchLink seacher, ExtractData extractor) {
		this.seacher = seacher;
		this.extractor = extractor;
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
		- to search urls from list of seeds and stored lists from each seed into corresponding files
		- parameters
			(1) listUrlSeed: list of seeds
			(2) directory: the path of the directory where to stored the list
				*NOTE: THE directory must existed, otherwise no files will be written*
			(3) level: maximum number of level reached by searching
			(4) size: maximum number of urls searched from each seed
	 */
	public void searchMultipleListUrl(List<String> listUrlSeed, String directory, int level, int size) {
		int index = 0;
		System.out.println("# THE PROGRAM IS GOING TO SEARCH FROM " + listUrlSeed.size() + " SEEDs");
		for (String seed: listUrlSeed) {
			System.out.println("# START SEARCHING WITH SEED: " + seed);
			String filePath = directory + "/listUrl#" + index;
			List<String> listUrl = seacher.getListUrl(seed, level, size);
			// add url seed at begin of the list
			listUrl.add(0, seed);		
			// write each list into corresponding file
			writeListUrl(listUrl, filePath);
			index += 1;
		}	// close for
		System.out.println("Finished wrote " + listUrlSeed.size() + " url files in " + directory);
	}	// close searchMultipleListUrl


	/*
		- to join a set of list of urls without dupplicate 
		- parameters:
			(1) directory: where the set of url files stored
			(2) filePath: where the list of url after merging stored
	 */
	public void joinMultipleListUrl(String directory, String filePathDest) {
		File directoryPath = new File(directory);
		String contents[] = directoryPath.list();
		List<String> filePaths = new ArrayList<String>();
		// get list url file
		for (String name: contents) 
			if (name.contains("listUrl#"))
				filePaths.add(directory + "/" + name);
		// read list url from each file
		List<String> listUrl = null;
		HashMap<String, Integer> hashListUrl = new HashMap<String, Integer>(1000);
		int index = 0;
		for (String filePath: filePaths) {
			listUrl = readListUrl(filePath);
			for (String url: listUrl) {
				if (!hashListUrl.containsKey(url)) {
					hashListUrl.put(url, index);
					index += 1;
				}	// close if
			}	// close for 1
		}	// close for
		listUrl = new ArrayList<String>(hashListUrl.keySet());
		writeListUrl(listUrl, filePathDest);
		System.out.println("Successfully join " + filePaths.size() + " url files into " + filePathDest	 + ", containing " + index + " urls");
	}	// close joinMultipleListUrl


	/* 	
		- To provide a readble format of database
		- Detail:
			+ read list of objects in database
			+ the attributes of the objects are stored in attributeFile
			+ the list of objects is rewritten into new file in a readable format
	 */
	public static void reWriteData(String databaseFile, String readableFile, String attributeFile) {
		try {
			// Read data
			InputStream fis = new FileInputStream(databaseFile);
			JsonReader reader = Json.createReader(fis);
			JsonArray listObject = reader.readArray();
			// Read attributes list
			fis = new FileInputStream(attributeFile);
			reader = Json.createReader(fis);
			JsonArray listAttri = reader.readArray();
			// Write each object in the array into readable file
			FileWriter writer = new FileWriter(readableFile);
			for (int i = 0; i < listObject.size(); i ++ ) {
				if (listObject.isNull(i))
					continue;
				String contentObj = "\t----- #" + i + " -----\n";
				JsonObject object = listObject.getJsonObject(i);
				for (int j = 0; j < listAttri.size(); j ++ ) {
					String attri = listAttri.getString(j);
					String value = object.getString(attri, "null");
					contentObj += attri + ": " + value + "\n";
				}	// close for 1
				contentObj += "\t----------\n";
				writer.write(contentObj);
			}	// close for
			writer.flush();
			writer.close();
			System.out.println("SUCCESSFULLY REWRITTEN DATABASE!");
		} catch (IOException e) {
			System.out.println("Unable to rewriten the database into readable format\n");
			System.out.println(e.getMessage());
		}	// close try
	}	// close reWriteData


	/*
		Given a file contains list of URLs, read list url and extract data from the corresponding website
			Then store data into database-file
	 */
	public void extractUrlFile(String fileUrl, String databaseFile) {
		List<String> listUrl = readListUrl(fileUrl);
		extractor.extract(listUrl, databaseFile);
	}	// close extractUrlFile


	/*
	 	To searck all links given url seed, max level, max number
	 	Store the links into given file
	 */
	public void searchLinkIntoFile(String urlSeed, int level, int size, String fileUrl) {
		List<String> listUrl = seacher.getListUrl(urlSeed, level, size);
		writeListUrl(listUrl, fileUrl);
	}	// close searchLinkIntoFile

}	// close DataControler