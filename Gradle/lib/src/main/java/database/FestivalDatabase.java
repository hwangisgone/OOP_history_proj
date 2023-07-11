package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import database.constants.PathConstants;
import database.handler.CSVHandler;
import entity.Festival;

public class FestivalDatabase implements IDatabase<Festival> {
	private File fileJson;
	private CSVHandler<Festival> jacksonHandler;

	public FestivalDatabase() {
		fileJson = new File(PathConstants.pathFestival);
		jacksonHandler = new CSVHandler<>(Festival.class);
	}

	@Override
	public void store(List<Festival> listObject) {
		jacksonHandler.write(fileJson, listObject);
	}

	@Override
	public List<Festival> load() {
		if (fileJson.exists()) {
			return jacksonHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Festival> loadOr(Supplier<List<Festival>> getList) {
		if (fileJson.exists()) {
			return jacksonHandler.load(fileJson);
		} else {
			return getList.get();
		}
	}

	@Override
	public void close() {
	}
}
