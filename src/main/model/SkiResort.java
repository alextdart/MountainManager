package model;

import java.util.ArrayList;

public class SkiResort {

    private ArrayList<SkiRun> runs = new ArrayList<SkiRun>();
    private ArrayList<Lift> lifts = new ArrayList<Lift>();
    private boolean open = false;   // false = closed, true = open
    private final String name;

    // EFFECTS: constructs resort with Name and no runs or lifts
    public SkiResort(String name) {
        this.name = name;
    }

    // EFFECTS: returns name of resort
    public String getName() {
        return name;
    }

    // EFFECTS: returns number of runs
    public int getNumOfRuns() {
        return runs.size();
    }

    // EFFECTS: returns number of lifts
    public int getNumOfLifts() {
        return lifts.size();
    }

    // EFFECTS: prints all ski runs and their info
    public ArrayList<SkiRun> viewAllRuns() {
        return this.runs;
    }

    // EFFECTS: returns list of all lifts and their info
    public ArrayList<Lift> viewAllLifts() {
        return this.lifts;
    }

    // REQUIRES: the liftID of a valid lift
    // EFFECTS: returns an estimate of how long it will take to get through a lift line
    public String getLiftLineEstimate(int targetID) {
        int estimate = 0;
        int peopleInLine = 0;
        for (Lift lift : this.lifts) {
            if (lift.getID() == targetID) {
                peopleInLine = lift.getNumPeopleInLine();
                estimate = ((peopleInLine / lift.getSeatsPerChair()) * 30);
            }
        }
        return ((estimate / 60) + " minutes");
    }

    // REQUIRES: the liftID of a valid lift, and a number of people in line
    // MODIFIES: number of people in given lift's line
    // EFFECTS: sets new value for number of people in a certain lift's line
    public void updateLiftLine(int targetID, int peopleInLine) {
        for (Lift lift : this.lifts) {
            if (lift.getID() == targetID) {
                lift.setNumPeopleInLine(peopleInLine);
            }
        }
    }

    // REQUIRES: a name for the run as a string
    // MODIFIES: SkiResort, and a new SkiRun
    // EFFECTS: creates a new SkiRun with given name
    public void addRun(String name) {
        runs.add(new SkiRun(name, getNumOfRuns()));
    }

    // REQUIRES: a name for the lift as a string, number of seats per chair
    // MODIFIES: SkiResort, and a new Lift
    // EFFECTS: creates a new Lift with given name, num of seats
    public void addLift(String name, int numOfSeats) {
        lifts.add(new Lift(name, numOfSeats, getNumOfLifts()));
    }

    // REQUIRES: a valid runID, a new status for said run as a string
    // MODIFIES: the given SkiRun
    // EFFECTS: opens the given SkiRun, with a new status
    public int openRun(int targetRun, String newStatus) {
        for (SkiRun currentRun : this.runs) {
            if (currentRun.getID() == targetRun) {
                currentRun.open(newStatus);
                return 1;
            }
        }
        return 0;
    }

    // REQUIRES: a valid runID
    // MODIFIES: the given SkiRun
    // EFFECTS: closes the given SkiRun
    public int closeRun(int targetRun) {
        for (SkiRun currentRun : this.runs) {
            if (currentRun.getID() == targetRun) {
                currentRun.close();
                return 1;
            }
        }
        return 0;
    }

    // REQUIRES: a valid liftID
    // MODIFIES: the given Lift
    // EFFECTS: opens the given Lift
    public int openLift(int targetLift) {
        for (Lift currentLift : this.lifts) {
            if (currentLift.getID() == targetLift) {
                currentLift.openLift();
                return 1;
            }
        }
        return 0;
    }

    // REQUIRES: a valid liftID
    // MODIFIES: the given Lift
    // EFFECTS: closes the given Lift
    public int closeLift(int targetLift) {
        for (Lift currentLift : this.lifts) {
            if (currentLift.getID() == targetLift) {
                currentLift.closeLift();
                return 1;
            }
        }
        return 0;
    }
}