/* 
	DataControler: to implement the data modular:
		(1) Search list of link for specified data -> listUrl
		(2) From listUrl, extract the data and store it into database.json
 */

package data.crawl;

// used classes
import java.util.List;						// Container
import data.crawl.SearchLink;				// self
import data.crawl.ExtractData;
import data.crawl.SearchLinkCharacter;
import data.crawl.ExtractHistoricalCharacter;
import javax.json.*;
import java.io.*;


public class DataControler {

	private SearchLink seacher;			// seacher for specific data
	private ExtractData extractor;		// extractor for specific data

	public DataControler(SearchLink seacher, ExtractData extractor) {
		this.seacher = seacher;
		this.extractor = extractor;
	}	// close constructor


	/* 
		* To crawl data
		* Parameter:
			(1) urlSeed: link where searching starts off
			(2) level: Maximum number of searching levels
			(3) size: maximum number of links will be found
			(4) databaseFile: a json file where data is stored
	 */
	public void crawlData(String urlSeed, int level, int size, String databaseFile) {
		List<String> listUrl = seacher.getListUrl(urlSeed, level, size);
		extractor.extract(listUrl, databaseFile);
	}	// close crawlData


	/* 	
		- To provide a readble format of database
		- Detail:
			+ read list of objects in database
			+ the attributes of the objects are stored in attributeFile
			+ the list of objects is rewritten into new file in a readable format
	 */
	public static void reWriteData(String databaseFile, String readableFile, String attributeFile) {
		try {
			// Read data
			InputStream fis = new FileInputStream(databaseFile);
			JsonReader reader = Json.createReader(fis);
			JsonArray listObject = reader.readArray();
			// Read attributes list
			fis = new FileInputStream(attributeFile);
			reader = Json.createReader(fis);
			JsonArray listAttri = reader.readArray();
			// Write each object in the array into readable file
			FileWriter writer = new FileWriter(readableFile);
			for (int i = 0; i < listObject.size(); i ++ ) {
				if (listObject.isNull(i))
					continue;
				String contentObj = "\t----- #" + i + " -----\n";
				JsonObject object = listObject.getJsonObject(i);
				for (int j = 0; j < listAttri.size(); j ++ ) {
					String attri = listAttri.getString(j);
					String value = object.getString(attri, "null");
					contentObj += attri + ": " + value + "\n";
				}	// close for 1
				contentObj += "\t----------\n";
				writer.write(contentObj);
			}	// close for
			writer.flush();
			writer.close();
			System.out.println("SUCCESSFULLY REWRITTEN DATABASE!");
		} catch (IOException e) {
			System.out.println("Unable to rewriten the database into readable format\n");
			System.out.println(e.getMessage());
		}	// close try
	}	// close reWriteData

	
	public static void main(String[] args) {
		// setup
		SearchLinkCharacter seacher = new SearchLinkCharacter();
		ExtractHistoricalCharacter extractor = new ExtractHistoricalCharacter();
		DataControler controler = new DataControler(seacher, extractor);
		// parameter
		String urlSeed = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
		int level = 2;
		int size = 100;
		String databaseFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/HistoricalCharacter/HC1.json";
		// crawl
		controler.crawlData(urlSeed, level, size, databaseFile);
		// rewrite
		String readableFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/Database/Readable/HC1.readable";
		String attributeFile = "/home/minh/School/Learning/20222/OOP/ProjectOOP20222/Data/DataConfigure/attributeHistoricalCharacter.json";
		DataControler.reWriteData(databaseFile, readableFile, attributeFile);
	}	// close main
}	// close DataControler