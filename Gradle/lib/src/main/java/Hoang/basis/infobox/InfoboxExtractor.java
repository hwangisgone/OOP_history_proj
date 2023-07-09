package Hoang.basis.infobox;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Hoang.Multithreader;
import Hoang.URLMaker;

public abstract class InfoboxExtractor<T> {
	private HttpClient client;

	public InfoboxExtractor(HttpClient client) {
		this.client = client;
	}

	protected abstract T endNew();
	protected abstract void startNew(String title);
	protected abstract void mapKeyVal(Element key, Element val);
	protected abstract void mapFindInInfobox(Element infobox);
	protected abstract boolean mapRow(int index, Element key, Element val);

	protected List<String> splitComma(String text) {
		return Arrays.stream(text.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
	}

	private void remapBr(Element el) {
		Elements brTags = el.select("br");
        for (Element br : brTags) {
//        	br.tagName("span");
//        	br.text("<br/>");
        	br.before(", ");
        }
	}

	private T getInfoFromHtml(String html, String title) throws InfoboxException {
		Document doc = Jsoup.parse(html);
		Element infobox = doc.selectFirst("table.infobox");

		if (infobox == null) {
			throw new InfoboxException("Infobox not found for: " + title);
		}
		this.remapBr(infobox);
		this.startNew(title);
		this.mapFindInInfobox(infobox);

		Elements rows = infobox.select("tr");
		for (int i = 0; i < rows.size(); i++) {
			Element key = rows.get(i).selectFirst("th");
			Element val = rows.get(i).selectFirst("td");

			boolean result = this.mapRow(i, key, val);
			if (!result) {
				// Default logic
				if (key != null && val != null ) {
					this.mapKeyVal(key, val);
				} else {
//					System.out.println("Null " + i + ": --" 
//							+ (key != null ? key.text() : "") + "--" 
//							+ (val != null ? val.text() : "") + "--");
				}
			}
		}

		return this.endNew();
	}

	public List<T> getInfoboxContents(List<String> pages) {
		List<String> htmlurls = URLMaker.getHtmlQueries(pages);
		List<T> infos = new ArrayList<>();

		int index = 0;
		Multithreader multithreader = new Multithreader();
        for (HttpResponse<String> response : multithreader.getResponseFromPages(client, htmlurls)) {
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                // System.out.println(jsonResponse);

                ObjectMapper mapper = new ObjectMapper();

                try {
					JsonNode parse = mapper.readTree(jsonResponse).get("parse");

					if (parse == null) { continue; }

					try {
						T output = getInfoFromHtml(parse.get("text").asText(), pages.get(index));
						infos.add(output);
					} catch (InfoboxException e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}


				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            index += 1;
        }

        return infos;
	}
}
