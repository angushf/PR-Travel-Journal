package persistence;

import model.Entry;
import model.EntryList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: source: JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            EntryList entryList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyEntryList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyEntryList.json");
        try {
            EntryList entryList = reader.read();
            assertEquals(0, entryList.getNumItems());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralEntryList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralEntryList.json");
        try {
            EntryList entryList = reader.read();
            List<Entry> journal = entryList.getEntries();
            assertEquals(2, journal.size());
            checkEntry("2023-08-27", "2023-09-06", "Pleasure", "Hawaii", entryList.getEntries().get(0));
            checkEntry("2024-02-01", "2024-02-15", "Business", "Morocco", entryList.getEntries().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
