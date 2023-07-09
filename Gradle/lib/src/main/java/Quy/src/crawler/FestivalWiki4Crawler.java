package Quy.src.crawler;

import java.util.LinkedList;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Quy.src.datamodel.FestivalEntity;

public class FestivalWiki4Crawler extends AbstractCrawler {
	public void start() {
		String url = "https://vi.wikipedia.org/wiki/Thể_loại:Lễ_hội_Việt_Nam_theo_tỉnh_thành";
		String rootURL = "https://vi.wikipedia.org";
		crawled = new LinkedList<>();
		
		System.out.println("Crawler: FestivalWiki4Crawler");
		
		Document document = WikiUtility.getWikiDocumentFromURL(url);
		if (document == null) {
			System.out.println("End FestivalWiki4Crawler");
			return;
		}
		
		// Lấy pages chứa các lễ hội trong trang web.
		Elements pages = document.select("#mw-subcategories li");
		
		for (Element page: pages) {
			Element aTag = page.selectFirst("a");
			if (aTag == null) continue;
			
			String linkToPage = rootURL + aTag.attr("href");
			
			Document pageDocument = WikiUtility.getWikiDocumentFromURL(linkToPage);
			if (pageDocument == null) continue;
			
			Elements festivals = pageDocument.select("#mw-pages li");
			
			for (Element festival: festivals) {
				String festivalName = festival.text();
				if (festivalName.length() < 4) continue;
				
				Element aTag_toFestival = festival.selectFirst("a");
				if (aTag_toFestival == null) continue;
				
				String linkToDetailedPost = rootURL + aTag_toFestival.attr("href");
				String festivalDescription = WikiUtility.getDescriptionFromContentBoxOfURL(linkToDetailedPost);
				Map<String, String> festivalAdditionalInfo = WikiUtility.getAdditionalInfoFromWikiInfoBoxOfURL(linkToDetailedPost);
				if (festivalDescription == "Chưa có thông tin." && festivalAdditionalInfo.size() == 0) continue;
				
				crawled.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL));
				jsonArray.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL).toJSONObject());
				System.out.println("+1 Festival from Wikipedia");
			}
		}
		
		System.out.println("End FestivalWiki4Crawler");
	}
}