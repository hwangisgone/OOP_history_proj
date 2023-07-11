package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import database.constants.PathConstants;
import database.handler.CSVHandler;
import entity.Dynasty;

public class DynastyDatabase implements IDatabase<Dynasty> {
	private File fileJson;
	private CSVHandler<Dynasty> jacksonHandler;

	public DynastyDatabase() {
		fileJson = new File(PathConstants.pathDynasty);
		jacksonHandler = new CSVHandler<>(Dynasty.class);
	}

	@Override
	public void store(List<Dynasty> listObject) {
		jacksonHandler.write(fileJson, listObject);
	}

	@Override
	public List<Dynasty> load() {
		if (fileJson.exists()) {
			return jacksonHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Dynasty> loadOr(Supplier<List<Dynasty>> getList) {
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
