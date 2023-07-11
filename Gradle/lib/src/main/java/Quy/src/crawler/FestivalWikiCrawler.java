package Quy.src.crawler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Quy.src.datamodel.DynastyEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Quy.src.datamodel.FestivalEntity;
import crawldata.wikibasis.WikiUtility;

public class FestivalWikiCrawler extends AbstractCrawler {
	public void start() {
		String url = "https://vi.wikipedia.org/wiki/Các_ngày_lễ_ở_Việt_Nam";
		String rootURL = "https://vi.wikipedia.org";
		crawled = new LinkedList<>();
		
		System.out.println("Crawler: FestivalWikiCrawler");
		
		Document document = WikiUtility.getWikiDocumentFromURL(url);
		if (document == null) {
			System.out.println("End FestivalWikiCrawler");
			return;
		}
		
		// Lấy bảng thông tin thứ 2 trong trang web.
		Element table = document.select(".wikitable").get(1);
		if (table == null) {
			System.out.println("End FestivalWikiCrawler");
			return;
		}
		
		List<String> tableHeaders = WikiUtility.getWikiTableHeaders(table);
		List<List<Element>> tableElements = WikiUtility.getWikiTableElements(table);
		
		for (List<Element> currentRowElements: tableElements) {
			
			String festivalName = currentRowElements.get(1).text();
			String linkToDetailedPost = rootURL + currentRowElements.get(1).selectFirst("a").attr("href");
			String festivalDescription = WikiUtility.getDescriptionFromDocument(linkToDetailedPost);
			
			Map<String, String> festivalAdditionalInfo = new HashMap<String, String>();
			festivalAdditionalInfo.put(tableHeaders.get(0), currentRowElements.get(0).text());
			festivalAdditionalInfo.put(tableHeaders.get(2), currentRowElements.get(2).text());
			
			crawled.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL));
			jsonArray.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL).toJSONObject());
			System.out.println("+1 Festival from Wikipedia");
		}
		
		System.out.println("End FestivalWikiCrawler");
	}
}
