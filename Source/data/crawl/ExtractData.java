/* 
	* ExtractData is an abstract class, providing tools for extract data from urls. 
	It's neccessay to implement the abstract method: `extract`, which extracts specified data given an url
 */

package data.crawl;

// used classes
	// jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
	// net
import java.net.URL;
	// file
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
	// file:jason
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
	// containers
import java.util.Map;
import java.util.HashMap;
import java.util.List;

	

public abstract class ExtractData {
	

	/* to extract data given an url, return an Map of field name and string value */
	public abstract Map<String, String> extract(String url);


	/* 	* to extract data from list of urls and stored in json file 
		* Parameter
			- listUrl: list of links to be extracted
			- filePath: a json file path, where the extract data will be stored */
	public final void extract(List<String> listUrl, String filePath) {
		try {
			// open file
			OutputStream fos = new FileOutputStream(filePath);
			JsonWriter writer = Json.createWriter(fos);
			JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
			// write each instance data to file
			for (String url: listUrl) {
				Map<String, String> instance = extract(url);
				JsonObjectBuilder builder = Json.createObjectBuilder();
				// add each field to builder
				for (Map.Entry<String, String> entry: instance.entrySet()) 
					builder.add(entry.getKey(), entry.getValue());
				JsonObject object = builder.build();
				arrayBuilder.add(object);
			}	// close for
			JsonArray data = arrayBuilder.build();
			writer.writeArray(data);
			writer.close();
			System.out.println("SUCCESFULLY WROTE " + data.size() + " objects!");
		} catch (IOException e) {
			System.out.println("Unable to write to file: " + filePath);
			System.out.println("Error: " + e.getMessage());
		}	// close extract
	}	// close extract
}	// close ExtractData