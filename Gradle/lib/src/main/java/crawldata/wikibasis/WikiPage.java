package crawldata.wikibasis;

import java.net.http.HttpResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WikiPage {
	private String title;
	private Document doc;
	private String url;
	
	public WikiPage(String title) {
		this.setTitle(title);
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public boolean acceptResponse(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
        	ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = response.body();

            try {
				JsonNode parse = mapper.readTree(jsonResponse).get("parse");

				if (parse == null) {
					return false;
				}

				Document parsedoc = Jsoup.parse(parse.get("text").asText());
				if (parsedoc != null) {
					this.doc = parsedoc;
					return true;
				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return false;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
}
