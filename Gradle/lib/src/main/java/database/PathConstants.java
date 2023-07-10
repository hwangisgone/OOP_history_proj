package database;

import java.io.File;

public class PathConstants {
	public static final String finalDirectory = "src/main/resources/final/";
	
	public static final String pathDynasty	= finalDirectory + "Dynasty.json";
	public static final String pathCharacter = finalDirectory + "Character.json";
	public static final String pathEvent	= finalDirectory + "Event.json";
	public static final String pathFestival	= finalDirectory + "Festival.json";
	public static final String pathLocation	= finalDirectory + "Location.json";
	
	public static void createRequiredDir() {
		File finalFileDir = new File(finalDirectory);
        if (!finalFileDir.exists()) {
        	finalFileDir.mkdirs();
        }
	}
	
	public static void forceRestart() {
		File finalFileDir = new File(finalDirectory);
        if (finalFileDir.exists()) {
        	for (File f: finalFileDir.listFiles())  f.delete();
        }
	}
}
