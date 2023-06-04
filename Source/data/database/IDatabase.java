/*
	An interface for database, which must provides below services:
		1. store: store an object into database (append not overwrite)
		2. load: get the list of all objects in database
		3. size: return the number of objects in the database
 */

package data.database;

import java.util.Map;
import java.util.List;


public interface IDatabase {
	
	/* Store an object into database */
	public void store(List<Object> listObject);

	/* Store a string to database */

	/* Load and return all objects in the database */
	public List<Object> load();

	/* return the number of objects in the database */
	public int size();
}	// close IDatabase