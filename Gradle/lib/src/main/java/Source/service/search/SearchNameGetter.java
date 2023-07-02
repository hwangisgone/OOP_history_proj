
package service.search;

import service.search.ISearchFieldGetter;
import data.entity.Entity;


public class SearchNameGetter implements ISearchFieldGetter {
	// return the lower case of id of the entity
	@Override
	public String getSearchField(Entity entity) {
		String name = entity.getProperty("name").toLowerCase();
		return name;
	}	// close getSearchField
}	