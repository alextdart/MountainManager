package model;

public class SkiRun {

    private boolean open = false;   // false = closed, true = open
    private String status = "closed";
    private final int runID;
    private final String name;

    // EFFECTS: constructs new run with Name
    public SkiRun(String name, int numRunsCurrently) {
        this.name = name;
        this.runID = numRunsCurrently + 1; // this run gets the next available runID, assuming all prev. are taken
    }

    public String getStatus() {
        return this.status;
    }

    public int getID() {
        return this.runID;
    }

    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: closes the run
    public void close() {
        this.open = false;
        this.status = "closed";
    }

    // REQUIRES: a new status for the run
    // MODIFIES: this
    // EFFECTS: opens the run with new status
    public void open(String newStatus) {
        this.open = true;
        this.status = newStatus;
    }
}
