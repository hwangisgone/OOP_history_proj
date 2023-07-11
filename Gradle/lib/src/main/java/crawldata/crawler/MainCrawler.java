package crawldata.crawler;

import java.io.File;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import crawldata.crawler.nonwiki.DiTichLocationCrawler;
import crawldata.crawler.wiki.DynastyCrawler;
import crawldata.crawler.wiki.FestivalCrawler;
import database.DynastyDatabase;
import database.FestivalDatabase;
import database.IDatabase;
import database.LocationDatabase;
import database.PathConstants;
import entity.Dynasty;
import entity.Festival;
import entity.Location;
import main.CSVHandler;

public class MainCrawler {
	public MainCrawler() {
	}

	public void forceRestart() {
		PathConstants.forceRestart();
	}

	public void startCrawl() {
		PathConstants.createRequiredDir();
		HttpClient client = HttpClient.newHttpClient();

		IDatabase<Dynasty> dynastyDB = new DynastyDatabase();
		IDatabase<Festival> festivalDB = new FestivalDatabase();
		IDatabase<Location> locationDB = new LocationDatabase();

		List<Dynasty> dynastyList = dynastyDB.loadOr(() -> {
			ICrawler<Dynasty> crawler = new DynastyCrawler(client);
			return crawler.crawl();
		});

		List<Festival> festivalList = festivalDB.loadOr(() -> {
			ICrawler<Festival> crawler = new FestivalCrawler(client);
			return crawler.crawl();
		});

		List<Location> locList = locationDB.loadOr(() -> {
			ICrawler<Location> crawler = new DiTichLocationCrawler();
			return crawler.crawl();
		});

		System.out.println("Collected in Dynasty: " + dynastyList.size());
		System.out.println("Collected in Festival: " + festivalList.size());
		System.out.println("Collected in Location: " + locList.size());
	}
}
