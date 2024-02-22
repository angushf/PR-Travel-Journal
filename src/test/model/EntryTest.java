package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryTest {
    private Entry testEntry;

    @BeforeEach
    void setup() {
        testEntry = new Entry("2024-02-04", "2024-02-10", "Mexico", "Leisure");
    }

    @Test
    public void testConstructor() {
        Entry.setTotalEntries(0);
        Entry testEntry2 = new Entry("2022-01-15", "2022-02-01", "London", "Business");
        assertEquals(1, testEntry2.getEntryID());
        assertEquals("2022-01-15", testEntry2.getDepartureDate());
        assertEquals("2022-02-01", testEntry2.getReturnDate());
        assertEquals("London", testEntry2.getDestination());
        assertEquals("Business", testEntry2.getReason());
        assertEquals(1, Entry.getTotalEntries());
    }

    @Test
    void testPrintEntry() {
        String expectedString = "ID: " + testEntry.getEntryID() + "\n"
                                + " Departure Date: " + testEntry.getDepartureDate() + "\n"
                                + " Return Date: " + testEntry.getReturnDate() + "\n"
                                + " Destination: " + testEntry.getDestination() + "\n"
                                + " Reason: "      + testEntry.getReason() + "\n";
        assertEquals(expectedString, testEntry.printEntry());
    }

    @Test
    void testPrintSecondEntry() {
        assertEquals(1, testEntry.getEntryID());
        Entry secondTestEntry = new Entry("2024-03-15", "2024-03-25", "USA", "Work");
        String expectedString = "ID: " + secondTestEntry.getEntryID() + "\n"
                                + " Departure Date: " + secondTestEntry.getDepartureDate() + "\n"
                                + " Return Date: " + secondTestEntry.getReturnDate() + "\n"
                                + " Destination: " + secondTestEntry.getDestination() + "\n"
                                + " Reason: "      + secondTestEntry.getReason() + "\n";
        assertEquals(expectedString, secondTestEntry.printEntry());
        assertEquals(2, secondTestEntry.getEntryID());
    }

}