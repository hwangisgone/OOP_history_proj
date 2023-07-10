package Hoang.crawler;

import java.util.List;

public interface ICrawler<T> {
	public abstract List<T> crawl();
}
