package javafx.view.entity;

import java.util.Map;
import java.util.function.Function;

import entity.Location;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;

public abstract class SearchController<T> {
	protected Map<String, Function<T, String>> searchMap;
	protected ObservableList<T> data;
	
	protected abstract void initSearchMap();
	public abstract void refresh();
	
    protected FilteredList<T> getSearchData(String searchText, String searchOption) {	
        // Apply filtering based on the search text and option
        FilteredList<T> filteredList = new FilteredList<>(data);

        filteredList.setPredicate(chara -> {
            Function<T, String> fun = searchMap.get(searchOption);
            
            if (fun != null) {
            	System.out.println("GOT THE FUNC");
            	String originalText = fun.apply(chara);
            	
                if (searchOption.equals("ID")) {
                    return originalText.startsWith(searchText);
                } else {
                    return originalText.toLowerCase().contains(searchText.toLowerCase());
                }
            }

            return false;
        });
        
        return filteredList;
    }
}
