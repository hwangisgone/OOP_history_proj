package crawldata;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class URLMaker {

	private static final String URL = "https://vi.wikipedia.org/w/api.php";
	private static final String URL_SUBCAT = URL + "?action=query&cmlimit=100&list=categorymembers&format=json&formatversion=2&cmtype=subcat&cmtitle=";
	private static final String URL_SUBPAGE = URL + "?action=query&cmlimit=100&list=categorymembers&format=json&formatversion=2&cmtype=page&cmtitle=";
	private static final String URL_HTML = URL + "?action=parse&format=json&formatversion=2&redirects=true&page=";

    private static String encodeCorrectURL(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    private static List<String> getQueries(List<String> titles, String initialUrl) {
        List<String> urls = new ArrayList<>();

        for(String title: titles) {
        	urls.add(initialUrl + encodeCorrectURL(title));
        }

        return urls;
    }

	public static List<String> getSubcategoryQueries(List<String> titles) {
		return getQueries(titles, URL_SUBCAT);
	}

	public static List<String> getPageQueries(List<String> titles) {
		return getQueries(titles, URL_SUBPAGE);
	}

	public static List<String> getHtmlQueries(List<String> titles) {
		return getQueries(titles, URL_HTML);
	}

	public static String getHtmlQuery(String title) {
		return URL_HTML + encodeCorrectURL(title);
	}

//	public URLMaker() {
//		// TODO Auto-generated constructor stub
//	}
}
