package Quy.src.crawler;
import java.io.FileWriter;
import java.io.IOException;

import Quy.src.constant.Constant;

public class saveToJson {
    public static void main(String[] args) throws IOException {


        DynastyAndEventWikiCrawler data = new DynastyAndEventWikiCrawler();
        data.start();

        try (FileWriter fileWriter = new FileWriter(Constant.JSON_PATH))
        {
            fileWriter.write(data.getJsonArray().toJSONString());
            fileWriter.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println("Size = " + data.getCrawledData().size());
    }
}
