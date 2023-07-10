package Hoang.crawler;

import java.io.File;
import java.util.List;

import Hoang.CSVHandler;
import Hoang.TextHandler;

public abstract class BaseCrawler {
	protected static final String finalDirectory = "src/main/resources/final/";
	
	public BaseCrawler() {

	}

	public abstract void crawl();
}
