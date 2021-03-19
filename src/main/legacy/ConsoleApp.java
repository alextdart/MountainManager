package legacy;

import model.Lift;
import model.SkiResort;
import model.SkiRun;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// runs the entire Resort Application with ui
public class ConsoleApp {

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/resort.json"; // borrowed from JsonDemo
    private SkiResort resort;
    private Scanner input;

    // EFFECTS: runs the resort application
    public ConsoleApp() {
        runResort();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runResort() {
        boolean run = true;
        String command;

        init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                promptSave();
                run = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("lifts")) {
            checkLifts();
        } else if (command.equals("runs")) {
            checkRuns();
        } else if (command.equals("add")) {
            addNew();
        } else if (command.equals("save")) {
            saveSkiResort();
        } else if (command.equals("load")) {
            loadSkiResort();
        } else if (command.equals("change")) {
            String modificationTarget = modifyWho();
            if (modificationTarget.equals("run")) {
                modifyRuns();
            } else if (modificationTarget.equals("lift")) {
                modifyLifts();
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new ski resort named Dart Mountain
    private void init() {
        resort = new SkiResort("Dart Mountain");
        System.out.println("Welcome to the " + resort.getName() + " management system!");
        input = new Scanner(System.in);
        input.useDelimiter("\n"); // changes input delimiter to a newline instead of whitespace
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tlifts -> view lifts");
        System.out.println("\truns -> view runs");
        System.out.println("\tadd -> add new lift or run");
        System.out.println("\tsave -> save resort to a file");
        System.out.println("\tload -> load resort from a file");
        System.out.println("\tchange -> change status of a lift or a run");
        System.out.println("\tquit -> quit the program");
    }

    // EFFECTS: prompts user to save mountain before quitting
    private void promptSave() {
        String command;
        System.out.println("\nWould you like to save your mountain before exiting? (y/n)");
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            saveSkiResort();
        }
    }

    // EFFECTS: displays all lifts and their related info to the user, or a message if there aren't any
    private void checkLifts() {
        ArrayList<Lift> allLifts;
        if (resort.getNumOfLifts() == 0) {
            System.out.println("\nthere are no lifts yet!");
        } else {
            allLifts = resort.viewAllLifts();
            System.out.println("\n");
            for (Lift lift : allLifts) {
                String currentStatus;
                if (lift.getOpen()) {
                    currentStatus = "open";
                } else {
                    currentStatus = "closed";
                }
                System.out.println("Lift ID: " + lift.getID() + " | Name: " + lift.getName() + " | Status: "
                        + currentStatus + " | Wait estimate: " + resort.getLiftLineEstimate(lift.getID()));
            }
        }
    }

    // EFFECTS: displays all runs and their related info to the user, or a message if there aren't any
    private void checkRuns() {
        ArrayList<SkiRun> allRuns;
        if (resort.getNumOfRuns() == 0) {
            System.out.println("\nthere are no runs yet!");
        } else {
            allRuns = resort.viewAllRuns();
            System.out.println("\n");
            for (SkiRun run : allRuns) {
                System.out.println("Run ID: " + run.getID() + " | Name: "
                        + run.getName() + " | Status: " + run.getStatus());
            }
        }
    }

    // MODIFIES: SkiResort
    // EFFECTS: adds a new run or lift to the resort depending on user input
    private void addNew() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("run") || selection.equals("lift"))) {
            System.out.println("run -> new run");
            System.out.println("lift -> new lift");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("run")) {
            System.out.println("enter the new run's name: ");
            String newName = input.next();
            resort.addRun(newName);
            System.out.println("new run " + newName + " created.");
        } else {
            System.out.println("enter the new lifts's name: ");
            String newName = input.next();
            String numSeats = getValidNumSeats();
            resort.addLift(newName, Integer.parseInt(numSeats));
            System.out.println("new lift " + newName + " created with " + numSeats + " seat(s) per chair.");
        }
    }

    // EFFECTS: gets a valid number of seats per chair
    private String getValidNumSeats() {
        String numSeats = ""; // forced entry to loop
        while (!isNumeric(numSeats) || Integer.parseInt(numSeats) < 1) {
            System.out.println("please enter the number of seats per chair: ");
            numSeats = input.next();
        }
        return numSeats;
    }

    // used to determine user's choice of what to modify
    // EFFECTS: returns the type of modification the user wishes to make
    private String modifyWho() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("run") || selection.equals("lift") || selection.equals("exit"))) {
            System.out.println("run -> modify a run");
            System.out.println("lift -> modify a lift");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("run") && (resort.getNumOfRuns() == 0)) {
            System.out.println("there aren't any runs to modify yet!");
            return "invalid";
        } else if (selection.equals("lift") && (resort.getNumOfLifts() == 0)) {
            System.out.println("there aren't any lifts to modify yet!");
            return "invalid";
        } else {
            return selection;
        }

    }

    // REQUIRES: user has selected "run" in modifyWho()
    // MODIFIES: a selected ski run from within SkiResort
    // EFFECTS: changes status of a selected ski run
    private void modifyRuns() {
        String runID = "";  // force entry into loop
        while ((runID.equals("")) || (Integer.parseInt(runID) > resort.getNumOfRuns())) {
            System.out.println("enter the runID number you wish to modify: ");
            runID = input.next();
        }
        String operation = ""; // force entry into loop
        while (!(operation.equals("open") || operation.equals("close"))) {
            System.out.println("open -> open run");
            System.out.println("close -> close run");
            operation = input.next();
            operation = operation.toLowerCase();
        }
        if (operation.equals("open")) {
            String newStatus;
            System.out.println("enter the run's new status: ");
            newStatus = input.next();
            resort.openRun(Integer.parseInt(runID), newStatus);
            System.out.println("run " + runID + " opened with status: " + newStatus);
        } else {
            resort.closeRun(Integer.parseInt(runID));
            System.out.println("run " + runID + " closed.");
        }

    }

    // REQUIRES: user has selected "lift" in modifyWho()
    // MODIFIES: a selected lift from within SkiResort
    // EFFECTS: changes status or lift line time of a selected lift
    private void modifyLifts() {
        String liftID = "";  // force entry into loop
        while (!(isNumeric(liftID)) || !(liftIDValid(liftID))) {
            System.out.println("enter the liftID number you wish to modify: ");
            liftID = input.next();
        }
        String operation = ""; // force entry into loop
        while (!(operation.equals("open") || operation.equals("close") || operation.equals("update"))) {
            System.out.println("open -> open lift");
            System.out.println("close -> close lift");
            System.out.println("update -> update num of people in lift line");
            operation = input.next();
            operation = operation.toLowerCase();
        }
        if (operation.equals("open")) {
            resort.openLift(Integer.parseInt(liftID));
            System.out.println("Lift " + liftID + " opened.");
        } else if (operation.equals("close")) {
            resort.closeLift(Integer.parseInt(liftID));
            System.out.println("Lift " + liftID + " closed.");
        } else {
            updatePeopleInLiftLine(liftID);
        }
    }

    // updates how many people are in a lift's line
    // REQUIRES: at least one lift exists, valid liftID passed
    // MODIFIES: a lift from within SkiResort
    // EFFECTS: updates how many people are in that lift's line
    private void updatePeopleInLiftLine(String liftID) {
        System.out.println("how many people are in line?: ");
        int peopleInLine = input.nextInt();
        resort.updateLiftLine(Integer.parseInt(liftID), peopleInLine);
        System.out.println("updated lift " + liftID + " to have " + peopleInLine + " people in line.");
    }

    // EFFECTS: returns true if string passed through is actually a valid integer
    private boolean isNumeric(String value) {
        try {
            int i = Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // REQUIRES: an integer
    // EFFECTS: returns true if liftID given is a valid lift
    private boolean liftIDValid(String liftID) {
        return (0 < Integer.parseInt(liftID)) && (Integer.parseInt(liftID) <= resort.getNumOfLifts());
    }

    // EFFECTS: saves the SkiResort to file
    private void saveSkiResort() {
        try {
            jsonWriter.open();
            jsonWriter.write(resort);
            jsonWriter.close();
            System.out.println("Saved " + resort.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads SkiResort from file
    private void loadSkiResort() {
        try {
            resort = jsonReader.read();
            System.out.println("Loaded " + resort.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

