package Hoang.crawler;

import java.io.File;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import Hoang.CSVHandler;
import Hoang.crawler.nonwiki.DiTichLocationCrawler;
import Hoang.crawler.wiki.DynastyCrawler;
import Hoang.crawler.wiki.FestivalCrawler;
import database.PathConstants;
import entity.Dynasty;
import entity.Entity;
import entity.Festival;
import entity.Location;

public class MainCrawler {
	public MainCrawler() {
	}
	
	public void forceRestart() {
		PathConstants.forceRestart();
	}
	
	private <T> void crawlAndSave(CSVHandler<T> csvHandler, String filepath, ICrawler<T> crawler) {
		// Pages
		List<T> resultList = new ArrayList<>();
		
		File fileJson = new File(filepath);
		if (fileJson.exists()) {
			resultList = csvHandler.load(fileJson);
		} else {
			resultList = crawler.crawl();
			csvHandler.write(fileJson, resultList);
		}

		System.out.println("Collected in " + filepath + ": " + resultList.size());

//		resultList.forEach(map -> {
//			if (map instanceof Location) {
//				System.out.println(map.toString());
//			} else {
//				System.out.println(map.getClass());
//			}
//		});
	}
	
	public void startCrawl() {
		PathConstants.createRequiredDir();
		HttpClient client = HttpClient.newHttpClient();
		
		CSVHandler<Dynasty> CSVdynasty = new CSVHandler<>(Dynasty.class);
		CSVHandler<Festival> CSVfestival = new CSVHandler<>(Festival.class);
		CSVHandler<Location> CSVlocation = new CSVHandler<>(Location.class);
		
		crawlAndSave(CSVdynasty, PathConstants.pathDynasty, new DynastyCrawler(client));
		crawlAndSave(CSVfestival, PathConstants.pathFestival, new FestivalCrawler(client));
		crawlAndSave(CSVlocation, PathConstants.pathLocation, new DiTichLocationCrawler());
	}
}
