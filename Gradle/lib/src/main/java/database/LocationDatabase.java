package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import database.constants.PathConstants;
import database.handler.CSVHandler;
import entity.Location;

public class LocationDatabase implements IDatabase<Location> {
	private File fileJson;
	private CSVHandler<Location> jacksonHandler;

	public LocationDatabase() {
		fileJson = new File(PathConstants.pathLocation);
		jacksonHandler = new CSVHandler<>(Location.class);
	}
	
	public LocationDatabase(String filePath) {
		fileJson = new File(filePath);
		jacksonHandler = new CSVHandler<>(Location.class);
	}

	@Override
	public void store(List<Location> listObject) {
		jacksonHandler.write(fileJson, listObject);
	}

	@Override
	public List<Location> load() {
		if (fileJson.exists()) {
			return jacksonHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Location> loadOr(Supplier<List<Location>> getList) {
		if (fileJson.exists()) {
			return jacksonHandler.load(fileJson);
		} else {
			return getList.get();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
}
