package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.constants.PathConstants;
import entity.Dynasty;
import entity.HistoricalEvent;
import main.CSVHandler;

public class EventDatabase implements IDatabase<HistoricalEvent>{
	File fileJson;
	CSVHandler<HistoricalEvent> csvHandler;

	public EventDatabase() {
		fileJson = new File("lib/src/main/resources/event_data.json");
		csvHandler = new CSVHandler<>(HistoricalEvent.class);
	}

	@Override
	public void store(List<HistoricalEvent> listObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<HistoricalEvent> load() {
		// TODO Auto-generated method stub
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<HistoricalEvent> loadOr(Supplier<List<HistoricalEvent>> getList) {
		// TODO Auto-generated method stub
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			return getList.get();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
    
	public static void main(String[] args){
		EventDatabase ed = new EventDatabase();
		List<HistoricalEvent> eventlist = ed.load();
		System.out.println(eventlist.get(40).toString());
	}
}
