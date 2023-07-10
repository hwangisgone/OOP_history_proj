package Hoang.crawler;

import java.io.File;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import Hoang.CSVHandler;
import Hoang.crawler.nonwiki.DiTichLocationCrawler;
import Hoang.crawler.wiki.DynastyCrawler;
import Hoang.crawler.wiki.FestivalCrawler;
import entity.Dynasty;
import entity.Entity;
import entity.Festival;
import entity.Location;

public class MainCrawler {
	private static final String finalDirectory = "src/main/resources/final/";
	private boolean forceRestart;
	
	public MainCrawler() {
	}
	
	public void setForceRestart(boolean forceRestart) {
		this.forceRestart = forceRestart;
	}
	
	private void createRequiredDir() {
		File finalFileDir = new File(finalDirectory);
        if (!finalFileDir.exists()) {
        	finalFileDir.mkdirs();
        }
        
        if (forceRestart) {
        	for (File f: finalFileDir.listFiles())  f.delete();
        }
	}
	
	private <T> void crawlAndSave(CSVHandler<T> csvHandler, String filename, ICrawler<T> crawler) {
		// Pages
		List<T> resultList = new ArrayList<>();
		
		File fileJson = new File(finalDirectory + filename);
		if (fileJson.exists()) {
			resultList = csvHandler.load(fileJson);
		} else {
			resultList = crawler.crawl();
			csvHandler.write(fileJson, resultList);
		}

		System.out.println("Collected in " + filename + ": " + resultList.size());

//		resultList.forEach(map -> {
//			if (map instanceof Location) {
//				System.out.println(map.toString());
//			} else {
//				System.out.println(map.getClass());
//			}
//		});
	}
	
	public void startCrawl() {
		createRequiredDir();
		HttpClient client = HttpClient.newHttpClient();
		
		CSVHandler<Dynasty> CSVdynasty = new CSVHandler<>(Dynasty.class);
		CSVHandler<Festival> CSVfestival = new CSVHandler<>(Festival.class);
		CSVHandler<Location> CSVlocation = new CSVHandler<>(Location.class);
		
		crawlAndSave(CSVdynasty, "Dynasty.json", new DynastyCrawler(client));
		crawlAndSave(CSVfestival, "Festival.json", new FestivalCrawler(client));
		crawlAndSave(CSVlocation, "Location.json", new DiTichLocationCrawler());
	}
}
