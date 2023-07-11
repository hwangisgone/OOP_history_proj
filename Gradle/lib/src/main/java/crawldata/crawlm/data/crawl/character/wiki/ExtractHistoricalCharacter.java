/*
	* ExtractHistoricalCharacter: to extract all historical character data of wikipedia websites
 */

package crawldata.crawlm.data.crawl.character.wiki;

import java.io.IOException;
// net
import java.net.URL;
import java.util.HashMap;
// containers
import java.util.Map;

// used classes
	// jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// custom
import crawldata.crawlm.data.crawl.ExtractData;


public class ExtractHistoricalCharacter extends ExtractData {

	private final String UNICODE = "utf-8";		// unicode of website
	private final int CAPACITY = 10;				// maximum number of fields


	/* to extract data given an url, return an Map of field name and string value */
	@Override
	public Map<String, String> extract(String url) {
		Map<String, String> instance = null;		// instance data in map format
		try {
			instance = new HashMap<>(CAPACITY);
			Document doc = Jsoup.parse(new URL(url).openStream(), UNICODE, url);
				// Get table info
			Element infobox = doc.getElementsByClass("infobox").first();
		// Get attribute #1: id - wellknown name
			Element titleElement = doc.getElementsByClass("mw-page-title-main").first();
			String id = titleElement.ownText();
		// Get attribute #2: name: full-name or real-name
			String name = "";
			String nameKeyWord[] = {"Tên húy", "Tên đầy đủ", "Tên thật"};
			// get infobox of name
			Element infoboxName = infobox.getElementsByClass("infobox").first();
			if (infoboxName != null) {			// found infobox of name
			// find element having one match in keyword-name list
				Element nameElement = null;		// is header of a row
				for (String keyword: nameKeyWord) {
					nameElement = infoboxName.getElementsMatchingOwnText(keyword).first();
					if (nameElement != null)	// found name element
						break;
				}	// close for
			// the name is contained the data of the next row
				if (nameElement != null) {
					nameElement = nameElement.parent().nextElementSibling();
					name = nameElement.text();					// NOTE: can use text() instead of ownText to capture more name cases
				}	// close if
			}	// close if
		// Get attribute #3: dateOfBirth
			String dateOfBirth = "";
			Element header = null;
			for (Element ele: infobox.getElementsByTag("th")) {					// find the row contains date of birth
				Elements setMatchs = ele.getElementsMatchingOwnText("Sinh");
				if (!setMatchs.isEmpty())
					header = setMatchs.first();
			}	// end for
			if ((header != null) && (header.nextElementSibling() != null))
				dateOfBirth = header.nextElementSibling().text();
		// Get attribute #4: dateofDeath
			String dateOfDeath = "";
			header = null;
			for (Element ele: infobox.getElementsByTag("th")) {					// find the row contains date of death
				Elements setMatchs = ele.getElementsMatchingOwnText("Mất");
				if (!setMatchs.isEmpty())
					header = setMatchs.first();
			}	// end for
			if ((header != null) && (header.nextElementSibling() != null))
				dateOfDeath = header.nextElementSibling().text();
		// Get attribute #5: career&bio
			String careerBio = "";
			String carBioList[] = {"Tiểu_sử", "Thân_thế", "Sự_nghiệp"};
			for (String keyword: carBioList) {				// search for keyword in the header
				header = doc.getElementById(keyword);
				if (header != null) {
					header = header.parent();
					break;
				}	// close if
			}	// close
			while (header != null) {
				header = header.nextElementSibling();
				if ((header == null) || header.tagName().equals("h2"))
					break;
				careerBio += header.text() + "\n";
			}	// close while
			careerBio.replace("[sửa | sửa mã nguồn]", "");
		// get attribute #6: dynasty
			String dynasty = "";
			String dynastyKeyword[] = {"Triều đại"};
			// find dynasty header contains one of keywords in the infobox
			Element dynastyHeader = null;
			for (String keyword: dynastyKeyword) {
				dynastyHeader = infobox.getElementsMatchingOwnText(keyword).first();
				if (dynastyHeader != null) {		// found the dynasty header
					// the dynasty info is in data-row next to the header
					dynasty = dynastyHeader.nextElementSibling().text();
					break;
				}	// close if
			}	// close for
		// get attribute #7: mother
			String mother = "";
			String motherKeyword[] = {"Thân mẫu", "Mẹ"};
			// find the mother header in infobox
			for (String keyword: motherKeyword) {
				Element motherHeader = infobox.getElementsMatchingOwnText(keyword).first();
				if (motherHeader != null) {
					// mother name is contained in the next element
					mother = motherHeader.nextElementSibling().text();
					break;
				}	// close if
			}	// close for
		// get attribute #8: father
			String father = "";
			String fatherKeyword[] = {"Thân phụ", "Cha"};
			// find the father header in infobox
			for (String keyword: fatherKeyword) {
				Element fatherHeaer = infobox.getElementsMatchingOwnText(keyword).first();
				if (fatherHeaer != null) {
					// father name is in next element of the header
					father = fatherHeaer.nextElementSibling().text();
					break;
				}	// close if
			}	// close for
		// Put data into map
			instance.put("url", url);						// attri #0 -> for trace back the page
			instance.put("id", id);							// attri #1
			instance.put("name", name);
			instance.put("dateOfBirth", dateOfBirth);
			instance.put("dateOfDeath", dateOfDeath);
			instance.put("career&bio", careerBio);
			instance.put("dynasty", dynasty);
			instance.put("mother", mother);
			instance.put("father", father);					// attri #8
		} catch (IOException e) {
			System.out.println("Unable to read URL: " + url);
			System.out.println("Error: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("The structure of URL is uable be to extracted, URL: " + url);
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unable to read URL: " + url);
			System.out.println("Error: " + e.getMessage());
		}	// close try
		return instance;
	}	// close extract


	private void testExtractOneUrl(String url) {
		ExtractData extractor = new ExtractHistoricalCharacter();
		Map<String, String> map = extractor.extract(url);
		for (Map.Entry<String, String> entry: map.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}	// close for
	}	// close main

}	// close ExtractHistoricalCharacter