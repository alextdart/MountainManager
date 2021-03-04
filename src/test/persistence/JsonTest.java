package persistence;

import model.Lift;
import model.SkiRun;
import model.SkiResort;

import static org.junit.jupiter.api.Assertions.assertEquals;

// borrowed structure from JsonDemo
public class JsonTest {
    protected void checkRun(SkiRun run, String name, String status, int id, boolean open) {
        assertEquals(name, run.getName());
        assertEquals(status, run.getStatus());
        assertEquals(id, run.getID());
        assertEquals(open, run.getOpen());
    }

    protected void checkLift(Lift lift, String name, boolean open, int id, int seats, int lineup) {
        assertEquals(name, lift.getName());
        assertEquals(open, lift.getOpen());
        assertEquals(id, lift.getID());
        assertEquals(seats, lift.getSeatsPerChair());
        assertEquals(lineup, lift.getNumPeopleInLine());

    }
}
