package Hoang;

import java.net.http.HttpClient;

import Hoang.crawler.WikiCrawler;
import Hoang.crawler.location.DiTichLocationCrawler;
import Hoang.crawler.BaseCrawler;
import Hoang.crawler.FestivalCrawler;
import Hoang.crawler.DynastyCrawler;


public class App {
    public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		BaseCrawler crawler = new DiTichLocationCrawler();

		crawler.crawl();
    }
}
