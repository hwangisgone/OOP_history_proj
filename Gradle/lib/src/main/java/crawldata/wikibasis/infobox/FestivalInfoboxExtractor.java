package crawldata.wikibasis.infobox;

import java.net.http.HttpClient;

import org.jsoup.nodes.Element;

import entity.Festival;

public class FestivalInfoboxExtractor extends InfoboxExtractor<Festival> {

    public FestivalInfoboxExtractor(HttpClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	private Festival festival;

	@Override
	public void startNew(String name) {
		festival = new Festival();
		festival.setName(name);
	}

	@Override
	public Festival endNew() {
		return festival;
	}


	@Override
	protected void mapFindInInfobox(Element infobox) {
//		Element toFind;
//
//		toFind = infobox.selectFirst("td:containsOwn(–)");
//		if (toFind != null) {
//			System.err.println(toFind.text());
//			String[] period = toFind.text().split("–");
//			if (period.length == 2) {
//				festival.setYearStart(period[0]);
//				festival.setYearEnd(period[1]);
//			}
//			return;
//		}
	}

	@Override
	public boolean mapRow(int index, Element key, Element val) {
//		if (key != null) {
//			switch(index) {
//				case 0:
//					festival.setLongName(this.splitComma(key.text()));
//					return true;
//			}
//		} else {
//			// Null header
//			switch(index) {
//				case 1:
//					festival.setNativeName(
//						this.splitComma(val.select("ul").text())
//					);
//					return true;
//			}
//		}

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
