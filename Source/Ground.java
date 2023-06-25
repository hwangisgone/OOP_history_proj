/*
	The ground of project: where all actions take place
 */

import data.database.JsonDatabase;
import service.search.*;
import data.entity.Entity;
import data.DataGround;
import java.util.*;


public class Ground {
	public static void main(String[] args) {
		JsonDatabase database = JsonDatabase.getDatabase("/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/historical-character/hc#4.json");
		List<Entity> listEntity = DataGround.load(database);
		Searcher searcher = new Searcher();
		searcher.setSearchFieldGetter(new SearchIdGetter());
		searcher.setSearchStrategy(new LcsSearchStrategy());
		List<Entity> listMatchEntity = searcher.search(listEntity, "nguyen");
		for (Entity entity: listMatchEntity) {
			System.out.println(entity.getProperty("id"));
		}	// close for
	}	// close main
}