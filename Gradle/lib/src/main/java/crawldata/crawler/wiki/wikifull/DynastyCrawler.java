package crawldata.crawler.wiki.wikifull;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;

import crawldata.wikibasis.CategoryFinder;
import crawldata.wikibasis.PageFinder;
import crawldata.wikibasis.WikiUtility;
import crawldata.wikibasis.infobox.DynastyInfoboxExtractor;
import crawldata.wikibasis.infobox.InfoboxException;
import crawldata.wikibasis.infobox.InfoboxExtractor;
import entity.Dynasty;
import util.ExtraStringUtil;

public class DynastyCrawler extends WikiCrawler<Dynasty> {
	private HttpClient client;

	public DynastyCrawler(HttpClient client) {
		super("src/main/resources/final/trieu_dai/");
		this.client = client;
	}

	@Override
	protected List<String> getCategories() {
		CategoryFinder catFinder = new CategoryFinder(client);
		Set<String> catSet = new HashSet<>(); // Ensure no duplicates

		// Get subcategories
		catSet.add("Thể loại:Triều đại Việt Nam");
		catFinder.getCategoriesFor(catSet, "Thể loại:Triều đại Việt Nam", 1);

		// Filter subcategories
		List<String> wordsFilter = Arrays.asList(
			":Thời kỳ",
			":Nhà",
			":Triều đại"
		);
		return ExtraStringUtil.filterString(catSet, wordsFilter, false);
	}

	@Override
	protected List<String> getPagesFromCats(List<String> categories) {
		PageFinder pageFinder = new PageFinder(client);
		Set<String> pageSet = new HashSet<>(); // Ensure no duplicates

		pageFinder.getPagesFor(pageSet, categories);

		List<String> wordsFilter = Arrays.asList(
			"Thời kỳ",
	        "Nhà",
	        "Triều đại",
	        "cổ đại"
		);
		return ExtraStringUtil.filterString(pageSet, wordsFilter, true);
	}

	@Override
	protected List<Dynasty> getInfoFromPages(List<String> pages) {
		InfoboxExtractor<Dynasty> ext = new DynastyInfoboxExtractor();

		List<Dynasty> dynasties = new ArrayList<>();
		Map<String, Document> docs = WikiUtility.getDocumentsFromPages(pages, client);
		for (Map.Entry<String, Document> entry : docs.entrySet()) {
		    String title = entry.getKey();
		    Document doc = entry.getValue();

		    Dynasty dynasty = new Dynasty();
		    dynasty.setID(title);

		    try {
				ext.getInfoFromHtmlFor(doc, dynasty);
			    dynasty.setDescription(WikiUtility.getDescriptionFromDocument(doc));

			    dynasties.add(dynasty);
			} catch (InfoboxException e) {

				// Don't add without infobox
				System.err.println(e.getMessage());
			}
		}

		return dynasties;
	}
}
