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

    public String getName() {
        return name;
    }

    public int getNumOfRuns() {
        return runs.size();
    }

    public int getNumOfLifts() {
        return lifts.size();
    }

    public void viewAllRuns() {
        if (this.runs.size() == 0) {
            System.out.println("\nthere are no runs yet!");
        }
        System.out.println("\n");
        for (SkiRun run : this.runs) {
            System.out.println("Run ID: " + run.getID() + " | Name: " + run.getName() + " | Status: " + run.getStatus());
        }
    }

    public void viewAllLifts() {
        if (this.lifts.size() == 0) {
            System.out.println("\nthere are no lifts yet!");
        }
        System.out.println("\n");
        for (Lift lift : this.lifts) {
            String currentStatus;
            if (lift.getOpen()) {
                currentStatus = "open";
            } else {
                currentStatus = "closed";
            }
            System.out.println("Lift ID: " + lift.getID() + " | Name: " + lift.getName() + " | Status: "
                    + currentStatus + " | Wait estimate: " + getLiftLineEstimate(lift.getID()));
        }
    }

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

    public void updateLiftLine(int targetID, int peopleInLine) {
        for (Lift lift : this.lifts) {
            if (lift.getID() == targetID) {
                lift.setNumPeopleInLine(peopleInLine);
            }
        }
    }

    public void addRun(String name) {
        runs.add(new SkiRun(name, getNumOfRuns()));
    }

    public void addLift(String name, int numOfSeats) {
        lifts.add(new Lift(name, numOfSeats, getNumOfLifts()));
    }

    public int openRun(int targetRun, String newStatus) {
        for (SkiRun currentRun : this.runs) {
            if (currentRun.getID() == targetRun) {
                currentRun.open(newStatus);
                return 1;
            }
        }
        return 0;
    }

    public int closeRun(int targetRun) {
        for (SkiRun currentRun : this.runs) {
            if (currentRun.getID() == targetRun) {
                currentRun.close();
                return 1;
            }
        }
        return 0;
    }

    public int openLift(int targetLift) {
        for (Lift currentLift : this.lifts) {
            if (currentLift.getID() == targetLift) {
                currentLift.openLift();
                return 1;
            }
        }
        return 0;
    }

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