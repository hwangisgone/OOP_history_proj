/*
	A generic Json database, which implements the IDatabase interface
	Parameters:
		1. JSON file: objects is stored in an JSON array
		2. IDatabase interface generics type is Map<String, String>
 */

package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import database.constants.PathConstants;
import database.handler.CSVHandler;
import entity.Character;
import entity.Dynasty;


public class CharacterDatabase implements IDatabase <Character> {

	private static List<String> listFileExist;			// list of files have already been opened
	private static List<CharacterDatabase> listDatabase;		// list of database exist
//	private String jsonFilePath;						
	private List<Character> list;						// the list of objects in database
	
	private File fileJson;								// the file where the database operates on
	private CSVHandler<Character> jacksonHandler;
	// initialize static properties
	static {
		listFileExist = new ArrayList<>();
		listDatabase = new ArrayList<>();
	}


	// constructor
	private CharacterDatabase(File fileJson) {
		this.fileJson = fileJson;
		this.list = new ArrayList<>();
		jacksonHandler = new CSVHandler<>(Character.class);
		loadFile();
	}	// constructor


	/* get instance through this method
		to ensure that no more than 1 database on the same json-file at anytime */
	public static CharacterDatabase getDatabase(String jsonFilePath) {
		int indexOfFile = listFileExist.indexOf(jsonFilePath);
		if (indexOfFile != -1) {		// file has been opened
			System.out.println("NO CREATE NEW");
			return listDatabase.get(indexOfFile);
		}	// close if
										// file does not exist
		listFileExist.add(jsonFilePath);
		CharacterDatabase newDatabase = new CharacterDatabase(new File(jsonFilePath));
		listDatabase.add(newDatabase);
		return newDatabase;
	}	// close getDatabase


	/*
		*QUICK-FIX*
		To get final dataset, relative-path: "src/main/resources/final/Character.json"
		@return 	List of Character
	 */
	public static List<Character> getListCharacter() {
		CharacterDatabase database = CharacterDatabase.getDatabase(PathConstants.pathCharacter);
		// read database
		List<Character> listCharacter = database.load();

		return listCharacter;
	}	// close


	// load the JSON array from file and return the list of object
	private void loadFile() {
		if (fileJson.exists()) {
			this.list.addAll(jacksonHandler.load(fileJson));
		} else {
			System.err.println("File not found for: " + fileJson.getName());
		}
	}	// close loadFile


	// write each object in list into json file
	private void storeFile() {
		jacksonHandler.write(fileJson, this.list);
	}	// close storeFile

	/* Store an object into database */
	@Override
	public void store(List<Character> listObject) {
		this.list.addAll(listObject);
	}	// close store


	/* Load and return all objects in the database
		- changes in the return list does NOT change the database
	 */
	@Override
	public List<Character> load() {
		List<Character> cloneList = new ArrayList<>(this.list);
		return cloneList;
	}	// close load


	/* close the database */
	@Override
	public void close() {
		storeFile();
	}	// close database


	/* load or ?*/
	@Override
	public List<Character> loadOr(Supplier<List<Character>> getList) {
		// TO-DO?
		return null;
	}	// close

}	// close CharacterDatabase