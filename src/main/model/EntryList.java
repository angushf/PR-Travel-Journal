package model;

import java.util.ArrayList;

// Represents a Journal that stores Entries of absences from
// the country. When instantiated, EntryList will create an empty List
// that will store all of its entries.
public class EntryList {
    private ArrayList<Entry> journal;

    public EntryList() {
        journal = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds entry object to journal
    public void addEntry(Entry entry) {
        journal.add(entry);
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

}
