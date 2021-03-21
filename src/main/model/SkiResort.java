package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// this class represents a full ski resort with multiple runs and lifts
public class SkiResort implements Writable {

    private ArrayList<SkiRun> runs = new ArrayList<SkiRun>();
    private ArrayList<Lift> lifts = new ArrayList<Lift>();
    private final String name;

    // EFFECTS: constructs resort with Name and no runs or lifts
    public SkiResort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumOfRuns() {
        return runs.size();
    }

    public int getNumOfLifts() {
        return lifts.size();
    }

    // EFFECTS: returns list of all ski runs and their info
    public ArrayList<SkiRun> viewAllRuns() {
        return this.runs;
    }

    // EFFECTS: returns list of all lifts and their info
    public ArrayList<Lift> viewAllLifts() {
        return this.lifts;
    }

    // EFFECTS: returns num of open lifts
    public int getNumOpenLifts() {
        int count = 0;
        for (Lift lift : this.lifts) {
            if (lift.getOpen()) {
                count++;
            }
        }
        return count;
    }

    // EFFECTS: returns num of open runs
    public int getNumOpenRuns() {
        int count = 0;
        for (SkiRun run : this.runs) {
            if (run.getOpen()) {
                count++;
            }
        }
        return count;
    }

    // REQUIRES: the liftID of a valid lift
    // EFFECTS: returns an estimate of how long it will take to get through a lift line
    public String getLiftLineEstimate(int targetID) {
        int estimate = 0;
        int peopleInLine;
        for (Lift lift : this.lifts) {
            if (lift.getID() == targetID) {
                peopleInLine = lift.getNumPeopleInLine();
                estimate = ((peopleInLine / lift.getSeatsPerChair()) * 30); // 30 seconds per chair
            }
        }
        return ((estimate / 60) + " mins");
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
    // EFFECTS: creates a new SkiRun with given name, adds to list of runs
    public void addRun(String name) {
        runs.add(new SkiRun(name, getNumOfRuns()));
    }

    // REQUIRES: a name for the lift as a string, number of seats per chair
    // MODIFIES: SkiResort, and a new Lift
    // EFFECTS: creates a new Lift with given name, num of seats, adds to list of lifts
    public void addLift(String name, int numOfSeats) {
        lifts.add(new Lift(name, numOfSeats, getNumOfLifts()));
    }

    // REQUIRES: a valid run's id to remove
    // MODIFIES: SkiResort
    // EFFECTS: deletes a run from SkiResort's list
    public void removeRun(int id) {
        runs.removeIf(run -> run.getID() == id);
        renumberRuns();
    }

    // REQUIRES: a valid lift's id to remove
    // MODIFIES: SkiResort
    // EFFECTS: deletes a lift from SkiResort's list
    public void removeLift(int id) {
        lifts.removeIf(lift -> lift.getID() == id);
        renumberLifts();
    }

    // MODIFIES: all runs in list of runs
    // EFFECTS: renumbers all runs to be consecutive
    private void renumberRuns() {
        int newID = 1;
        for (SkiRun run : runs) {
            run.setID(newID);
            newID++;
        }
    }

    // MODIFIES: all lifts in list of lifts
    // EFFECTS: renumbers all lifts to be consecutive
    private void renumberLifts() {
        int newID = 1;
        for (Lift lift : lifts) {
            lift.setID(newID);
            newID++;
        }
    }

    // REQUIRES: a SkiRun
    // MODIFIES: SkiResort
    // EFFECTS: adds an existing given SkiRun to SkiResort
    public void importRun(SkiRun run)  {
        runs.add(run);
    }

    // REQUIRES: a Lift
    // MODIFIES: SkiResort
    // EFFECTS: adds given Lift to SkiResort
    public void importLift(Lift lift) {
        lifts.add(lift);
    }

    // REQUIRES: a valid runID, a new status for said run as a string
    // MODIFIES: the given SkiRun
    // EFFECTS: opens the given SkiRun, with a new status
    public int openRun(int targetRun, String newStatus) {
        for (SkiRun currentRun : this.runs) {
            if (currentRun.getID() == targetRun) {
                currentRun.open(newStatus);
                return 1; // 1 marks success
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
                return 1; // 1 marks success
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
                return 1;  // 1 marks success
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
                return 1;  // 1 marks success
            }
        }
        return 0;
    }

    @Override
    // borrowed structure from JsonDemo
    // exports skiresort as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("runs", runsToJson());
        json.put("lifts", liftsToJson());
        return json;
    }

    // EFFECTS: returns runs in this SkiResort as a JSON array
    private JSONArray runsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (SkiRun r : runs) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns lifts in this SkiResort as a JSON array
    private JSONArray liftsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Lift l : lifts) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}