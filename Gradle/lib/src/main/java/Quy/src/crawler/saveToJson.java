package Quy.src.crawler;
import javax.json.JsonValue;
import java.io.FileWriter;
import java.io.IOException;

public class saveToJson {
    public static void main(String[] args) throws IOException {

        AbstractCrawler data_crawler[] = new AbstractCrawler[5];
        data_crawler[0] = new DynastyWikiCrawler();
        data_crawler[1] = new FestivalWikiCrawler();
        data_crawler[2] = new FestivalWiki2Crawler();
        data_crawler[3] = new FestivalWiki3Crawler();
        data_crawler[4] = new FestivalWiki4Crawler();


        for(int i=0;i<5;i++)
        {
            try {
                data_crawler[i].start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try (FileWriter fileWriter = new FileWriter(data_crawler[i].getCrawledData().get(0).getDesJsonPath(),true))
            {
                fileWriter.write(data_crawler[i].getJsonArray().toJSONString());
                fileWriter.flush();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("size of " + data_crawler[i].crawled.size());
        }




    }
}
