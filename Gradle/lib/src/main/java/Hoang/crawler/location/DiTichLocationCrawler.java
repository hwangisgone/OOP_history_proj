package Hoang.crawler.location;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Hoang.crawler.BaseCrawler;
import entity.Festival;
import entity.Location;

public class DiTichLocationCrawler extends BaseCrawler {

	@Override
	protected void crawl() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Location>> typeReference = new TypeReference<List<Location>>() {};
		// Pages
		List<Location> resultLocation = new ArrayList<>();
		
		File fileJson = new File(finalDirectory + "Location.json");
		if (fileJson.exists()) {
	}
	
	
	
}
	public class HtmlParsingExample {
	    public static void main(String[] args) {
	        int cpage = 1;
	        int totalrecord = 10; // Replace with the actual total record value

	        String url = "http://example.com"; // Replace with the actual URL
	        String mainUrl = "http://ditich.vn";

	        // Build the URL with parameters
	        String fullUrl = url + "?cpage=" + cpage + "&rpage=" + totalrecord;

	        try {
	            Document doc = Jsoup.connect(fullUrl).timeout(5000).get();
	            Elements aTags = doc.select("a[title=Xem chi tiết]");

	            List<Location> locList = new ArrayList<>();

	            for (Element aTag : aTags) {
	                Element thisLoc = aTag.selectFirst("span:matchesOwn((?i).+)");
	                String locText = thisLoc != null ? thisLoc.text() : null;

	                locList.add(new Location(
	                        aTag.selectFirst("h2").text(),
	                        mainUrl + aTag.attr("href"),
	                        mainUrl + aTag.selectFirst("img").attr("src"),
	                        locText
	                ));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
