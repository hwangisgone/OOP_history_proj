package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import database.constants.PathConstants;
import entity.Location;
import main.CSVHandler;

public class LocationDatabase implements IDatabase<Location> {
	File fileJson;
	CSVHandler<Location> csvHandler;

	public LocationDatabase() {
		fileJson = new File(PathConstants.pathLocation);
		csvHandler = new CSVHandler<>(Location.class);
	}
	
	public LocationDatabase(String filePath) {
		fileJson = new File(filePath);
		csvHandler = new CSVHandler<>(Location.class);
	}

	@Override
	public void store(List<Location> listObject) {
		csvHandler.write(fileJson, listObject);
	}

	@Override
	public List<Location> load() {
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Location> loadOr(Supplier<List<Location>> getList) {
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
}
