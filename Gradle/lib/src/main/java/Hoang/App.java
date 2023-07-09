package Hoang;

import java.net.http.HttpClient;

import Hoang.crawler.BaseCrawler;
import Hoang.crawler.DynastyCrawler;
import Hoang.crawler.FestivalCrawler;

public class App {
    public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		BaseCrawler crawler = new FestivalCrawler(client);

		crawler.setForceRestart();
		crawler.crawl();
    }
}
