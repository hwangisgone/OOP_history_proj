package crawldata.wikibasis.infobox;

import java.net.http.HttpClient;
import java.util.ArrayList;
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
					dynasty.setLongName(this.splitComma(key.text()));
					return true;
			}
		} else {
			// Null header
			switch(index) {
				case 1:
					dynasty.setNativeName(
						this.splitComma(val.select("ul").text())
					);
					return true;
			}
		}

		return false;
	}


    private List<String> splitCapitals(String input) {
        List<String> result = new ArrayList<>();
        String[] parts = input.split(",");

        StringBuilder currentElement = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
        	String trimmedPart = parts[i].trim();
            if (trimmedPart.startsWith("(")) {
                currentElement.append(" ").append(trimmedPart);
            } else {
                result.add(currentElement.toString());
                currentElement = new StringBuilder(trimmedPart);
            }
        }
        result.add(currentElement.toString());

        return result;
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
				dynasty.setLanguage(val);
				break;
			case "Thủ đô":
				dynasty.setCapitals(splitCapitals(val));
				break;
			case "Tôn giáo chính":
				dynasty.setReligion(val);
				break;
			case "Vị thế":
				dynasty.setStatus(val);
				break;
			case "Đơn vị tiền tệ":
				dynasty.setCurrency(val);
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
