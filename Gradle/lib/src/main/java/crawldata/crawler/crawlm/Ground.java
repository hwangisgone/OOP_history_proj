package crawldata.crawler.crawlm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
	The ground of project: where all actions take place
 */

import database.CharacterDatabase;
import entity.Character;
import entity.Entity;


public class Ground {

	/*
		In order to merge multiple dataset into single one
	 */
	public static void mergeDataset() {
		// create list of databases
		String listDataset[] = {
			"src/main/resources/character_crawl/historical-character/hc#29-06#0.json",
			"src/main/resources/character_crawl/historical-character/hc#2.json",
			"src/main/resources/character_crawl/historical-character/hc#10-07#500.json"
		};
		// Using hashmap to remove entities with dupplicated ID
		Map<String, Character> mergedMap= new HashMap<> (2000);

		List<Character> uniqueList = new ArrayList<> ();
		// create list of database
		for (String datasetPath: listDataset) {
			CharacterDatabase database = CharacterDatabase.getDatabase(datasetPath);
			List<Character> listChara = database.load();
			// add each Map into hashmap
			for (Character chara: listChara) {
				String id = chara.getID();
				if (id.contains("\""))
					continue;
				if (!mergedMap.containsKey(id)) {		// id is unique
					mergedMap.put(id, chara);
					uniqueList.add(chara);
				}	// close if
			}	// close for
		}	// close for
		// create a single database
		String unifiedDataset = "src/main/resources/character_crawl/historical-character/hc.json";
		CharacterDatabase unionDatabase = CharacterDatabase.getDatabase(unifiedDataset);
		unionDatabase.store(uniqueList);
		unionDatabase.close();
		System.out.println("Successfully merge " + listDataset.length + " datasets");
	}	// close mergeDataset


	/*
		Load the data from given dataset path
		@param 	jsonPath 	where the dataset allocated
	 */
	private static void testLoad(String jsonPath) {
		// Read database
		CharacterDatabase database = CharacterDatabase.getDatabase(jsonPath);

		// Modeling entities
		List<Character> listEntity = database.load();

		// Print the ID of entity
		for (Entity entity: listEntity) {
			System.out.println(entity.getID());
			System.out.println("------------------");
		}	// close for
		System.out.println("Size: " + listEntity.size());
	}	// close testSearch


	public static void main(String[] args) {
		Ground.testLoad("src/main/resources/character_crawl/historical-character/hc.json");
		// Ground.mergeDataset();
	}	// close main
}