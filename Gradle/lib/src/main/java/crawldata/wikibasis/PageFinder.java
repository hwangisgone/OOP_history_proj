package crawldata.wikibasis;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import crawldata.util.URLMaker;
import main.Multithreader;

public class PageFinder {
	private HttpClient client;

	public PageFinder(HttpClient client) {
		this.client = client;
	}

	public void getPagesFor(Collection<String> pages, List<String> categories) {
		List<String> original = URLMaker.getPageQueries(categories);

		Multithreader multithreader = new Multithreader();
        for (HttpResponse<String> response : multithreader.getResponseFromPages(client, original)) {

        	// Parsing response
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                // System.out.println(jsonResponse);

                ObjectMapper mapper = new ObjectMapper();

                try {
					JsonNode query = mapper.readTree(jsonResponse).get("query");

					if (query != null) {
						JsonNode pageNodes = query.get("categorymembers");

						int index = 0;
		                if (pageNodes.isArray()) {
		                	for (JsonNode pageNode: pageNodes) {
		                		String childTitle = pageNode.get("title").asText();
		                		pages.add(childTitle);

		                		System.out.println(index + " " + childTitle);
		                		index += 1;
		                	}
		                }
					}

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
	}

}
