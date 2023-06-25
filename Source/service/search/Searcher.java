/*
	Class provide search service to user (who use the database for retrieve information)
 */

package service.search;

import service.search.ISearchFieldGetter;
import service.search.ISearchStrategy;
import data.entity.Entity;
import java.util.*;


public class Searcher {
	
	private ISearchFieldGetter fieldGetter;
	private ISearchStrategy searchWorker;

	public void setSearchFieldGetter(ISearchFieldGetter newFieldGetter) {
		fieldGetter = newFieldGetter;
	}	// close setFieldeGetter


	public void setSearchStrategy(ISearchStrategy newSearchStrategy) {
		searchWorker = newSearchStrategy;
	}	// close setSearchStrategy


	public List<Entity> search(List<Entity> listEntity, String keyword) {
		// get the list of searching fields from list entity
		List<String> listField = new ArrayList<String>();
		for (Entity entity: listEntity) 
			listField.add(fieldGetter.getSearchField(entity));
		// searching for list of indices of matching strings
		List<Integer> listIndexMatch = searchWorker.search(listField, keyword);
		// get list of matching entities
		List<Entity> listMatchEntity = new ArrayList<Entity>();
		for (Integer i: listIndexMatch)
			listMatchEntity.add(listEntity.get(i));
		return listMatchEntity;
	}	// close search
}	// close Searcher