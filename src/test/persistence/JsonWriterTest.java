package persistence;

import model.Entry;
import model.EntryList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: source: JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            EntryList entryList = new EntryList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:filename.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyEntryList() {
        try {
            EntryList entryList = new EntryList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyEntryList.json");
            writer.open();
            writer.write(entryList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyEntryList.json");
            entryList = reader.read();
            assertEquals(0, entryList.getNumItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralEntryList() {
        try {
            EntryList entryList = new EntryList();
            entryList.addEntry(new Entry("2023-08-27", "2023-09-06", "Hawaii", "Pleasure"));
            entryList.addEntry(new Entry("2024-02-01", "2024-02-15", "Morocco", "Business"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralEntryList.json");
            writer.open();
            writer.write(entryList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralEntryList.json");
            entryList = reader.read();
            System.out.println(entryList.toJson());
            assertEquals(2, entryList.getEntries().size());
            checkEntry("2023-08-27", "2023-09-06", "Pleasure", "Hawaii", entryList.getEntries().get(0));
            checkEntry("2024-02-01", "2024-02-15", "Business", "Morocco", entryList.getEntries().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
