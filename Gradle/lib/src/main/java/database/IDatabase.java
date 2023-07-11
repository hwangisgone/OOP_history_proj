/*
	- This is a database interface for store/load objects
	The database is a virtual list of objects
	- An interface for database, which must provides below services:
		1. store: store an object into database (append not overwrite)
		2. load: get the list of all objects in database
		3. size: return the number of objects in the database
 */

package database;

import java.util.List;
import java.util.function.Supplier;


public interface IDatabase <E>{

	/* Store an object into database */
	public void store(List<E> listObject);

	/* Load and return all objects in the database */
	public List<E> load();

	/* Load from other source if database doesn't exist */
	/* Useful for combining crawling and saving code */
	public List<E> loadOr(Supplier<List<E>> getList);

	/* Close the database: cleaning environment if neccessary */
	public void close();
}	// close IDatabase