/* 
	The place where all data process activies occur
 */


package data;

// used classes
import java.util.*;						// Container
import data.crawl.SearchLink;				// self
import data.crawl.ExtractData;
import data.crawl.DataControler;
import data.crawl.character.wiki.SearchLinkCharacter;
import data.crawl.character.wiki.ExtractHistoricalCharacter;
import data.database.JsonDatabase;


public class DataGround {

	// Crawling data activity
	public static void crawl() {
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		DataControler controler = new DataControler(seacher, extractor);
		// Search links
		int no = 2;
		String urlSeed = "https://vi.wikipedia.org/wiki/H%E1%BB%93_Ch%C3%AD_Minh";
		String fileUrl = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/url/historical-character/url#" + no;
		controler.searchLinkIntoFile(urlSeed, 3, 100, fileUrl);
		// Extract data
		String databaseFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#" + no + ".json";
		String attributeFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/DataConfigure/attributeHistoricalCharacter.json";
		String readableFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/readable/hc#" + no + ".readable";
		controler.extractUrlFile(fileUrl, databaseFile);
		controler.reWriteData(databaseFile, readableFile, attributeFile);
	}	// close crawl

	
	public static void main(String[] args) {
		String databaseFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#0.json";
		JsonDatabase database = new JsonDatabase(databaseFile);
		List<Map<String, String>> list = database.load();
		for (Map<String, String> map: list) {
			System.out.println(map.get("id"));
		}	// close
		database.close();
	}	// close main
}	// close DataGround