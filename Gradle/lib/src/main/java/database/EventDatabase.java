package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import database.constants.PathConstants;
import entity.HistoricalEventWar;
import main.CSVHandler;

public class EventDatabase implements IDatabase<HistoricalEventWar>{
	File fileJson;
	CSVHandler<HistoricalEventWar> csvHandler;

	public EventDatabase() {
		fileJson = new File(PathConstants.pathEvent);
		csvHandler = new CSVHandler<>(HistoricalEventWar.class);
	}

	@Override
	public void store(List<HistoricalEventWar> listObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<HistoricalEventWar> load() {
		// TODO Auto-generated method stub
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<HistoricalEventWar> loadOr(Supplier<List<HistoricalEventWar>> getList) {
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
		List<HistoricalEventWar> eventlist = ed.load();
		System.out.println(eventlist.get(40).toString());
	}
}
