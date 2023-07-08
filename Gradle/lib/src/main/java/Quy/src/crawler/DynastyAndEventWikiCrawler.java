package Quy.src.crawler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Quy.src.datamodel.DynastyEntity;

public class DynastyAndEventWikiCrawler extends AbstractCrawler {

	private JSONArray jsonArray = new JSONArray();

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	@Override
	public void start()
	{
		String url = "https://vi.wikipedia.org/wiki/Các_cuộc_chiến_tranh_Việt_Nam_tham_gia";
		String rootURL = "https://vi.wikipedia.org";
		crawled = new LinkedList<>();
		System.out.println("Crawler: DynastyWikiCrawler");

		Document document = WikiUtility.getWikiDocumentFromURL(url);
		if (document == null) {
			System.out.println("End DynastyWikiCrawler");
			return;
		}
		Element content = document.selectFirst("#mw-content-text .mw-parser-output");
		if (content == null) {
			System.out.println("End DynastyWikiCrawler");
			return;
		}

		Map<Element, List<Element>> dynasty__tables = new HashMap<>();
		Element prev_h2 = null;
		Element prev_h3 = null;
		boolean has_h3 = false;

		for (Element child: content.children()) {
			String type = child.tagName();

			switch(type) {
				case "p": case "ul": case "figure": case "div": break;
				case "h2":
					prev_h2 = child;
					has_h3 = false;
					break;
				case "h3":
					prev_h3 = child;
					has_h3 = true;
					break;
				case "table":
					if (child.hasClass("wikitable")) {
						Element dynasty = has_h3 ? prev_h3 : prev_h2;
						if (dynasty__tables.get(dynasty) == null) dynasty__tables.put(dynasty, new LinkedList<>());
						dynasty__tables.get(dynasty).add(child);
					}
			}
		}

		for (Map.Entry<Element, List<Element>> entry: dynasty__tables.entrySet()) {
			Element dynasty = entry.getKey();
			dynasty.selectFirst(".mw-editsection").remove();
			String dynastyName = dynasty.text();
			boolean isThisDynastyValid = false;

			Elements aTags = dynasty.select("a");
			//
			if (aTags.size() != 0) {
				String linkToDetailedPost = rootURL + aTags.first().attr("href");

				Map<String, String> dynastyAdditionalInfo = WikiUtility.getAdditionalInfoFromWikiInfoBoxOfURL(linkToDetailedPost);
				String dynastyDescription = WikiUtility.getDescriptionFromContentBoxOfURL(linkToDetailedPost);

				isThisDynastyValid = (dynastyDescription != "Chưa có thông tin.") || (dynastyAdditionalInfo.size() != 0);

				if (isThisDynastyValid) {
					crawled.add(new DynastyEntity(dynastyName, dynastyAdditionalInfo, dynastyDescription, rootURL));
					jsonArray.add((new DynastyEntity(dynastyName, dynastyAdditionalInfo, dynastyDescription, rootURL)).toJSONObject());
					System.out.println("+1 Dynasty from Wikipedia");
				}
			}
		}

		System.out.println("End DynastyWikiCrawler");
	}
}
