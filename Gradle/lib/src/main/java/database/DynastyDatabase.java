package database;

import java.io.File;
import java.util.List;
import java.util.function.Supplier;

import entity.Dynasty;
import main.CSVHandler;

public class DynastyDatabase implements IDatabase<Dynasty> {
	File fileJson;
	CSVHandler<Dynasty> csvHandler;
	
	public DynastyDatabase() {
		fileJson = new File(PathConstants.pathDynasty);
		csvHandler = new CSVHandler<>(Dynasty.class);
	}
	
	@Override
	public void store(List<Dynasty> listObject) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Dynasty> load() {
		if (fileJson.exists()) {
			return csvHandler.load(fileJson);
		} else {
			System.err.println("File not found for: " + fileJson.getName());
			return null;
		}
	}

	@Override
	public List<Dynasty> loadOr(Supplier<List<Dynasty>> getList) {
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
