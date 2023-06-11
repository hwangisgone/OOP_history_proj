/*
	This class is used to represent a Historical Character
 */
package data.entity;

import java.util.Map;
import exception.data.NoIdException;


public class HistoricalCharacter {

	private Map<String, String> properties;		// map string with string
	private String id;							// id of the character

	// Constructor
	public HistoricalCharacter(Map<String, String> map) {
		this.properties = map;
		if (!map.containsKey("id")) 
			throw new NoIdException();
		this.id = map.getValue("id");
	}	// close constructor


	// Get a value of given attribute(key) in map
	public String getProperty(String key) {
		return map.getValue(key);
	}	// close getProperty


	@Override
	public String toString() {
		String info = "";
		for (Map.Entry entry: map.entrySet()) {
			info += entry.getKey() + " : ";
			info += entry.getValue() + "\n";
		}	// close for
		return info;
	}	// close info
}	// close HistoricalCharacter
