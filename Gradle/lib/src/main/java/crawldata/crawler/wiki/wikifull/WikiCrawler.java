package crawldata.crawler.wiki.wikifull;

import java.io.File;
import java.util.List;

import crawldata.crawler.ICrawler;
import database.handler.TextHandler;

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

	@Override
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
