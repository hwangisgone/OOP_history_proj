package crawldata.wikibasis.infobox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.Entity;

public abstract class InfoboxExtractor<T extends Entity> {

	protected abstract void startNew(T entity);
	protected abstract void mapKeyVal(Element key, Element val);
	protected abstract void mapFindInInfobox(Element infobox);
	protected abstract boolean mapRow(int index, Element key, Element val);

	protected List<String> splitComma(String text) {
		return Arrays.stream(text.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
	}

	private void remapBr(Element el) {
		Elements brTags = el.select("br");
        for (Element br : brTags) {
//        	br.tagName("span");
//        	br.text("<br/>");
        	br.before(", ");
        }
	}

	public void getInfoFromHtmlFor(Document doc, T entity) throws InfoboxException {
		Element infobox = doc.selectFirst("table.infobox");

		if (infobox == null) {
			throw new InfoboxException("Infobox not found for: " + entity.getID());
		}

		this.remapBr(infobox);
		this.startNew(entity);
		this.mapFindInInfobox(infobox);

		Elements rows = infobox.select("tr");
		for (int i = 0; i < rows.size(); i++) {
			Element key = rows.get(i).selectFirst("th");
			Element val = rows.get(i).selectFirst("td");

			boolean result = this.mapRow(i, key, val);
			if (!result) {
				// Default logic
				if (key != null && val != null ) {
					this.mapKeyVal(key, val);
				} else {
//					System.out.println("Null " + i + ": --"
//							+ (key != null ? key.text() : "") + "--"
//							+ (val != null ? val.text() : "") + "--");
				}
			}
		}
	}
}
