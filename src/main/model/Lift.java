package model;

// this class represents a chair lift, and represents it's name, number of seats per chair, and other related info
public class Lift {

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
}
