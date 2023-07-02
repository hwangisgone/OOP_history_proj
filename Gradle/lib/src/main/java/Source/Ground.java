package Source;

/*
	The ground of project: where all actions take place
 */

import Source.data.database.JsonDatabase;
import Source.service.search.*;
import Source.data.entity.Entity;
import Source.data.entity.Character;
import Source.data.DataGround;
import Source.exception.data.NoIdException;
import java.util.*;


public class Ground {

	public static void testSearch(String keyword) {
		// Read database
		JsonDatabase database = JsonDatabase.getDatabase("/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#29-06#0.json");
		// modeling entities
		List<Entity> listEntity = new ArrayList<Entity> ();		// place holder - testing
		List<Map<String, String>> listObject = database.load();
		for (Map<String, String> map: listObject) {
			try {
				listEntity.add(new Character(map));
			} catch (NoIdException e) {
				System.out.println(e.getMessage());
			}	// close try creating entity
		}	// close for
		Searcher searcher = new Searcher();
		// Set up for searcher
		searcher.setSearchFieldGetter(new SearchNameGetter());
		searcher.setSearchStrategy(new LcsSearchStrategy());
		List<Entity> listMatchEntity = searcher.search(listEntity, keyword);
		for (Entity entity: listMatchEntity) {
			System.out.println(entity);
			System.out.println("------------------");
		}	// close for
	}	// close testSearch

	public static void main(String[] args) {
		Ground.testSearch("Nguyễn");
	}	// close main
}