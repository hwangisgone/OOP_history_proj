package database.HistoricalEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.HistoricalEventWar;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.List;


public class ManageDatabase {
    public static void main(String[] args) {
        //HistoricalEventWar
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("lib/src/main/java/Data/Database/historical-event/extractData.json");

            // Read JSON file and convert it to a List of Person objects
            List<HistoricalEventWar> eventList = objectMapper.readValue(jsonFile, new TypeReference<List<HistoricalEventWar>>() {});

            // Use the personList as needed
            // for (HistoricalEventWar event : eventList) {
            //     System.out.println("Name: " + person.getName() + ", Age: " + person.getAge());
            // }
            System.out.println(eventList.get(10).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
