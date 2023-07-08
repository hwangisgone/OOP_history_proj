package Quy.src.crawler;

import java.util.List;

import Quy.src.datamodel.BaseEntity;

public abstract class AbstractCrawler {
	public List<BaseEntity> crawled;
	public abstract void start() throws Exception;
	public List<BaseEntity> getCrawledData(){
		return this.crawled;
	}
}
