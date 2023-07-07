/*
	A generic Json database, which implements the IDatabase interface
	Parameters:
		1. JSON file: objects is stored in an JSON array
		2. IDatabase interface generics type is Map<String, String>
 */

package team12.data.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;


public class JsonDatabase implements IDatabase <Map<String, String>> {

	private static List<String> listFileExist;			// list of files have already been opened
	private static List<JsonDatabase> listDatabase;		// list of database exist
	private String jsonFilePath;						// the json file where the database operates on
	private List<Map<String, String>> list;				// the list of objects in database

	// initialize static properties
	static {
		listFileExist = new ArrayList<>();
		listDatabase = new ArrayList<>();
	}


	// constructor
	private JsonDatabase(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
		this.list = new ArrayList<>();
		// pre-load the file
		loadFile();
	}	// constructor


	/* get instance through this method
		to ensure that no more than 1 database on the same json-file at anytime */
	public static JsonDatabase getDatabase(String jsonFilePath) {
		int indexOfFile = listFileExist.indexOf(jsonFilePath);
		if (indexOfFile != -1) {		// file has been opened
			System.out.println("NO CREATE NEW");
			return listDatabase.get(indexOfFile);
		}	// close if
										// file does not exist
		listFileExist.add(jsonFilePath);
		JsonDatabase newDatabase = new JsonDatabase(jsonFilePath);
		listDatabase.add(newDatabase);
		return newDatabase;
	}	// close getDatabase



	// load the JSON array from file and return the list of object
	private void loadFile() {
		try {
			InputStream fis = new FileInputStream(jsonFilePath);

			JsonReader reader = Json.createReader(fis);
			JsonArray array = reader.readArray();
			// convert json array into list of Map<String, String>
			for (JsonValue value: array) {
				JsonObject obj = (JsonObject) value;
				if (obj.isEmpty())			// if obj is empty, remove it
					continue;
				Map<String, String> map = new HashMap<> ();
				for (String key: obj.keySet()) {
					map.put(key, obj.getString(key));
				}	// close for
				this.list.add(map);
			}	// close for
			reader.close();
			fis.close();
		} catch (IOException e) {
			System.out.println("Unable to read the json file: " + jsonFilePath);
			System.out.println("ERROR: " + e.getMessage());
		}	// close try
	}	// close loadFile


	// write each object in list into json file
	private void storeFile() {
		try {
			OutputStream fos = new FileOutputStream(this.jsonFilePath);
			JsonWriter writer = Json.createWriter(fos);
			// convert list into Json array
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			// convert each object in list into JsonObject
			for (Map<String, String> map: this.list) {
				JsonObjectBuilder objBuilder = Json.createObjectBuilder();
				for (Map.Entry<String, String> entry: map.entrySet())
					objBuilder.add(entry.getKey(), entry.getValue());
				arrayBuilder.add(objBuilder);
			}	// close for
			// write array into file
			writer.writeArray(arrayBuilder.build());
			writer.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("Unable to save the change to the database!");
			System.out.println("ERROR: " + e.getMessage());
		}	// close
	}	// close storeFile


	/* Store an object into database */
	@Override
	public void store(List<Map<String, String>> listObject) {
		this.list.addAll(listObject);
	}	// close store

	/* Load a list of objects with given index range
		- changes in the return list does NOT change the database
	 */
	@Override
	public List<Map<String, String>> load(int startIndex, int endIndex) {
		List<Map<String, String>> cloneList = new ArrayList<>(this.list.subList(startIndex, endIndex));
		return cloneList;
	}	// close load

	/* Load and return all objects in the database
		- changes in the return list does NOT change the database
	 */
	@Override
	public List<Map<String, String>> load() {
		List<Map<String, String>> cloneList = new ArrayList<>(this.list);
		return cloneList;
	}	// close load

	/* return the number of objects in the database */
	@Override
	public int size() {
		return this.list.size();
	}	// close size


	/* close the database */
	@Override
	public void close() {
		storeFile();
	}	// close database

}	// close JsonDatabase