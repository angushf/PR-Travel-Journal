package ui;

import model.Entry;
import model.EntryList;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// Travel Journal application
// Some of this code/structure was used from the BankTeller/JsonSerializationDemo app JFYI!
public class TravelJournalApp {
    private static final String JSON_STORE = "./data/journal.json";
    private EntryList journal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    public TravelJournalApp() throws FileNotFoundException {
        runTravelJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTravelJournal() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addEntry();
        } else if (command.equals("d")) {
            deleteEntry();
        } else if (command.equals("e")) {
            editEntry();
        } else if (command.equals("v")) {
            viewEntries();
        } else if (command.equals("s")) {
            saveJournal();
        } else if (command.equals("l")) {
            loadJournal();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: saves the journal to file
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved travel journal to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded travel journal from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out all entries in the journal
    public void viewEntries() {
        System.out.println(journal.printEntries());
    }

    // MODIFIES: this
    // EFFECTS: edits an entry from the journal
    public void editEntry() {
        System.out.println("Enter the Entry ID:");
        int entryIDToEdit = input.nextInt();

        System.out.println("New Departure Date (YYYY-MMM-DD): ");
        String editedDepartureDate = input.next();

        System.out.println("New Return Date (YYYY-MM-DD): ");
        String editedReturnDate = input.next();

        System.out.println("New Destination: ");
        String newDestination = input.next();

        System.out.println("New Reason: ");
        String editedReason = input.next();

        journal.editEntry(entryIDToEdit, editedDepartureDate, editedReturnDate, newDestination, editedReason);
    }

    // MODIFIES: this
    // EFFECTS: deletes an entry from the journal
    public void deleteEntry() {
        System.out.println("Enter the Entry ID: ");
        int entryIDToDelete = input.nextInt();
        Entry entryToDelete = journal.retrieveEntryByID(entryIDToDelete);
        System.out.println("Are you sure you want to delete " + entryToDelete.getEntryID() + "?\n");
        System.out.println(entryToDelete.printEntry());
        System.out.println("y/n?");
        String userFinalDecision = input.next();

        if (userFinalDecision.equals("y")) {
            journal.deleteEntry(entryIDToDelete);
        }
    }

    // MODIFIES: this
    // EFFECTS: add an entry to the journal
    public void addEntry() {
        System.out.println("Enter Departure Date (YYYY-MM-DD): ");
        String departureDate = input.next();

        System.out.println("Enter Return Date (YYYY-MM-DD): ");
        String returnDate = input.next();

        System.out.println("Enter Destination: ");
        String destination = input.next();

        System.out.println("Enter Reason: ");
        String reason = input.next();

        Entry userEntry = new Entry(departureDate, returnDate, destination, reason);
        journal.addEntry(userEntry);
        System.out.println("\n Added: " + userEntry.printEntry());
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add travel entry");
        System.out.println("\td -> Delete travel entry");
        System.out.println("\te -> Edit travel entry");
        System.out.println("\tv -> View travel entries");
        System.out.println("\ts -> Save journal to file");
        System.out.println("\tl -> Load journal from file");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes EntryList
    private void init() {
        journal = new EntryList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }
}
