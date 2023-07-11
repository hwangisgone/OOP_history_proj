package Quy.src.crawler;

import java.util.LinkedList;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Quy.src.datamodel.FestivalEntity;
import crawldata.wikibasis.WikiUtility;

public class FestivalWiki2Crawler extends AbstractCrawler {
	public void start() {
		String url = "https://vi.wikipedia.org/wiki/Lễ_hội_các_dân_tộc_Việt_Nam";
		String rootURL = "https://vi.wikipedia.org";
		crawled = new LinkedList<>();
		
		System.out.println("Crawler: FestivalWiki2Crawler");
		
		Document document = WikiUtility.getWikiDocumentFromURL(url);
		if (document == null) {
			System.out.println("End FestivalWiki2Crawler");
			return;
		}
		
		// Lấy các lễ hội trong trang web.
		Elements festivals = document.select(".mw-parser-output div ul li");
		
		for (Element festival: festivals) {
			String festivalName = festival.text();
			if (festivalName.length() < 4) continue;
			
			Element aTag = festival.selectFirst("a");
			if (aTag == null) continue;
			
			String linkToDetailedPost = rootURL + aTag.attr("href");
			String festivalDescription = WikiUtility.getDescriptionFromDocument(linkToDetailedPost);
			Map<String, String> festivalAdditionalInfo = WikiUtility.getAdditionalInfoFromWikiInfoBoxDocument(linkToDetailedPost);
			if (festivalDescription == "Chưa có thông tin." && festivalAdditionalInfo.size() == 0) continue;
			
			crawled.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL));
			jsonArray.add(new FestivalEntity(festivalName, festivalAdditionalInfo, festivalDescription, rootURL).toJSONObject());

			System.out.println("+1 Festival from Wikipedia");
		}
		
		System.out.println("End FestivalWiki2Crawler");
	}
}
