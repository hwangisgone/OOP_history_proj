/*
	This class is used to represent a Historical Character
 */
package data.entity;

import java.util.jar.Attributes;
import java.util.Map;


public class HistoricalCharacter {

	private 	Attributes map;		// map string with string


	public HistoricalCharacter() {
		map = new Attributes();
	}	// close constructor


	// Set new value for the given attribute(key) in map
	public void setProperty(String key, String value) {
		map.putValue(key, value);
	}	// close setProperties


	// Get a value of given attribute(key) in map
	public String getProperty(String key) {
		return map.getValue(key);
	}	// close getProperty


	// return info of the HistoricalCharacter
	public String info() {
		String info = "";
		for (Map.Entry entry: map.entrySet()) {
			info += entry.getKey() + " : ";
			info += entry.getValue() + "\n";
		}	// close for
		return info;
	}	// close info
}	// close HistoricalCharacter