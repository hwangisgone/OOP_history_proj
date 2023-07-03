package Ngan.Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Ngan.Crawl.ExtractData;
import Source.data.database.IDatabase;

public class Database implements IDatabase<JSONObject> {
    ExtractData extractData = new ExtractData();
    JSONArray jsonArray = new JSONArray();
    List<JSONObject> listObject = new ArrayList<>();

    public Database(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        store(this.listObject);
    }

    /* Store an object into database */
    @Override
	public void store(List<JSONObject> listObject) {
        for (Object i : jsonArray) {
            listObject.add((JSONObject) i);
        }
        try (
            FileWriter fileWriter = new FileWriter("E:/OOP/javaProject/Crawl/extractData.json")) {
            String modifiedJsonString = ExtractData.unescapeUnicode(jsonArray.toString());
            fileWriter.write(modifiedJsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Load a list of objects with given index range[startIndex, endIndex)
     * - startIndex is inclusive
     * - endIndex is exclusive
     */
    @Override
	public List<JSONObject> load(int startIndex, int endIndex) {
        List<JSONObject> cloneList = new ArrayList<>(this.listObject.subList(startIndex, endIndex));
        return cloneList;
    }

    /* Load and return all objects in the database */
    @Override
	public List<JSONObject> load() {
        return this.listObject;
    }

    /* return the number of objects in the database */
    @Override
	public int size() {
        return this.jsonArray.size();
    }

    /* close the database: cleaning environment if neccessary */
    @Override
	public void close() {
    }
}
