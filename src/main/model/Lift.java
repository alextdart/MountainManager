package model;

import org.json.JSONObject;
import persistence.Writable;

// this class represents a chair lift, and represents it's name, number of seats per chair, and other related info
public class Lift implements Writable {

    private boolean open = false;   // false = closed, true = open
    private final int liftID;
    private final String name;
    private final int seatsPerChair;
    private int numPeopleInLine = 0;

    // EFFECTS: constructs new lift with Name, seats per chair
    public Lift(String name, int seatsPerChair, int numLiftsCurrently) {
        this.name = name;
        this.seatsPerChair = seatsPerChair;
        this.liftID = numLiftsCurrently + 1; // liftID is the next number after how many lifts exist already
    }

    // EFFECTS: constructs lift with given parameters
    public Lift(String name, boolean open, int id, int seats, int lineup) {
        this.name = name;
        this.open = open;
        this.liftID = id;
        this.seatsPerChair = seats;
        this.numPeopleInLine = lineup;
    }

    public int getID() {
        return this.liftID;
    }

    public boolean getOpen() {
        return this.open;
    }

    public int getSeatsPerChair() {
        return this.seatsPerChair;
    }

    public String getName() {
        return this.name;
    }

    public int getNumPeopleInLine() {
        return this.numPeopleInLine;
    }

    public void setNumPeopleInLine(int newNumber) {
        this.numPeopleInLine = newNumber;
    }

    // MODIFIES: this
    // EFFECTS: opens the lift
    public void openLift() {
        this.open = true;
    }

    // MODIFIES: this
    // EFFECTS: closes the lift
    public void closeLift() {
        this.open = false;
    }

    @Override
    // borrowed structure from JsonDemo
    // EFFECTS: exports lift as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("open", open);
        json.put("id", liftID);
        json.put("seats", seatsPerChair);
        json.put("lineup", numPeopleInLine);
        return json;
    }
}
