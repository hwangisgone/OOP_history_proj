package Hoang.crawler.nonwiki;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DefaultJsoup {
	public static Document getDoc(String url) throws IOException {
		return Jsoup
				.connect(url)
				.timeout(10000)
				.maxBodySize(10*1024*1024)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.get();
	}
}
