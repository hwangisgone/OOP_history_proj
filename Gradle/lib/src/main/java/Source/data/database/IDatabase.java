/*
	- This is a database interface for store/load objects
	The database is a virtual list of objects
	- An interface for database, which must provides below services:
		1. store: store an object into database (append not overwrite)
		2. load: get the list of all objects in database
		3. size: return the number of objects in the database
 */

package Source.data.database;

import java.util.List;


public interface IDatabase <E> {

	/* Store an object into database */
	public void store(List<E> listObject);

	/* Load a list of objects with given index range[startIndex, endIndex)
		- startIndex is inclusive
		- endIndex is exclusive
	 */
	public List<E> load(int startIndex, int endIndex);

	/* Load and return all objects in the database */
	public List<E> load();

	/* return the number of objects in the database */
	public int size();

	/* close the database: cleaning environment if neccessary */
	public void close();
}	// close IDatabase