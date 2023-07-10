package Hoang.crawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Hoang.CSVHandler;
import Hoang.TextHandler;
import Hoang.basis.infobox.FestivalInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import entity.Festival;

public abstract class WikiCrawler<T> implements ICrawler<T> {
	protected String tempDirectory;
	private boolean forceRestart;
	
	public void setForceRestart(boolean forceRestart) {
		this.forceRestart = forceRestart;
	}
	
	public WikiCrawler(String tempDirectory) {
		this.tempDirectory = tempDirectory;
	}
	
	private void createRequiredDir() {
		File fileDir = new File(tempDirectory);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        
        if (forceRestart) {
        	for (File f: fileDir.listFiles())  f.delete();
        }
	}

	protected abstract List<String> getCategories();
	protected abstract List<String> getPagesFromCats(List<String> categories);
	protected abstract List<T> getInfoFromPages(List<String> pages);

	public List<T> crawl() {
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

		return this.getInfoFromPages(pages);
	}
}
