package crawldata.crawler.wiki;

import java.net.http.HttpClient;
import java.util.List;

import database.CharacterDatabase;
import database.IDatabase;
import database.LocationDatabase;
import database.constants.PathConstants;

import crawldata.crawler.ICrawler;
import crawldata.crawler.nonwiki.DiTichLocationCrawler;
import crawldata.wikibasis.WikiUtility;
import entity.Character;
import entity.Location;

public class CharacterDescCrawler implements ICrawler<Character> {
	private HttpClient client;

	public CharacterDescCrawler(HttpClient client) {
		this.client = client;
	}
	
	@Override
	public List<Character> crawl() {
		List<Character> listCharacter = CharacterDatabase.getListCharacter();
		
		WikiUtility.getDescriptionsFor(listCharacter, client);
		
		return listCharacter;
	}

}
