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
import Hoang.basis.infobox.DynastyInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import entity.Dynasty;
import Hoang.util.ResultUtil;

public class DynastyCrawler extends BaseCrawler {
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
		List<String> wordsFilter = Arrays.asList(":Thời kỳ", ":Nhà", ":Triều đại");
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
	protected void workWithObjectsFromPages(List<String> pages) {

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Dynasty>> typeReference = new TypeReference<List<Dynasty>>() {};
		// Pages
		List<Dynasty> resultDynasty = new ArrayList<>();
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		File fileJson = new File(finalDirectory + "Dynasty.json");
		if (fileJson.exists()) {
			
			try {
				resultDynasty = mapper.readValue(fileJson, typeReference);
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
			
//	        try (FileReader reader = new FileReader(fileJson)) {
//
//	            // Load JSON file into object list
//	            resultDynasty = gson.fromJson(reader, new TypeToken<List<Dynasty>>() {}.getType());
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            resultDynasty 
//	        }
		} else {
			InfoboxExtractor<Dynasty> ext = new DynastyInfoboxExtractor(client);

			resultDynasty = ext.getInfoboxContents(pages);
			
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultDynasty);
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

		resultDynasty.forEach(map -> {
			System.out.println(map.toString());
		});
	}
}
