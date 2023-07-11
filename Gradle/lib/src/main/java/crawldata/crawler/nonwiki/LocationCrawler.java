package crawldata.crawler.nonwiki;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import database.IDatabase;
import database.LocationDatabase;
import database.constants.PathConstants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import crawldata.URLMaker;
import crawldata.crawler.ICrawler;
import crawldata.wikibasis.WikiUtility;
import crawldata.wikibasis.infobox.InfoboxException;
import entity.Festival;
import entity.Location;
import main.Multithreader;

public class LocationCrawler implements ICrawler<Location> {
	private HttpClient client;

	public LocationCrawler(HttpClient client) {
		this.client = client;
	}
	
	private void getDescriptionsFor(Collection<Location> locs) {
		List<String> pages = new ArrayList<>();
		Map<String, Location> newMap = new HashMap<>();
		for (Location loc: locs) {
			newMap.put(loc.getID(), loc);
			pages.add(loc.getID());
		}
		
		Map<String, Document> docs = WikiUtility.getDocumentsFromPages(pages, client);
		for (Map.Entry<String, Document> entry : docs.entrySet()) {
		    Location location = newMap.get(entry.getKey());
		    Document doc = entry.getValue();

		    location.setDescription(WikiUtility.getDescriptionFromDocument(doc));
		}
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
		
		getDescriptionsFor(locations);
		
		return locations;
	}

}
