package Hoang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextHandler {

    public void writeListToFile(File file, List<String> stringList) {
    	System.out.println("START WRITING TXT: " + file.getName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String str : stringList) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
        System.out.println("END WRITING TXT.");
    }

    public List<String> readListFromFile(File file) {
        List<String> readStringList = new ArrayList<>();
        System.out.println("START READING TXT: " + file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                readStringList.add(line);
            }
            System.out.println("List of strings successfully read from the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        System.out.println("END READING TXT.");
        return readStringList;
    }

    // Example usage
    public static void main(String[] args) {
    }
}