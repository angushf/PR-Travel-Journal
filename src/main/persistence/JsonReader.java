package persistence;

import model.Entry;
import model.EntryList;

import java.io. IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// NOTE: source: JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads EntryList from file and returns it
    // throws IOException if an error occurs reading data from file
    public EntryList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseEntryList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses EntryList from JSON object and returns it
    private EntryList parseEntryList(JSONObject jsonObject) {
        EntryList entryList = new EntryList();
        addEntries(entryList, jsonObject);
        return entryList;
    }

    // MODIFIES: entryList
    // EFFECTS: parses entries from JSON object and adds them to EntryList
    private void addEntries(EntryList entryList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        int totalEntries = jsonObject.getInt("totalEntries");
        Entry.setTotalEntries(totalEntries);
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(entryList, nextEntry);
        }
    }

    // MODIFIES: entryList
    // EFFECTS: parses entry from JSON object and adds it to EntryList
    private void addEntry(EntryList entryList, JSONObject jsonObject) {
        String departureDate = jsonObject.getString("departure date");
        String returnDate = jsonObject.getString("return date");
        String destination = jsonObject.getString("destination");
        String reason = jsonObject.getString("reason");
        int entryID = jsonObject.getInt("entry ID");


        Entry entry = new Entry(departureDate, returnDate, destination, reason, entryID);
        entryList. addEntry(entry);
    }
}


