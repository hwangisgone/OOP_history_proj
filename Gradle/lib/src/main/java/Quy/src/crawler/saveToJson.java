package Quy.src.crawler;
import java.io.FileWriter;
import java.io.IOException;

import Quy.src.constant.Constant;
import Quy.src.datamodel.FestivalEntity;

public class saveToJson {
    public static void main(String[] args) throws IOException {

        AbstractCrawler data_crawler[] = new AbstractCrawler[2];
        data_crawler[0] = new DynastyAndEventWikiCrawler();
        data_crawler[1] = new FestivalWikiCrawler();

        for(int i=0;i<2;i++)
        {
            try {
                data_crawler[i].start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try (FileWriter fileWriter = new FileWriter(data_crawler[i].getCrawledData().get(0).getDesJsonPath()))
            {
                fileWriter.write(data_crawler[i].getJsonArray().toJSONString());
                fileWriter.flush();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Size of dynasty data= " + data_crawler[0].getCrawledData().size());
        System.out.println("Size of festival data= " + data_crawler[1].getCrawledData().size());

    }
}
