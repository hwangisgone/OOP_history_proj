package Hoang;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Hoang.entity.Dynasty;

public class JSONHandler<T> {
	private final Type listType;
	private final Gson gson;
	
	public JSONHandler() {
        this.listType = new TypeToken<List<T>>(){}.getType();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
	
    public List<T> readListFromFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Return empty list on error
    }
    
    public void writeListToFile(List<T> list, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
