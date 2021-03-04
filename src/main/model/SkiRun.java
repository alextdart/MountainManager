package model;

import org.json.JSONObject;
import persistence.Writable;

// this class represents a ski run, it's condition, it's status (open or closed) and operations to change those
public class SkiRun implements Writable {

    private boolean open = false;   // false = closed, true = open
    private String status = "closed";
    private final int runID;
    private final String name;

    // EFFECTS: constructs new run with Name
    public SkiRun(String name, int numRunsCurrently) {
        this.name = name;
        this.runID = numRunsCurrently + 1; // this run gets the next available runID, assuming all prev. are taken
    }

    // EFFECTS: constructs new run given parameters
    public SkiRun(String name, String status, int id, boolean open) {
        this.name = name;
        this.status = status;
        this.runID = id;
        this.open = open;
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

    public boolean getOpen() {
        return this.open;
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

    @Override
    // borrowed structure from JsonDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("status", status);
        json.put("id", runID);
        json.put("open", open);
        return json;
    }
}
