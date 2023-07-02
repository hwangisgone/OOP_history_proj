/*
	An interface provide the searching field from an entity for searcher
 */

package Source.service.search;

import Source.data.entity.Entity;


public interface ISearchFieldGetter {
	/*
		Get the search field, which is attribute value of the entity
		Example: 
			if we are searching by name, the method will return the name of the entity
	 */
	public String getSearchField(Entity entity);

}	// close ISearchFieldGetter