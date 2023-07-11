package crawldata.crawler;

import java.net.http.HttpClient;
import java.util.List;

import crawldata.crawler.nonwiki.DiTichLocationCrawler;
import crawldata.crawler.wiki.DynastyCrawler;
import crawldata.crawler.wiki.FestivalCrawler;
import database.DynastyDatabase;
import database.FestivalDatabase;
import database.IDatabase;
import database.LocationDatabase;
import database.constants.PathConstants;
import entity.Dynasty;
import entity.Festival;
import entity.Location;

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

		List<Dynasty> dynasties = dynastyDB.loadOr(() -> {
			ICrawler<Dynasty> crawler = new DynastyCrawler(client);
			List<Dynasty> dynastyList = crawler.crawl();
			dynastyDB.store(dynastyList);

			return dynastyList;
		});

		List<Festival> festivals = festivalDB.loadOr(() -> {
			ICrawler<Festival> crawler = new FestivalCrawler(client);
			List<Festival> festivalList = crawler.crawl();
			festivalDB.store(festivalList);

			return festivalList;
		});

		List<Location> locations = locationDB.loadOr(() -> {
			ICrawler<Location> crawler = new DiTichLocationCrawler();
			List<Location> locList = crawler.crawl();
			locationDB.store(locList);

			return locList;
		});

		System.out.println("Collected in Dynasty: " + dynasties.size());
		System.out.println("Collected in Festival: " + festivals.size());
		System.out.println("Collected in Location: " + locations.size());
	}
}
