package Hoang.basis;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryFinder {
	private static final String URL = "https://vi.wikipedia.org/w/api.php";
	private static final String URL_SUBCAT = URL + "?action=query&cmlimit=100&list=categorymembers&format=json&formatversion=2&cmtype=subcat&cmtitle=";
	private Collection<String> catFilter;
	private HttpClient client;

    public CategoryFinder(HttpClient client) {
		this.client = client;
	}

	public void setCatFilter(Collection<String> catFilter) {
		this.catFilter = catFilter;
	}

	private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private void getCategoriesRecursive(Collection<String> collectedCats, String pageTitle, int level, int maxlevel) {
        try {
            HttpResponse<String> response = client.send(
            		HttpRequest.newBuilder()
                    .uri(URI.create(URL_SUBCAT + encodeValue(pageTitle)))
                    .GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                // System.out.println(jsonResponse);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode PAGES = mapper.readTree(jsonResponse).get("query").get("categorymembers");

                int index = 0;
                if (PAGES.isArray()) {
                	for (JsonNode page: PAGES) {
                		String childTitle = page.get("title").asText();

                		if (collectedCats.contains(childTitle) // No duplicate allowed
                				|| (catFilter != null && catFilter.contains(childTitle))) { // Filter in
                			continue;
                		}

                		for (int i = 0; i < level; i++) System.out.print("- ");
                		System.out.printf("%d %s\n", (index++), childTitle);

                		collectedCats.add(childTitle);

                		if (level < maxlevel) {
                		getCategoriesRecursive(collectedCats, childTitle, level + 1, maxlevel);
                		} else {
                			System.out.println("Max level reached");
                		}
                	}
                }
            } else {
                System.out.println("HTTP request failed with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCategoriesFor(Collection<String> collected, String pageTitle, int maxlevel) {
    	getCategoriesRecursive(collected, pageTitle, 0, maxlevel);
    }
}
