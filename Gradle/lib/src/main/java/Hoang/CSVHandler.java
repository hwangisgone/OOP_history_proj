package Hoang;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.simpleflatmapper.csv.CsvWriter;
import org.simpleflatmapper.lightningcsv.CsvParser;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CSVHandler {
	ObjectMapper mapper;
	
	public CSVHandler() {
		mapper = new ObjectMapper();
	}
	
	public <T> void write(File fileJson, List<T> resultList) {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(fileJson, resultList);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public <T> List<T> load(File fileJson) {
		TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
		List<T> resultList = new ArrayList<>();
		
		try {
			resultList = mapper.readValue(fileJson, typeReference);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

}
