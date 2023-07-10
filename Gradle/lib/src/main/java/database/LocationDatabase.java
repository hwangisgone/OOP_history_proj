package database;

import java.io.File;
import java.util.List;

import Hoang.CSVHandler;
import Source.data.database.IDatabase;
import entity.Location;

public class LocationDatabase implements IDatabase<Location> {

	@Override
	public void store(List<Location> listObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Location> load(int startIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> load() {
		File fileJson = new File(PathConstants.pathLocation);
		
		if (fileJson.exists()) {
			CSVHandler<Location> csvHandler = new CSVHandler<>(Location.class);
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
