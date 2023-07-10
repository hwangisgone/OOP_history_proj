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

import Hoang.TextHandler;
import Hoang.basis.infobox.FestivalInfoboxExtractor;
import Hoang.basis.infobox.InfoboxExtractor;
import entity.Festival;

public abstract class WikiCrawler extends BaseCrawler {
	protected String tempDirectory;
	private boolean forceRestart;
	
	public void setForceRestart(boolean forceRestart) {
		this.forceRestart = forceRestart;
	}
	
	public WikiCrawler(String tempDirectory) {
		super();
		this.tempDirectory = tempDirectory;
		// TODO Auto-generated constructor stub
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
	
	protected <T> void workWithObjectsFromPages(List<String> pages, String filename, InfoboxExtractor<T> ext) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
		// Pages
		List<T> resultList = new ArrayList<>();
		
		File fileJson = new File(finalDirectory + filename);
		if (fileJson.exists()) {
			
			try {
				resultList = mapper.readValue(fileJson, typeReference);
			} catch (StreamReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			resultList = ext.getInfoboxContents(pages);
			
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultList);
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		resultList.forEach(map -> {
			System.out.println(map.toString());
		});
	}
	
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
