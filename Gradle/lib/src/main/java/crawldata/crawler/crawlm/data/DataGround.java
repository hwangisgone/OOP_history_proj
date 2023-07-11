/*
	The place where all data process activies occur
 */


package crawldata.crawler.crawlm.data;

import crawldata.crawler.crawlm.data.crawl.DataControler;
import crawldata.crawler.crawlm.data.crawl.character.wiki.ExtractHistoricalCharacter;
import crawldata.crawler.crawlm.data.crawl.character.wiki.SearchLinkCharacter;
import database.CharacterDatabase;


public class DataGround {

	/* Crawling data activity
		@param 	no 		name of the dataset file
		@param 	urlSeed the link, where to start searching
		@param 	size 	number of the searching links
	 */
	public static void crawl(String no, String urlSeed, int size) {
		String databaseFile = "src/main/resources/character_crawl/historical-character/hc#"+ no +".json";
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		CharacterDatabase database = CharacterDatabase.getDatabase(databaseFile);
		DataControler controler = new DataControler(seacher, extractor, database);
		// Measure running-time
		long startTime = System.nanoTime();
		// Search links
		String fileUrl = "src/main/resources/character_crawl/url/historical-character/url#" + no;
		controler.searchLinkIntoFile(urlSeed, 3, size, fileUrl);
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("\nSEARCH LINK COST: " + (elapsedTime / 1e9) + " secs");
		// Extract data
		startTime = System.nanoTime();
		controler.extractUrlFile(fileUrl);
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("\nEXTRACT DATA COST: " + (elapsedTime / 1e9) + " secs");
	}	// close crawl


	public static void main(String[] args) {
		String urlSeed = "https://vi.wikipedia.org/wiki/Ng%C3%B4_Quy%E1%BB%81n";
		DataGround.crawl("11-07#1", urlSeed, 1);
	}	// close main
}	// close DataGround