package Hoang.crawler.wiki;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Hoang.basis.CategoryFinder;
import Hoang.basis.PageFinder;
import Hoang.basis.infobox.DynastyInfoboxExtractor;
import Hoang.basis.infobox.FestivalInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import entity.Dynasty;
import Hoang.util.ResultUtil;

public class DynastyCrawler extends WikiCrawler<Dynasty> {
	private HttpClient client;

	public DynastyCrawler(HttpClient client) {
		super("src/main/resources/crawl_temp/trieu_dai/");
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
		return ResultUtil.filterString(catSet, wordsFilter, false);
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
		return ResultUtil.filterString(pageSet, wordsFilter, true);
	}
	
	@Override
	protected List<Dynasty> getInfoFromPages(List<String> pages) {
		InfoboxExtractor<Dynasty> ext = new DynastyInfoboxExtractor(client);
		return ext.getInfoboxContents(pages);
	}
}
