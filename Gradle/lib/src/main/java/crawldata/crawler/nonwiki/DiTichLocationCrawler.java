package crawldata.crawler.nonwiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawldata.crawler.ICrawler;
import entity.Location;
import main.Multithreader;

public class DiTichLocationCrawler implements ICrawler<Location> {

	@Override
	public List<Location> crawl() {
		return getLocationsFromDiTich();
	}

	private List<Location> getLocationsFromDiTich() {
		int cpage = 1;
		int totalRecordInt = 4024; // Replace with the actual total record value

		String url = "http://ditich.vn/FrontEnd/DiTich"; // Replace with the actual URL
		String mainUrl = "http://ditich.vn";


		try {
			Document docstart = DefaultJsoup.getDoc(url);
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
		    Document doc = DefaultJsoup.getDoc(fullUrl);
		    Elements aTags = doc.select("a[title=Xem chi tiết]");
		    System.out.println(fullUrl);
		    System.out.println(aTags.size());

		    for (Element aTag : aTags) {
		        Element thisLoc = aTag.selectFirst("span:matchesOwn((?i).+)");
		        String locText = thisLoc != null ? thisLoc.text() : null;

		        Location loc = new Location();
		        loc.setUrl(mainUrl + aTag.attr("href"));
		        loc.setID(aTag.selectFirst("h2").text());
		        loc.setLocated(locText);
		        loc.setImage(mainUrl + aTag.selectFirst("img").attr("src"));

		        loc.setID(loc.getID().substring(0, 1).toUpperCase() + loc.getID().substring(1)); // Capitalize first letter
		        locList.add(loc);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Connecting to DiTich failed");
		    return locList;
		}

		List<Location> failedLocations = new ArrayList<>();

		System.out.println("Success.");

		Multithreader multithreader = new Multithreader();
		locList = multithreader.start(locList, loc -> {
			try {
				this.getLocationInfo(loc);
			} catch (IOException e) {
				failedLocations.add(loc);
			}
			return loc;
		});


//		for (Location loc: locList) {
//			getLocationInfo(loc);
//			System.out.println(i);
//			i--;
//		}

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

	private void getLocationInfo(Location location) throws IOException {
        System.out.println("Getting " + location.getID() + "...");

        Document doc = DefaultJsoup.getDoc(location.getUrl());
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
                System.err.println("ERR in split: for span" + spanSplit[0]);
            }
        }
    }

}
