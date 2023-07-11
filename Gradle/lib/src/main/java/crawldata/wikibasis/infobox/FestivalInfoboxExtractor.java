package crawldata.wikibasis.infobox;

import org.jsoup.nodes.Element;

import entity.Festival;

public class FestivalInfoboxExtractor extends InfoboxExtractor<Festival> {
	private Festival festival;

	@Override
	public void startNew(Festival festival) {
		this.festival = festival;
	}

	@Override
	protected void mapFindInInfobox(Element infobox) {
	}

	@Override
	public boolean mapRow(int index, Element key, Element val) {

		return false;
	}

	@Override
	public void mapKeyVal(Element keyEl, Element valEl) {
		String key = keyEl.text().trim();
		String val = valEl.text().trim();

		if (key.isBlank() || val.isBlank()) {
			System.out.println("Empty : " + key + " = " + val);
		}

		switch(key) {
			case "Kiểu":
				festival.setType(val);
				return;
			default:
				break;
		}

		key = key.toLowerCase();

		if (key.contains("ngày")) {
			festival.setDate(val);
		} else if (key.contains("cử hành")) {
			festival.setLocation(val);
		} else {
			System.out.println("Not valid : " + key + " = " + val);
		}
	}




    public static void main(String[] args) {

    }

}
