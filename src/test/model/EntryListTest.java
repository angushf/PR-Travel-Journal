package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EntryListTest {
    private EntryList testEntryList;
    private Entry entry1;
    private Entry entry2;
    private Entry entry3;

    @BeforeEach
    public void setup() {
        testEntryList = new EntryList();
        // Reset Total Entries counter back to 0, otherwise tests fail
        Entry.setTotalEntries(0);
        entry1 = new Entry("2023-02-12", "2023-02-24", "Argentina", "Pleasure");
        entry2 = new Entry("2023-06-01", "2023-07-01", "Germany", "Pleasure");
        entry3 = new Entry("2024-01-08", "2024-01-16", "San Francisco", "Business");
    }

    // TESTS FOR EntryList CONSTRUCTOR

    @Test
    public void testConstructor() {
        assertEquals(0, testEntryList.getNumItems());
    }

    // TESTS FOR addEntry() METHOD

    @Test
    public void testAddEntryOnce() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        System.out.println(entry1.getEntryID());
        assertEquals(entry1, testEntryList.retrieveEntryByID(1));
        assertEquals(1, testEntryList.getNumItems());
    }

    @Test
    public void testAddMultipleEntries() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        assertEquals(entry1, testEntryList.retrieveEntryByID(1));
        assertEquals(1, testEntryList.getNumItems());

        testEntryList.addEntry(entry2);
        assertEquals(entry2, testEntryList.retrieveEntryByID(2));
        assertEquals(2, testEntryList.getNumItems());
    }

    // TESTS FOR deleteEntry() METHOD

    @Test
    public void testDeleteOneEntry() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        assertEquals(entry1, testEntryList.retrieveEntryByID(1));
        testEntryList.deleteEntry(1);
        assertNull(testEntryList.retrieveEntryByID(1));
        assertEquals(0, testEntryList.getNumItems());
    }

    @Test
    public void testDeleteMultipleEntries() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        testEntryList.addEntry(entry2);
        testEntryList.addEntry(entry3);
        assertEquals(3, testEntryList.getNumItems());
        testEntryList.deleteEntry(3);
        assertEquals(2, testEntryList.getNumItems());
        assertEquals(entry1, testEntryList.retrieveEntryByID(1));
        assertEquals(entry2, testEntryList.retrieveEntryByID(2));
        testEntryList.deleteEntry(1);
        assertEquals(1, testEntryList.getNumItems());
        assertEquals(entry2, testEntryList.retrieveEntryByID(2));
    }

    // TESTS FOR retrieveEntryByID() METHOD

    @Test
    public void testRetrieveEntryByID() {
        testEntryList.addEntry(entry1);
        testEntryList.addEntry(entry2);
        testEntryList.addEntry(entry3);
        assertEquals(entry3, testEntryList.retrieveEntryByID(3));
    }

    // TESTS FOR editEntry() METHOD

    @Test
    public void testEditEntry() {
        testEntryList.addEntry(entry1);
        testEntryList.editEntry(1, entry2.getDepartureDate(), entry2.getReturnDate(), entry2.getDestination(), entry2.getReason());
        assertEquals(entry2.getDepartureDate(), entry1.getDepartureDate());
        assertEquals(entry2.getReturnDate(), entry1.getReturnDate());
        assertEquals(entry2.getDestination(), entry1.getDestination());
        assertEquals(entry2.getReason(), entry1.getReason());
        assertEquals(1, entry1.getEntryID());
    }

    // TESTS FOR printEntries() METHOD

    @Test
    public void testPrintOneEntry() {
        testEntryList.addEntry(entry1);
        assertEquals(entry1.printEntry(), testEntryList.printEntries());
    }

    @Test
    public void testPrintMultipleEntries() {
        testEntryList.addEntry(entry1);
        testEntryList.addEntry(entry2);
        assertEquals(entry1.printEntry() + entry2.printEntry(), testEntryList.printEntries());
    }

    // TESTS for getNumItems() METHOD

    @Test
    public void getNumItemsSizeOne() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        assertEquals(1, testEntryList.getNumItems());
    }

    @Test
    public void getNumItemsSizeMultiple() {
        assertEquals(0, testEntryList.getNumItems());
        testEntryList.addEntry(entry1);
        testEntryList.addEntry(entry2);
        assertEquals(2, testEntryList.getNumItems());
    }
}
