package persistence;

import model.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkEntry(String departureDate, String returnDate, String reason, String destination, Entry entry) {
        assertEquals(departureDate, entry.getDepartureDate());
        assertEquals(returnDate, entry.getReturnDate());
        assertEquals(reason, entry.getReason());
        assertEquals(destination, entry.getDestination());
    }
}
