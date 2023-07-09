package Hoang;

import java.net.http.HttpClient;

import Hoang.crawler.WikiCrawler;
import Hoang.crawler.BaseCrawler;
import Hoang.crawler.FestivalCrawler;
import Hoang.crawler.DynastyCrawler;


public class App {
    public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		WikiCrawler crawler = new DynastyCrawler(client);

		crawler.crawl();
    }
}
