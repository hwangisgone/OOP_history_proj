package Quy.src.crawler;

import java.util.List;

import Quy.src.datamodel.BaseEntity;
import org.json.simple.JSONArray;

public abstract class AbstractCrawler {

	protected JSONArray jsonArray = new JSONArray();

	public JSONArray getJsonArray() {
		return jsonArray;
	}
	protected List<BaseEntity> crawled;
	public abstract void start() throws Exception;
	public List<BaseEntity> getCrawledData(){
		return this.crawled;
	}

}
