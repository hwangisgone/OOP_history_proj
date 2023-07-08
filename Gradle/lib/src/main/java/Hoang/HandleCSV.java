package Hoang;

import java.io.File;
import java.util.List;

public interface HandleCSV {
	public List<String> readFromCSV(File file);
	public void writeToCSV(File file);
}
