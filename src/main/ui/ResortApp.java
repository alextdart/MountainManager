package ui;

import model.SkiResort;

import java.util.Scanner;

public class ResortApp {

    private SkiResort resort;
    private Scanner input;

    // EFFECTS: runs the resort application
    public ResortApp() {
        runResort();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runResort() {
        boolean run = true;
        String command = "";

        init();

        while (run) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
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
    // EFFECTS: initializes accounts
    private void init() {
        resort = new SkiResort("Dart Mountain");
        System.out.println("Welcome to the " + resort.getName() + " management system!");
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tlifts -> view lifts");
        System.out.println("\truns -> view runs");
        System.out.println("\tadd -> add new lift or run");
        System.out.println("\tchange -> change status of a lift or a run");
        System.out.println("\tquit -> quit the program");
    }

    private void checkLifts() {
        resort.viewAllLifts();
    }

    private void checkRuns() {
        resort.viewAllRuns();
    }

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
            System.out.println("enter the number of seats per chair: ");
            String numSeats = input.next();
            resort.addLift(newName, Integer.parseInt(numSeats));
            System.out.println("new lift " + newName + " created with " + numSeats + " seats per chair.");
        }
    }

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

    private void updatePeopleInLiftLine(String liftID) {
        System.out.println("how many people are in line?: ");
        int peopleInLine = input.nextInt();
        resort.updateLiftLine(Integer.parseInt(liftID), peopleInLine);
        System.out.println("updated lift " + liftID + " to have " + peopleInLine + " people in line.");
    }

    private boolean isNumeric(String value) {
        try {
            double d = Double.parseDouble(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean liftIDValid(String liftID) {
        return (0 < Integer.parseInt(liftID)) && (Integer.parseInt(liftID) <= resort.getNumOfLifts());
    }
}

