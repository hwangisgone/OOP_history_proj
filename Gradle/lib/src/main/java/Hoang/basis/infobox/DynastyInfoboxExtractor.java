package Hoang.basis.infobox;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;

import entity.Dynasty;

public class DynastyInfoboxExtractor extends InfoboxExtractor<Dynasty> {

    public DynastyInfoboxExtractor(HttpClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	private Dynasty dynasty;

	@Override
	public void startNew(String name) {
		dynasty = new Dynasty();
		dynasty.setName(name);
	}

	@Override
	public Dynasty endNew() {
		return dynasty;
	}


	@Override
	protected void mapFindInInfobox(Element infobox) {
		Element toFind;
		
		toFind = infobox.selectFirst("td:containsOwn(–)");
		if (toFind != null) {
			System.err.println(toFind.text());
			String[] period = toFind.text().split("–");
			if (period.length == 2) { 
				dynasty.setYearStart(period[0]);
				dynasty.setYearEnd(period[1]);
			}	
			return;
		}
	}
	
	@Override
	public boolean mapRow(int index, Element key, Element val) {
		if (key != null) {
			switch(index) {
				case 0:
					dynasty.setLongName(this.separateBr(key.text()));
					return true;
			}
		} else {
			// Null header
			switch(index) {
				case 1:
					dynasty.setNativeName(
						this.separateBr(val.select("ul").text())
					);
					return true;
			}
		}

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
			case "Chính phủ":
				dynasty.setGovernmentType(val);
				break;
			case "Ngôn ngữ thông dụng":
				dynasty.setLanguage(this.toCommaBr(val));
				break;
			case "Thủ đô":
				dynasty.setCapitals(this.separateBr(val));
				break;
			case "Tôn giáo chính":
				dynasty.setReligion(this.toCommaBr(val));
				break;
			case "Vị thế":
				dynasty.setStatus(this.toCommaBr(val));
				break;
			case "Đơn vị tiền tệ":
				dynasty.setCurrency(this.toCommaBr(val));
				break;
			default:
				System.out.println("Not valid : " + key + " = " + val);
				break;
		}
	}




    public static void main(String[] args) {
    	HttpClient client = HttpClient.newHttpClient();
    	InfoboxExtractor<Dynasty> ext = new DynastyInfoboxExtractor(client);

    	List<Dynasty> maps = ext.getInfoboxContents(Arrays.asList("Thời kỳ Bắc thuộc lần thứ ba", "Nhà Tiền Lý", "Nhà Hậu Trần"));

    	// Sort the map by key

    	maps.forEach(map -> {
    		System.out.println(map.toString());

    	});
    }

}
