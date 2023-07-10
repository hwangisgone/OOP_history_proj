package Hoang.crawler.location;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Hoang.Multithreader;
import Hoang.crawler.BaseCrawler;
import entity.Festival;
import entity.Location;

public class DiTichLocationCrawler extends BaseCrawler {

	@Override
	public void crawl() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Location>> typeReference = new TypeReference<List<Location>>() {};
		// Pages
		List<Location> resultLocation = new ArrayList<>();
		
		File fileJson = new File(finalDirectory + "Location.json");
		if (fileJson.exists()) {
			try {
				resultLocation = mapper.readValue(fileJson, typeReference);
			} catch (StreamReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			resultLocation = getLocationsFromDiTich();
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultLocation);
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Success.");
	}
	
	private List<Location> getLocationsFromDiTich() {
		int cpage = 1;
		int totalRecordInt = 4024; // Replace with the actual total record value
		
		String url = "http://ditich.vn/FrontEnd/DiTich"; // Replace with the actual URL
		String mainUrl = "http://ditich.vn";
		

		try {
			Document docstart = Jsoup.connect(url).timeout(9000).get();
	        Element span = docstart.selectFirst("span:contains(Tổng số)");
	        
	        if (span != null) {
	            String totalRecord = span.text().split(":")[1].trim();
	            totalRecordInt = Integer.parseInt(totalRecord);
	            System.out.println("Total Record: " + totalRecordInt);
	        } else {
	        	System.out.println("Can't find span");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// Build the URL with parameters
		String fullUrl = url + "?cpage=" + cpage + "&rpage=" + totalRecordInt;
		
	    List<Location> locList = new ArrayList<>();
		
		try {
		    Document doc = Jsoup.connect(fullUrl).timeout(9000).get();
		    Elements aTags = doc.select("a[title=Xem chi tiết]");
			
		    for (Element aTag : aTags) {
		        Element thisLoc = aTag.selectFirst("span:matchesOwn((?i).+)");
		        String locText = thisLoc != null ? thisLoc.text() : null;
		
		        Location loc = new Location();
		        loc.setUrl(mainUrl + aTag.attr("href"));
		        loc.setName(aTag.selectFirst("h2").text());
		        loc.setLocated(locText);
		        loc.setImage(mainUrl + aTag.selectFirst("img").attr("src"));
		        locList.add(loc);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		Multithreader multithreader = new Multithreader();
		locList = multithreader.start(locList, loc -> {
			getLocationInfo(loc);
			return loc;
		});
		
		return locList;
	}
	
	private void matchKeyVal(String key, String val, Location loc) {
		switch (key) {
			case "Xếp hạng":
				loc.setGrade(val); break;
			case "Loại hình xếp hạng":
				loc.setGradeType(val); break;
			case "Đối tượng thờ": 
				loc.setWorship(val); break;
			case "Tọa độ": 
				loc.setPosition(val); break;
			default:
				break;
		}
	}
	
	private void getLocationInfo(Location location) {
        String url = location.getUrl();
        System.out.println("Getting " + location.getName() + "...");
        try {
            Document doc = Jsoup.connect(url).get();
            Elements divs = doc.select("div.hl__illustrated-list__list-item");

            for (Element div : divs) {
                Element span = div.selectFirst("span:matchesOwn(.*:.*)");

                if (span == null) {
                    continue;
                }

                String[] spanSplit = span.text().split(":", 2);
                String key = spanSplit[0].trim();
                String val = spanSplit[1].trim();

                if (!key.isEmpty() && !val.isEmpty()) {
                    matchKeyVal(key, val, location);
                } else {
                    System.out.println("ERR in split: for span" + spanSplit[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
