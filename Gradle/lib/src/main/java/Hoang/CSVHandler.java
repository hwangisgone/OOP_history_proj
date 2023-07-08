package Hoang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.simpleflatmapper.csv.CsvWriter;
import org.simpleflatmapper.csv.CsvWriter.DefaultCsvWriterDSL;
import org.simpleflatmapper.lightningcsv.ClosableCsvWriter;
import org.simpleflatmapper.lightningcsv.CsvParser;
import org.simpleflatmapper.util.CheckedConsumer;

public class CSVHandler {
	public CSVHandler() {
	}

	public List<String> readStringFromCSV(File file) {
		List<String> loaded = new ArrayList<>();
		// Remove duplicates
		System.out.println("START READING CSV: " + file.getName());
		try {
			CsvParser.forEach(file, row -> { 
				System.out.println(Arrays.toString(row));
				loaded.add(row[0]);
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("END READING CSV.");
        return loaded;
	}


	private CsvWriter.CsvWriterDSL<String[]> writerDsl = CsvWriter.from(String[].class).columns("a[0]").skipHeaders();
	
	public void writeStringToCSV(File file, List<String> strings) {
		// TODO Auto-generated method stub
        file.setWritable(true);
        file.setReadable(true);
        System.out.println(file.getAbsolutePath());
        System.out.println("START WRITING CSV: " + file.getName());
        
        try (FileWriter fileWriter = new FileWriter(file)) {
            CsvWriter<String[]> writer = writerDsl.to(fileWriter);
            
            strings.forEach(str -> {
				try {
					writer.append(new String[] {str});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} );
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("END WRITING CSV.");
	}
	
	public static void main(String[] args) {
		File file = new File("src/main/resources/test.csv");
		CSVHandler thi = new CSVHandler();
		List<String> gotlist = thi.readStringFromCSV(file);
		System.out.println(gotlist);
		gotlist.add("HAHAHAHAH");
		gotlist.add("HAHAHAHAH2");
		
		thi.writeStringToCSV(file, gotlist);
	}

}
