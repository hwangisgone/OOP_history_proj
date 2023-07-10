package Quy.src.dynastydatabase;
import Quy.src.constant.Constant;
import Quy.src.crawler.*;
import database.IDatabase;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import javax.json.Json;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class DataController implements IDatabase<JSONObject>
{

    private List<AbstractCrawler> crawlers;
    private List<JSONObject> jsonObjectList;
    public DataController()
    {
        crawlers = new LinkedList<AbstractCrawler>();
        jsonObjectList = new LinkedList<JSONObject>();
    }

    /**
     * Thêm vào bộ thu thập dữ liệu
     */
    public void addCrawlers()
    {
        if(!this.crawlers.isEmpty())
        {
            return;
        }
        else
        {
            this.crawlers.add(new DynastyWikiCrawler());
            this.crawlers.add(new FestivalWikiCrawler());
            this.crawlers.add(new FestivalWiki2Crawler());
            this.crawlers.add(new FestivalWiki3Crawler());
            this.crawlers.add(new FestivalWiki4Crawler());
        }
    }

    /**
     * Khởi động các bộ thu thập dữ liệu luu vao jsonListObject
     */
    public void crawlData()
    {
        addCrawlers();
        for(AbstractCrawler cr: crawlers)
        {
            try
            {
                cr.start();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            for(Object i:cr.getJsonArray())
            {
                jsonObjectList.add((JSONObject) i);
            }
        }


    }


    /* Store list object into database
    * parameter: listObject*/
    @Override
    public void store(List<JSONObject> listObject)
    {
        JSONArray jsonArray = new JSONArray();
        for(JSONObject i: listObject)
        {
            jsonArray.add(i);
        }

        try (FileWriter fileWriter = new FileWriter(Constant.JSON_PATH_DES,false))
        {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataController dataController = new DataController();
        dataController.crawlData();
        dataController.store(dataController.jsonObjectList);
    }

    /* Load a list of objects with given index range[startIndex, endIndex)
		- startIndex is inclusive
		- endIndex is exclusive
	 */

    public List<JSONObject> load(int startIndex, int endIndex) {
        return null;
    }

    /* Load and return all objects in the database */
    @Override
    public List<JSONObject> load() {
        return null;
    }

    @Override
    public List<JSONObject> loadOr(Supplier<List<JSONObject>> getList) {
        return null;
    }

    public int size() {
        return jsonObjectList.size();
    }

    @Override
    public void close() {

    }
}
