package Hoang.crawler;

import java.io.File;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import Hoang.CSVHandler;
import Hoang.crawler.location.DiTichLocationCrawler;

public class MainCrawler {
	private static final String finalDirectory = "src/main/resources/final/";
	private boolean forceRestart;
	private CSVHandler csvHandler;
	
	public MainCrawler() {
		this.csvHandler = new CSVHandler();
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
	
	private <T> void crawlAndSave(String filename, ICrawler<T> crawler) {
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
//			System.out.println(map.toString());
//		});
	}
	
	public void startCrawl() {
		createRequiredDir();
		HttpClient client = HttpClient.newHttpClient();
		
		crawlAndSave("Dynasty.json", new DynastyCrawler(client));
		crawlAndSave("Festival.json", new FestivalCrawler(client));
		crawlAndSave("Location.json", new DiTichLocationCrawler());
	}
}
