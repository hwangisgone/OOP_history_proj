package main;

import crawldata.crawler.MainCrawler;

public class App {
    public static void main(String[] args) {

    	MainCrawler main = new MainCrawler();
    	// main.setForceRestart(true);
    	main.startCrawl();
    }
}
