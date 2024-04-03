package ui;

import model.Event;
import model.EventLog;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (Event next : EventLog.getInstance()) {
                System.out.println(next.toString() + "\n\n");
            }
        }));
        try {
            new TravelJournalApp();
        } catch (FileNotFoundException e) {
            System.out.print("Unable to run application: file not found");
        }
    }
}
