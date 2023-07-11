package crawldata.wikibasis;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.Multithreader;
import util.URLMaker;

public class WikiUtility {
	private static String whitespace = "    ";

	public static Map<String, Document> getDocumentsFromPages(List<String> pages, HttpClient client) {
		List<String> htmlurls = URLMaker.getHtmlQueries(pages);
		Map<String, Document> docs = new HashMap<>();

		int index = 0;
		Multithreader multithreader = new Multithreader();
        for (HttpResponse<String> response : multithreader.getResponseFromPages(client, htmlurls)) {
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                // System.out.println(jsonResponse);

                ObjectMapper mapper = new ObjectMapper();

                try {
					JsonNode parse = mapper.readTree(jsonResponse).get("parse");

					if (parse == null) { continue; }

					Document thisdoc = Jsoup.parse(parse.get("text").asText());
					if (thisdoc != null) {
						docs.put(pages.get(index), thisdoc);
					}

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            index += 1;
        }

        return docs;
	}

	/**
	 * Lấy Document từ trang web bất kì của Wikipedia.
	 * @param url Đường dẫn tới trang web đó.
	 * @return Document của trang web.
	 * </br>Trong trường hợp trang web không tồn tại hoặc gặp sự cố về mạng thì trả về null.
	 */
	public static Document getWikiDocumentFromURL(String url) {
		Document document = null;


		// Lấy document của trang web url.
		try {
			document = Jsoup.connect(url).get();
		}
		// Nếu có sự cố thì trả về null
		catch(Exception e) {
			System.out.println("Error url: " + url);
			return null;
		}

		// Lọc qua các ghi chú thừa trong trang web wikipedia
		Elements sups = document.select("sup");
		for (Element sup: sups) sup.remove();

		return document;
	}
	/**
	 * Trả về danh sách các nội dung tiêu đề của 1 bảng Wikipedia.
	 * @param table Bảng của Wikipedia.
	 * @return Danh sách các nội dung tiêu đề.
	 * </br> Trong trường hợp có lỗi xảy ra thì trả về null.
	 */
	public static List<String> getWikiTableHeaders(Element table) {
		if (table == null) return null;

		Elements headers = table.select("table tbody > tr th");
		int numHeaders = headers.size();

		List<String> listHeaderTexts = new ArrayList<>(numHeaders);

		for (Element header: headers) {
			listHeaderTexts.add(header.text());
		}

		return listHeaderTexts;
	}

	/**
	 * Trả về danh sách hai chiều các phần tử của 1 bảng Wikipedia, trừ dòng tiêu đề ra.
	 * @param table Bảng của Wikipedia.
	 * @return Danh sách hai chiều các phần tử.
	 * </br> Trong trường hợp có lỗi xảy ra thì trả về null.
	 */
	public static List<List<Element>> getWikiTableElements(Element table) {
		if (table == null) return null;

		Elements rows = table.select("table tbody > tr");

		int numRows = rows.size() - 1; // Bỏ qua dòng đầu tiên vì dòng này chứa các tiêu đề của bảng.
		int numCols = rows.get(0).select("th").size();

		// Khởi tạo danh sách 2 chiều là giá trị sẽ được trả về.
		List<List<Element>> elements = new ArrayList<>(numRows);
		for (int i = 0; i < numRows; i++) {
			ArrayList<Element> temp = new ArrayList<>(numCols);
			for (int j = 0; j < numCols; j++) temp.add(null);
			elements.add(new ArrayList<>(temp));
		}

		// Tìm các thành phần của bảng để gán cho danh sách.
		boolean[][] mark = new boolean[numRows + 1][numCols];
		for (int i = 1; i <= numRows; i++) {
			Elements currentRowElements = rows.get(i).select("td");

			int k_row = 0;
			for (int j = 0; j < numCols; j++) {
				if (mark[i][j] || (k_row >= currentRowElements.size())) continue;

				Element element = currentRowElements.get(k_row);

				// Multiple row handle
				k_row++;
				String rowspan = element.attr("rowspan");
				if (rowspan.length() == 0) {
					elements.get(i - 1).set(j, element);
				} else {
					int r = Integer.parseInt(rowspan);
					for (int u = 0; u < r; u++) {
						elements.get(i + u - 1).set(j, element);
						mark[i + u][j] = true;
					}
				}

				// Multiple column handle
				String colspan = element.attr("colspan");
				if (colspan.length() != 0) {
					int c = Integer.parseInt(colspan);
					for (int u = 1; u < c; u++) {
						elements.get(i).set(j + u, element);
						mark[i][j + u] = true;
					}
				}
			}
		}

		return elements;
	}

	/**
	 * Định dạng lại innerText của 1 phần tử
	 * @param element
	 * @return Nội dung văn bản của 1 phần tử đã được định dạng
	 */
	public static String getComplexInnerTextOfElement(Element element) {
		if (element == null) return "";

		for (Element brTag: element.select("br")) {
			brTag.before(", ");
		}

		for (Element liTag: element.select("li")) {
			liTag.before(", ");
		}

		return element.text();
	}

	/**
	 * Lấy về các thuộc tính và giá trị thuộc tính tương ứng dưới dạng key-value của một Map
	 * từ "info box" của 1 trang web Wikipedia.
	 * @param url Đường dẫn tới trang web.
	 * @return Một Map chứa các thuộc tính và giá trị thuộc tính tương ứng dưới dạng key-value.
	 * </br> Trong trường hợp trang web không tồn tại hoặc có lỗi hoặc trang web không có "info box" thì trả về Map rỗng.
	 */
	public static Map<String, String> getAdditionalInfoFromWikiInfoBoxDocument(Document document) {
		Map<String, String> additionalInfo = new HashMap<>();

//		Document document = getWikiDocumentFromURL(url);
		if (document == null) return additionalInfo;

		Element infoBox = document.selectFirst(".infobox");
		if (infoBox == null) return additionalInfo;

		Elements trTags = infoBox.select("tbody > tr");
		for (Element trTag: trTags) {
			if (trTag.hasClass("mergedrow")) continue;
			Element tdTag = trTag.selectFirst("td");
			Element thTag = trTag.selectFirst("th");
			Element subTable = trTag.selectFirst("table");

			// Nếu là thẻ đặc biệt thì bỏ qua
			if (tdTag == null || tdTag.text().length() == 0 || thTag == null || thTag.text().length() == 0 || subTable != null) continue;
			Elements brTags = trTag.select("br");
			for (Element brTag: brTags) brTag.before(",");
			additionalInfo.put(thTag.text(), tdTag.text());
		}

		return additionalInfo;
	}

	/**
	 * Lấy bài viết chi tiết về một thực thể từ trang web Wikipedia.
	 * @param url Đường dẫn tới trang web.
	 * @return String.
	 * </br>Trong trường hợp trang web không tồn tại hoặc có lỗi hoặc do load động
	 * mà không có nội dung thì trả về mặc định là "Chưa có thông tin.".
	 */
	public static String getDescriptionFromDocument(Document document) {
		String defaultDescription = "Chưa có thông tin.";

		if (document == null) return defaultDescription;

		Element content = document.selectFirst(".mw-parser-output");
		if (content == null || content.text().length() == 0) return defaultDescription;

		// Kết quả trả về.
		StringBuilder description = new StringBuilder();

		// Toàn bộ các tiêu đề, đoạn văn và các thành phần khác trong bài viết.
		Elements childs = content.children();

		// Dùng để lưu các thẻ không thể hiển thị trong description, để xóa trong tương lai.
		HashSet<Element> notValidTags = new HashSet<>();

		// Duyệt qua các thẻ con để tìm các thẻ không hợp lệ
		for (Element child: childs) {
			String type = child.tagName();
			switch(type) {
				// Hình ảnh đồ họa
				case "figure":
				// Bảng thông tin
				case "table":
					// Nếu là trích đoạn câu nói nhân vật thì hợp lệ
					if (child.hasClass("cquote")) {
						break;
					}
				case "div":
					notValidTags.add(child);
					break;
			}
		}
		// Xóa các thẻ không hợp lệ vừa tìm được
		for (Element child: notValidTags) {
			child.remove();
		}

		// Dùng để đánh chỉ mục các tiêu đề trong bài viết
		int index_h2 = 0;
		int index_h3 = 1;

		// Thêm các nội dung có thể thực sự trình bày
		childs = content.children();
		int nChilds = childs.size();
		for (int i = 0; i < nChilds; i++) {
			Element child = childs.get(i);
			String type = child.tagName();
			String childText = child.text();
			if (childText.length() == 0) continue;

			switch(type) {
				// Đoạn trích hoặc đoạn văn
				case "d1":
				case "p":
					description.append(whitespace).append(childText).append("\n\n");
					break;
				// Bảng thông tin
				case "table":
					// Nếu là trích đoạn câu nói nhân vật
					if (child.hasClass("cquote")) {
						description.append(whitespace).append(whitespace).append(childText).append("\n\n");
					}
					break;
				// Đầu đề lớn
				case "h2":
					if (i == nChilds - 1 || childs.get(i + 1).tagName() == "h2") break;
					index_h2++;
					child = child.selectFirst(".mw-headline");
					description.append(index_h2).append(". ").append(child.text()).append("\n\n");
					index_h3 = 1;
					break;
				// Đầu đề nhỏ
				case "h3":
					child = child.selectFirst(".mw-headline");
					description.append(index_h2).append(".").append(index_h3).append(". ").append(child.text()).append("\n\n");
					index_h3++;
					break;
				// Danh sách
				case "ul":
					// Nếu không phải là danh sách có kiểu đọc đặc biệt
					if (child.classNames().size() == 0) {
						for (Element e: child.select("li")) {
							description.append(whitespace).append(whitespace).append("+ ").append(e.text()).append("\n\n");
						}
					}
					break;
			}
		}

		return description.toString();
	}
}
