
package service.search;

import service.search.ISearchFieldGetter;
import data.entity.Entity;


public class SearchIdGetter implements ISearchFieldGetter {
	// return the lower case of id of the entity
	@Override
	public String getSearchField(Entity entity) {
		String id = entity.getProperty("id").toLowerCase();
		return id;
	}	// close getSearchField
}	