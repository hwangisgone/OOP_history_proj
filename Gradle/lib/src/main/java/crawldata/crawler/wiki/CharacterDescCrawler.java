package crawldata.crawler.wiki;

import java.net.http.HttpClient;
import java.util.List;

import crawldata.crawler.ICrawler;
import crawldata.wikibasis.WikiUtility;
import database.CharacterDatabase;
import entity.Character;

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
