package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import Hoang.CSVHandler;
import entity.Festival;

public class FestivalDatabase implements IDatabase<Festival> {
	File fileJson;
	CSVHandler<Festival> csvHandler;
	
	public FestivalDatabase() {
		fileJson = new File(PathConstants.pathFestival);
		csvHandler = new CSVHandler<>(Festival.class);
	}
	
	@Override
	public void store(List<Festival> listObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Festival> load() {
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Festival> loadOr(Supplier<List<Festival>> getList) {
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			return getList.get();
		}
	}

	@Override
	public void close() {
	}
}
