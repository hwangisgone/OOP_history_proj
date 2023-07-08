package Hoang.crawler;

import java.io.File;
import java.util.List;

import Hoang.CSVHandler;

public abstract class BaseCrawler {
	protected static final String finalDirectory = "src/main/resources/final/";
	private String tempDirectory;

	public BaseCrawler(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}

	private void createRequiredDir() {
		File finalFileDir = new File(finalDirectory);
        if (!finalFileDir.exists()) {
        	finalFileDir.mkdirs();
        }

		File fileDir = new File(tempDirectory);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
	}

	protected abstract List<String> getCategories();
	protected abstract List<String> getPagesFromCats(List<String> categories);
	protected abstract void workWithObjectsFromPages(List<String> pages);

	public void crawl() {
		// Responsibility: Handle file and CSV, connecting multiple parts
		CSVHandler thi = new CSVHandler();

		createRequiredDir();

		// Categories
		List<String> cats;
		File fileCats = new File(tempDirectory + "Cats.csv");
		if (fileCats.exists()) {
			cats = thi.readStringFromCSV(fileCats);
		} else {
			cats = this.getCategories();
			thi.writeStringToCSV(fileCats, cats);
		}

		// Pages
		List<String> pages;
		File filePages = new File(tempDirectory + "Pages.csv");
		if (filePages.exists()) {
			pages = thi.readStringFromCSV(filePages);
		} else {
			pages = this.getPagesFromCats(cats);
			thi.writeStringToCSV(filePages, pages);
		}

		workWithObjectsFromPages(pages);
	}
}
