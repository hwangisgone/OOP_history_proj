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
import data.entity.Entity;
import data.entity.Character;
import exception.data.NoIdException;


public class DataGround {

	// Crawling data activity
	public static void crawl(int no) {
		String databaseFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#"+ no +".json";
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		JsonDatabase database = JsonDatabase.getDatabase(databaseFile);
		DataControler controler = new DataControler(seacher, extractor, database);
		// Measure running-time
		long startTime = System.nanoTime();
		// Search links
		String urlSeed = "https://vi.wikipedia.org/wiki/H%E1%BB%93_Ch%C3%AD_Minh";
		String fileUrl = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/url/historical-character/url#" + no;
		controler.searchLinkIntoFile(urlSeed, 3, 100, fileUrl);
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("\nSEARCH LINK COST: " + (elapsedTime / 1e9) + " secs");
		// Extract data
		startTime = System.nanoTime();
		controler.extractUrlFile(fileUrl);
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("\nEXTRACT DATA COST: " + (elapsedTime / 1e9) + " secs");
	}	// close crawl


	/*
		Testing load datq from database
	 */
	public static List<Entity> load(IDatabase<Map<String, String>> database) {
		List<Entity> listEntity = new ArrayList<Entity> ();
		List<Map<String, String>> listObject = database.load();
		for (Map<String, String> obj: listObject) {
			try {
				listEntity.add(new Character(obj));
			} catch (NoIdException e) {
				System.out.println(e.getMessage());
			}	// close NoIdException
		}	// close for
		return listEntity;
	}	// close load

	
	public static void main(String[] args) {
		// JsonDatabase database = JsonDatabase.getDatabase("/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#4.json");
		// DataGround.load(database);
	}	// close main
}	// close DataGround