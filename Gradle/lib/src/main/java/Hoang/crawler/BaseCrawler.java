package Hoang.crawler;

import java.io.File;
import java.util.List;

import Hoang.CSVHandler;
import Hoang.TextHandler;

public abstract class BaseCrawler {
	protected static final String finalDirectory = "src/main/resources/final/";
	private String tempDirectory;
	private boolean forceRestart = false;


	public void setForceRestart() {
		this.forceRestart = true;
	}
	
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
        
        if (forceRestart) {
        	for (File f: finalFileDir.listFiles())  f.delete();
        	for (File f: fileDir.listFiles())  f.delete();
        }
	}

	protected abstract List<String> getCategories();
	protected abstract List<String> getPagesFromCats(List<String> categories);
	protected abstract void workWithObjectsFromPages(List<String> pages);
	
	public void crawl() {
		// Responsibility: Handle file and CSV, connecting multiple parts
		// CSVHandler thi = new CSVHandler();
		TextHandler thi = new TextHandler();

		createRequiredDir();

		// Categories
		List<String> cats;
		File fileCats = new File(tempDirectory + "Cats.txt");
		if (fileCats.exists()) {
			cats = thi.readListFromFile(fileCats);
		} else {
			cats = this.getCategories();
			thi.writeListToFile(fileCats, cats);
		}

		// Pages
		List<String> pages;
		File filePages = new File(tempDirectory + "Pages.txt");
		if (filePages.exists()) {
			pages = thi.readListFromFile(filePages);
		} else {
			pages = this.getPagesFromCats(cats);
			thi.writeListToFile(filePages, pages);
		}

		workWithObjectsFromPages(pages);
	}

}
