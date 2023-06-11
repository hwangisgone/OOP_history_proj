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
import data.database.IDatabase;
import data.database.JsonDatabase;


public class DataGround {

	// Crawling data activity
	public static void crawl(int no) {
		String databaseFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#"+ no +".json";
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		JsonDatabase database = new JsonDatabase(databaseFile);
		DataControler controler = new DataControler(seacher, extractor, database);
		// Search links
		String urlSeed = "https://vi.wikipedia.org/wiki/H%E1%BB%93_Ch%C3%AD_Minh";
		String fileUrl = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/url/historical-character/url#2";
		// controler.searchLinkIntoFile(urlSeed, 3, 100, fileUrl);
		// Extract data
		controler.extractUrlFile(fileUrl);
	}	// close crawl

	
	public static void main(String[] args) {
		DataGround.crawl(3);
	}	// close main
}	// close DataGround