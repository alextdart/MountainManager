package model;

public class Lift {

    private boolean open = false;   // false = closed, true = open
    private final int liftID;
    private final String name;
    private final int seatsPerChair;
    private int numPeopleInLine = 0;

    // EFFECTS: constructs new lift with Name
    public Lift(String name, int seatsPerChair, int numLiftsCurrently) {
        this.name = name;
        this.seatsPerChair = seatsPerChair;
        this.liftID = numLiftsCurrently + 1;
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

    public void closeLift() {
        this.open = false;
    }
}
