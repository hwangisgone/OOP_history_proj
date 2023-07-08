package Quy.src.dynastydatabase;

import Quy.src.constant.Constant;
import Quy.src.crawler.DynastyWikiCrawler;
import Source.data.database.IDatabase;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import javax.json.Json;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




/*
 Load a list of objects with given index range[startIndex, endIndex)
		- startIndex is inclusive
		- endIndex is exclusive


//public List<E> load(int startIndex, int endIndex);

 Load and return all objects in the database

//public List<E> load();

 return the number of objects in the database

//public int size();

 close the database: cleaning environment if neccessary
*/

//public void close();
// close IDatabase
public class DynastyDB implements IDatabase<JSONObject> {

    DynastyWikiCrawler dynastyCrawler;
    List<JSONObject> listObject;

    //constructor crawling data from web
    public DynastyDB() {
        listObject = new ArrayList<>();
        dynastyCrawler = new DynastyWikiCrawler();
        dynastyCrawler.start();
        for(Object i : dynastyCrawler.getJsonArray())
        {
            listObject.add((JSONObject) i);
        }
        /*store(this.listObject);*/
    }

    /*Store an object into database */
    // Store listObject to database
    @Override
    public void store(List<JSONObject> listObject) {
        JSONArray jsonArray = new JSONArray();
        for (Object i : listObject) {
            jsonArray.add((JSONObject) i);
        }
        try (FileWriter fileWriter = new FileWriter("./testData.json")) {
            fileWriter.write((jsonArray.toJSONString()));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*Load a list of objects with given index range[startIndex, endIndex)
        - startIndex is inclusive
		- endIndex is exclusive*/
    @Override
    public List<JSONObject> load(int startIndex, int endIndex) {
        return null;
    }


    /* Load and return all objects in the database */
    @Override
    public List<JSONObject> load()
    {

    }

    @Override
    public int size() {
        return dynastyCrawler.getCrawledData().size();
    }

    @Override
    public void close() {

    }
}
