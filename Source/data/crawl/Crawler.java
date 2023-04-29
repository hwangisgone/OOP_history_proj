/*
	This class is used to provide tools for crawling specific
	data:
		- HistoricalCharacter
 */

package data.crawl;

// Entiry classes
import data.entity.HistoricalCharacter;
// libary for crawling
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Attribute;
// File class
import java.io.File;
import java.io.IOException;


public class Crawler {
	
	public static HistoricalCharacter crawlHistoricalCharacter(String url) {
		HistoricalCharacter entity = new HistoricalCharacter();
		// Crawl data
		try {
			// get html document
			Document doc = Jsoup.connect(url).get();
			// get table info
			Element infobox = doc.getElementsByClass("infobox").first();
			// get attribute #1: name
			Element row = infobox.getElementsByTag("tr").first();
			String name = row.getElementsByTag("th").first().text();
			// get attribute #2: dateOfBirth
			String dateOfBirth = "";
			Element header = infobox.getElementsMatchingOwnText("Sinh").first();
			if (header != null)
				dateOfBirth = header.parent().getElementsByTag("td").first().text();
			// get attribute #3: dateofDeath
			String dateOfDeath = "";
			header = infobox.getElementsMatchingOwnText("Mất").first();
			if (header != null) 
				dateOfDeath = header.nextElementSibling().text();
			// get attribute #4: biography
			String biography = "";
			String biographyIdList[] = {"Tiểu_sử", "Thân_thế"};
			for (String id: biographyIdList) {
				header = doc.getElementById(id);
				if (header != null) {
					header = header.parent();
					break;
				}	// close if
			}	// close
			while (header != null) {
				header = header.nextElementSibling();
				if (header == null) 
					break;
				if (header.tagName().equals("h2"))
					break;
				biography += header.text() + "\n";
			}	// close while
			// get attribute #5: Career
			String career = "";
			header = doc.getElementById("Sự_nghiệp");
			if (header != null)
				header = header.parent();
			while (header != null) {
				header = header.nextElementSibling();
				if (header == null) 
					break;
				if (header.tagName().equals("h2"))
					break;
				career += header.text() + "\n";
			}	// close while
			// set attributes for HistoricalCharacter entity
			entity.setProperty("name", name);
			entity.setProperty("dateOfBirth", dateOfBirth);
			entity.setProperty("dateOfDeath", dateOfDeath);
			entity.setProperty("biography", biography);
			entity.setProperty("career", career);
		} catch (IOException e) {
			System.out.println("Unable to crawl HistoricalCharacter from: " + url);
			System.out.println("Error: " + e.getMessage());
		}	// close try
		// return HistoricalCharacter
		return entity;
	}	// close crawlHistoricalCharacter


	public static void main(String[] args) {
		
		HistoricalCharacter entity;
		
		String urlList[] = {
						"https://vi.wikipedia.org/wiki/Nguyễn_Du", 
						"https://vi.wikipedia.org/wiki/Dương_Đình_Nghệ",
						"https://vi.wikipedia.org/wiki/Ngô_Quyền"
					};
		for (String url: urlList) {
			entity = Crawler.crawlHistoricalCharacter(url);
			System.out.println("name: " + entity.getProperty("name"));
			System.out.println("dateOfBirth: " + entity.getProperty("dateOfBirth"));
			System.out.println("dateOfDeath: " + entity.getProperty("dateOfDeath"));
			System.out.println("biography: " + entity.getProperty("biography"));
			System.out.println("career: " + entity.getProperty("career"));
			System.out.println("Finished!");
			System.out.println("\t===");
		}	// close for
	}	// close main
}	// close Crawler