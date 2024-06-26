package ui;

import model.Entry;
import model.EntryList;

import java.awt.*;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.Vector;

// Travel Journal application
// Some of this code/structure was used from the BankTeller/JsonSerializationDemo app JFYI!
public class TravelJournalApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/journal.json";
    private EntryList journal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame inputWindow;

    private JLabel depDate;
    private JTextField textDepDate;
    private JLabel retDate;
    private JTextField textRetDate;
    private JLabel dest;
    private JTextField textDest;
    private JLabel reason;
    private JTextField textReason;
    private JLabel inputID;
    private JTextField textInputID;
    private JPanel buttonPanel;


    public TravelJournalApp() throws FileNotFoundException {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setPreferredSize(new Dimension(400, 334));
        setVisible(true);
        setResizable(false);

        ImageIcon imageIcon = new ImageIcon("data/Travel log.png");
        JLabel labelWithImage = new JLabel(imageIcon);
        labelWithImage.setBounds(0, 0, 400, 250);

        initButtonPanel();
        createButtonPanelButtons();
        initMenuBar();
        add(labelWithImage);
        add(buttonPanel);
        pack();
        centreOnScreen();
        runTravelJournal();
    }

    // REQUIRES: buttonPanel must be instantiated
    // MODIFIES: buttonPanel
    // EFFECTS: creates and adds four 'JButton' components to the buttonPanel
    //          each button is configured with an action command string
    private void createButtonPanelButtons() {
        JButton viewEntriesButton = new JButton("View Entries");
        viewEntriesButton.setActionCommand("viewEntries");
        viewEntriesButton.addActionListener(this);
        viewEntriesButton.setFocusable(false);
        buttonPanel.add(viewEntriesButton);

        JButton addEntryButton = new JButton("Add Entry");
        addEntryButton.setActionCommand("addEntry");
        addEntryButton.addActionListener(this);
        addEntryButton.setFocusable(false);
        buttonPanel.add(addEntryButton);

        JButton deleteEntryButton = new JButton("Delete Entry");
        deleteEntryButton.setActionCommand("deleteEntry");
        deleteEntryButton.addActionListener(this);
        deleteEntryButton.setFocusable(false);
        buttonPanel.add(deleteEntryButton);

        JButton editEntryButton = new JButton("Edit Entry");
        editEntryButton.setActionCommand("editEntry");
        editEntryButton.addActionListener(this);
        editEntryButton.setFocusable(false);
        buttonPanel.add(editEntryButton);
    }

    // MODIFIES: buttonPanel
    // EFFECTS: initializes buttonPanel by setting it up with
    //          specific dimensions, a background color, and a flow
    //          layout to the left.
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBounds(0,250, 400, 50);
        buttonPanel.setBackground(new Color(61, 66, 64));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

    // MODIFIES: menuBar
    // EFFECTS: Creates and initializes the menuBar with a new JMenuBar.
    //          Adds Load, Save, and Exit items.
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setActionCommand("loadFile");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setActionCommand("saveFile");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setActionCommand("exitApplication");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    // REQUIRES: ActionEvent 'e' != null and should contain one of the below commands
    // MODIFIES: modifies various components based on certain commands
    // EFFECTS: interprets the action command from the provided event and triggers corresponding
    //          helper method. Actions range from viewing entries to adding new entries to saving files,
    //          and exiting the application.
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("viewEntries")) {
            viewEntriesProcess();
        } else if (e.getActionCommand().equals("addEntry")) {
            addEntryProcess();
        } else if (e.getActionCommand().equals("loadFile")) {
            loadJournal();
        } else if (e.getActionCommand().equals("saveFile")) {
            saveJournal();
        } else if (e.getActionCommand().equals("exitApplication")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("add")) {
            triggerAddButtonClickProcess();
        } else if (e.getActionCommand().equals("edit")) {
            triggerEditButtonClickProcess();
        } else if (e.getActionCommand().equals("editEntry")) {
            editEntryProcess();
        } else if (e.getActionCommand().equals("deleteEntry")) {
            deleteEntryProcess();
        } else if (e.getActionCommand().equals("delete")) {
            triggerDeleteButtonClickProcess();
        }
    }

    // REQUIRES: textInputID exists
    // MODIFIES: journal and inputWindow
    // EFFECTS: deletes entry from journal and closes input window
    private void triggerDeleteButtonClickProcess() {
        journal.deleteEntry(Integer.parseInt(textInputID.getText()));
        inputWindow.dispose();
    }

    // REQUIRES: delete entry button pressed
    // EFFECTS: creates instance of input window allowing user to delete entry
    private void deleteEntryProcess() {
        makeInputWindow();
        makeInputIDLabelAndJTextField(10, 10);
        makeDeleteButton();
    }

    // MODIFIES: inputWindow & delButton
    // EFFECTS: creates delete button and adds it to inputWindow
    private void makeDeleteButton() {
        JButton delButton = new JButton("Delete");
        delButton.setActionCommand("delete");
        delButton.addActionListener(this);
        delButton.setFocusable(false);
        delButton.setBounds(100, 100, 150, 50);
        inputWindow.add(delButton);
    }

    // REQUIRES: textInputID exists
    // MODIFIES: journal and inputWindow
    // EFFECTS: edits entry from journal and closes input window
    private void triggerEditButtonClickProcess() {
        journal.editEntry(
                Integer.parseInt(textInputID.getText()),
                textDepDate.getText(),
                textRetDate.getText(),
                textReason.getText(),
                textDest.getText());
        inputWindow.dispose();
    }

    // MODIFIES: journal and inputWindow
    // EFFECTS: adds entry from journal and closes input window
    private void triggerAddButtonClickProcess() {
        Entry userEntry = new Entry(textDepDate.getText(), textRetDate.getText(), textReason.getText(),
                textDest.getText());
        journal.addEntry(userEntry);
        inputWindow.dispose();
    }

    // REQUIRES: view entries button clicked
    // MODIFIES: entries
    // EFFECTS: creates new entries window displays entries
    private void viewEntriesProcess() {
        JFrame entries = new JFrame();
        entries.setTitle("Entry List");
        entries.setSize(500, 500);
        entries.setVisible(true);
        createEntryListTable(entries);
    }

    // REQUIRES: edit entry button pressed
    // EFFECTS: creates instance of input window allowing user to edit entry
    private void editEntryProcess() {
        makeInputWindow();
        makeDepartureLabelAndJTextField();
        makeReturnLabelAndJTextField();
        makeDestinationLabelAndJTextField();
        makeReasonLabelAndJTextField();
        makeInputIDLabelAndJTextField(10, 210);
        makeEditButton();
    }

    // REQUIRES: add entry button pressed
    // EFFECTS: creates instance of input window allowing user to add entry
    private void addEntryProcess() {
        makeInputWindow();
        makeDepartureLabelAndJTextField();
        makeReturnLabelAndJTextField();
        makeDestinationLabelAndJTextField();
        makeReasonLabelAndJTextField();
        makeAddButton();
    }

    // MODIFIES: entries
    // EFFECTS: creates table of all entries in journal
    private void createEntryListTable(JFrame entries) {
        // Table column names
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Entry ID");
        columnNames.add("Departure Date");
        columnNames.add("Return Date");
        columnNames.add("Reason");
        columnNames.add("Destination");

        // Data for the table
        Vector<Vector<Object>> data = new Vector<>();
        for (Entry entry : journal.getEntries()) {
            Vector<Object> row = new Vector<>();
            row.add(entry.getEntryID());
            row.add(entry.getDepartureDate());
            row.add(entry.getReturnDate());
            row.add(entry.getReason());
            row.add(entry.getDestination());
            data.add(row);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Adding the table to a JScrollPane to have scroll bars
        JScrollPane scrollPane = new JScrollPane(table);
        entries.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: inputID and textInputID
    // EFFECTS: creates inputID and textInputID label and textfield
    private void makeInputIDLabelAndJTextField(int x, int y) {
        inputID = new JLabel("ID: ");
        inputID.setFont(new Font("Arial", Font.PLAIN, 20));
        inputID.setSize(180, 20);
        inputID.setLocation(x, y);
        inputWindow.add(inputID);

        textInputID = new JTextField("1");
        textInputID.setFont(new Font("Arial", Font.PLAIN, 15));
        textInputID.setSize(20, 20);
        textInputID.setLocation(160, y);
        inputWindow.add(textInputID);
    }

    // MODIFIES: inputWindow & editButton
    // EFFECTS: creates edit button and adds it to inputWindow
    private void makeEditButton() {
        JButton editButton = new JButton("Edit");
        editButton.setActionCommand("edit");
        editButton.addActionListener(this);
        editButton.setFocusable(false);
        editButton.setBounds(230, 205, 80, 40);
        inputWindow.add(editButton);
    }

    // MODIFIES: inputWindow & addButton
    // EFFECTS: creates add button and adds it to inputWindow
    private void makeAddButton() {
        JButton addButton = new JButton("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setBounds(100, 200, 150, 50);
        inputWindow.add(addButton);
    }

    // MODIFIES: inputWindow
    // EFFECTS: creates new input window
    private void makeInputWindow() {
        inputWindow = new JFrame();
        inputWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        inputWindow.setVisible(true);
        inputWindow.setLayout(null);
        inputWindow.setTitle("Input Window");
        inputWindow.setSize(350, 300);
    }

    // MODIFIES: depDate and textDepDate
    // EFFECTS: creates depDate and textDepDate label and textfield
    private void makeDepartureLabelAndJTextField() {
        depDate = new JLabel("Departure Date:");
        depDate.setFont(new Font("Arial", Font.PLAIN, 20));
        depDate.setSize(180, 20);
        depDate.setLocation(10, 10);
        inputWindow.add(depDate);

        textDepDate = new JTextField("YYYY-MM-DD");
        textDepDate.setFont(new Font("Arial", Font.PLAIN, 15));
        textDepDate.setSize(150, 20);
        textDepDate.setLocation(160, 12);
        inputWindow.add(textDepDate);
    }

    // MODIFIES: retDate and textRetDate
    // EFFECTS: creates retDate and textRetDate label and textfield
    private void makeReturnLabelAndJTextField() {
        retDate = new JLabel("Return Date:");
        retDate.setFont(new Font("Arial", Font.PLAIN, 20));
        retDate.setSize(180, 20);
        retDate.setLocation(10, 60);
        inputWindow.add(retDate);

        textRetDate = new JTextField("YYYY-MM-DD");
        textRetDate.setFont(new Font("Arial", Font.PLAIN, 15));
        textRetDate.setSize(150, 20);
        textRetDate.setLocation(160, 62);
        inputWindow.add(textRetDate);
    }

    // MODIFIES: dest and textDest
    // EFFECTS: creates dest and textDest label and textfield
    private void makeDestinationLabelAndJTextField() {
        dest = new JLabel("Destination:");
        dest.setFont(new Font("Arial", Font.PLAIN, 20));
        dest.setSize(180, 20);
        dest.setLocation(10, 110);
        inputWindow.add(dest);

        textDest = new JTextField("i.e. Mexico");
        textDest.setFont(new Font("Arial", Font.PLAIN, 15));
        textDest.setSize(150, 20);
        textDest.setLocation(160, 112);
        inputWindow.add(textDest);
    }

    // MODIFIES: reason and textReason
    // EFFECTS: creates reason and textReason label and textfield
    private void makeReasonLabelAndJTextField() {
        reason = new JLabel("Reason:");
        reason.setFont(new Font("Arial", Font.PLAIN, 20));
        reason.setSize(180, 20);
        reason.setLocation(10, 160);
        inputWindow.add(reason);

        textReason = new JTextField("i.e. Pleasure");
        textReason.setFont(new Font("Arial", Font.PLAIN, 15));
        textReason.setSize(150, 20);
        textReason.setLocation(160, 162);
        inputWindow.add(textReason);
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
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
