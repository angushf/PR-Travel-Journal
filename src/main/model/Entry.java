package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a single entry in a Travel Journal.
// When a new entry is instantiated, it is given a
// departure date, return date, destination, reason, and an entry ID.
// NOTE: it will also increment the totalEntries counter in the class.
public class Entry implements Writable {
    private String departureDate;
    private String returnDate;
    private String destination;
    private String reason;
    static int totalEntries = 0;
    private int entryID = totalEntries;

    public Entry(String depDate, String retDate, String des, String reason) {
        this.departureDate = depDate;
        this.returnDate = retDate;
        this.destination = des;
        this.reason = reason;
        this.totalEntries++;
        this.entryID += 1;
    }

    // getters & setters

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String depDate) {
        this.departureDate = depDate;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public void setReturnDate(String retDate) {
        this.returnDate = retDate;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String des) {
        this.destination = des;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getEntryID() {
        return this.entryID;
    }

    // EFFECTS: returns a printed & formatted entry row
    public String printEntry() {
        return "ID: " + this.getEntryID() + "\n"
                + " Departure Date: " + this.getDepartureDate() + "\n"
                + " Return Date: " + this.getReturnDate() + "\n"
                + " Destination: " + this.getDestination() + "\n"
                + " Reason: "      + this.getReason() + "\n";
    }

    // NOTE: source: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("departure date", this.departureDate);
        json.put("return date", this.returnDate);
        json.put("destination", this.destination);
        json.put("reason", this.reason);
        //json.put("entry ID", entryID);
        return json;
    }
}
