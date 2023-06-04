/*
	This is a database stored in json file, implements IDatabase interface
 */

package data.database;

import data.database.IDatabase;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class JsonDatabase implements IDatabase {
	private String jsonFilePath;

	// constructor
	public JsonDatabase(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
	}	// constructor


	// build json object from an Map<String, String>
	private JsonObject getJsonObject(Object instance) {
		Map<String, String> map = (Map<String, String>) instance;
		JsonObjectBuilder builder = Json.createObjectBuilder();
		// add each field to builder
		for (Map.Entry<String, String> entry: map.entrySet()) 
			builder.add(entry.getKey(), entry.getValue());
		JsonObject object = builder.build();
		return object;
	}	// close 


	/* Store an object into database */
	@Override
	public void store(List<Object> listObject) {
		// 
		try {
			OutputStream fos = new FileOutputStream(jsonFilePath);
			JsonWriter writer = Json.createWriter(fos);
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			// write all instance in list into file
			for (Object instance: listObject) {
				JsonObject object = getJsonObject(instance);
				arrayBuilder.add(object);
			}	// close for
			writer.write(arrayBuilder.build());
			writer.close();
		} catch (IOException e) {
			System.out.println("Unable write to file: " + jsonFilePath);
			System.out.println(e.getMessage());
		}	// close try
	}	// close store an object


	/* Load and return all objects in the database */
	@Override
	public List<Object> load() {
		List<Object> list = new ArrayList<Object>();
		return list;
	}	// close load

	/* return the number of objects in the database */
	@Override
	public int size() {
		return 0;
	}	// close size

}	// close JsonDatabase