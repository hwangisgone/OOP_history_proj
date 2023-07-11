package crawldata.crawler.wiki;

import java.net.http.HttpClient;
import java.util.List;
import database.IDatabase;
import database.LocationDatabase;
import database.constants.PathConstants;

import crawldata.crawler.ICrawler;
import crawldata.crawler.nonwiki.DiTichLocationCrawler;
import crawldata.wikibasis.WikiUtility;
import entity.Location;

public class LocationCrawler implements ICrawler<Location> {
	private HttpClient client;

	public LocationCrawler(HttpClient client) {
		this.client = client;
	}
	
	@Override
	public List<Location> crawl() {
		IDatabase<Location> locationDB = new LocationDatabase(PathConstants.finalDirectory + "DTLocation.json");
		
		List<Location> locations = locationDB.loadOr(() -> {
			ICrawler<Location> crawler = new DiTichLocationCrawler();
			List<Location> locList = crawler.crawl();
			locationDB.store(locList);

			return locList;
		});
		
		WikiUtility.getDescriptionsFor(locations, client);
		
		return locations;
	}

}
