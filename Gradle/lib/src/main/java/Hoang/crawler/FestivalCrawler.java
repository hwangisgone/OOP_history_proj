package Hoang.crawler;

import java.io.File;
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
import Hoang.basis.infobox.FestivalInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import entity.Dynasty;
import entity.Festival;
import Hoang.util.ResultUtil;

public class FestivalCrawler extends BaseCrawler {
	private HttpClient client;

	public FestivalCrawler(HttpClient client) {
		super("src/main/resources/final/le_hoi/");
		this.client = client;
	}

	@Override
	protected List<String> getCategories() {
		CategoryFinder catFinder = new CategoryFinder(client);
		Set<String> catSet = new HashSet<>(); // Ensure no duplicates

		// Get subcategories
		catSet.add("Thể_loại:Lễ_hội_Việt_Nam");
		List<String> wordsFilter = Arrays.asList(
				"Thể loại:Tết",
				"Thể loại:Đại lễ 1000 năm Thăng Long – Hà Nội"
		);
		
		catFinder.setCatFilter(wordsFilter);
		catFinder.getCategoriesFor(catSet, "Thể_loại:Lễ_hội_Việt_Nam", 4);

		return new ArrayList<>(catSet);
	}

	@Override
	protected List<String> getPagesFromCats(List<String> categories) {
		PageFinder pageFinder = new PageFinder(client);
		Set<String> pageSet = new HashSet<>(); // Ensure no duplicates

		pageFinder.getPagesFor(pageSet, categories);

//		List<String> wordsFilter = Arrays.asList(
//			"Thời kỳ",
//	        "Nhà",
//	        "Triều đại",
//	        "cổ đại"
//		);
//		ResultUtil.filterString(pageSet, wordsFilter, true);
		return new ArrayList<>(pageSet);
	}

	@Override
	protected void workWithObjectsFromPages(List<String> pages) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Festival>> typeReference = new TypeReference<List<Festival>>() {};
		// Pages
		List<Festival> resultFestival = new ArrayList<>();
		
		File fileJson = new File(finalDirectory + "Festival.json");
		if (fileJson.exists()) {
			
			try {
				resultFestival = mapper.readValue(fileJson, typeReference);
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
			InfoboxExtractor<Festival> ext = new FestivalInfoboxExtractor(client);

			resultFestival= ext.getInfoboxContents(pages);
			
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultFestival);
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
//	        try (FileWriter writer = new FileWriter(fileJson)) {
//
//	            // Convert object list to JSON and write to file
//	            gson.toJson(resultDynasty, writer);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
		}

		resultFestival.forEach(map -> {
			System.out.println(map.toString());
		});
	}
}