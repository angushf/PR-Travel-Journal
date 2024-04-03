package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a Journal that stores Entries of absences from
// the country. When instantiated, EntryList will create an empty List
// that will store all of its entries.
public class EntryList implements Writable {
    private ArrayList<Entry> journal;

    public EntryList() {
        journal = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds entry object to journal
    public void addEntry(Entry entry) {
        journal.add(entry);
        EventLog.getInstance().logEvent(new Event("Entry added to travel journal"));
    }

    // EFFECTS: returns a list of entries in journal
    public List<Entry> getEntries() {
        EventLog.getInstance().logEvent(new Event("Journal entries viewed"));
        return this.journal;
    }

    // EFFECTS: returns all entries in journal
    public String printEntries() {
        String entries = "";
        for (Entry entry : journal) {
            entries = entries + entry.printEntry();
        }
        return entries;
    }

    // REQUIRES: an entryID > 0 and one that exists
    // MODIFIES: this
    // EFFECTS: removes entry with given entryID from journal
    public void deleteEntry(int entryID) {
        Entry e = retrieveEntryByID(entryID);
        journal.remove(e);
        Entry.setTotalEntries(Entry.getTotalEntries() - 1);

        for (int i = entryID - 1; i < journal.size(); i++) {
            int oldEntryID = journal.get(i).getEntryID();
            journal.get(i).setEntryID(oldEntryID - 1);
        }
        EventLog.getInstance().logEvent(new Event("Entry ID #" + entryID + " deleted"));
    }

    // REQUIRES: an entryID > 0 and one that exists
    // MODIFIES: this
    // EFFECTS: edits entry with given entryID from journal
    public void editEntry(int entryID, String dep, String ret, String dest, String reason) {
        Entry e = retrieveEntryByID(entryID);
        e.setDepartureDate(dep);
        e.setReturnDate(ret);
        e.setDestination(dest);
        e.setReason(reason);
        EventLog.getInstance().logEvent(new Event("Entry ID #" + entryID + " modified"));
    }

    // EFFECTS: returns number of entries in journal
    public int getNumItems() {
        return journal.size();
    }

    // REQUIRES: entry with given id number must exist in journal
    // EFFECTS: return entry that matches id number
    public Entry retrieveEntryByID(int entryID) {
        for (Entry e : journal) {
            if (e.getEntryID() == entryID) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: constructs a new JSONObject and populates it with key-value pairs
    // NOTE: source: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("totalEntries", Entry.getTotalEntries());
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECTS: returns entries in this travel journal as a JSON array
    // NOTE: source: JsonSerializationDemo
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entry e : journal) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}

