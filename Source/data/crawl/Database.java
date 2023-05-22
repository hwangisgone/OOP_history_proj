/* 
	The place where all data process activies occur
 */


package data.crawl;

// used classes
import java.util.*;						// Container
import data.crawl.SearchLink;				// self
import data.crawl.ExtractData;
import data.crawl.SearchLinkCharacter;
import data.crawl.ExtractHistoricalCharacter;
import data.crawl.DataControler;



public class Database {
	
	public static void main(String[] args) {
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		DataControler controler = new DataControler(seacher, extractor);
		// parameter
		String arrayUrlSeed[] = {
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Tri%E1%BB%87u",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Ti%E1%BB%81n_L%C3%BD",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Ng%C3%B4",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_%C4%90inh",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Ti%E1%BB%81n_L%C3%AA",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_L%C3%BD",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Tr%E1%BA%A7n",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_H%E1%BB%93",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_H%E1%BA%ADu_L%C3%AA",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_M%E1%BA%A1c",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_T%C3%A2y_S%C6%A1n",
			"https://vi.wikipedia.org/wiki/Nh%C3%A0_Nguy%E1%BB%85n"
		};
		List<String> listUrlSeed = new ArrayList<String>();
		Collections.addAll(listUrlSeed, arrayUrlSeed);
		String directory = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/Url/HistoricalCharacter/Batch#1";
		int level = 3;
		int size = 30;
		controler.searchMultipleListUrl(listUrlSeed, directory, level, size);

	}	// close main

}	// close Database