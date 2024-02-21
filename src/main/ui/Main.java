package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new TravelJournalApp();
        } catch (FileNotFoundException e) {
            System.out.print("Unable to run application: file not found");
        }

    }
}
