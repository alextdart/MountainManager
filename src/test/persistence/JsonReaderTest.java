package persistence;

import model.Lift;
import model.SkiResort;
import model.SkiRun;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {
    // borrowed structure from JsonDemo

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SkiResort sr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySkiResort() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySkiResort.json");
        try {
            SkiResort sr = reader.read();
            assertEquals("Dart Mountain", sr.getName());
            assertEquals(0, sr.getNumOfLifts());
            assertEquals(0, sr.getNumOfRuns());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSkiResort.json");
        try {
            SkiResort sr = reader.read();
            assertEquals("Dart Mountain", sr.getName());
            List<Lift> lifts = sr.viewAllLifts();
            List<SkiRun> runs = sr.viewAllRuns();
            assertEquals(2, lifts.size());
            assertEquals(2, runs.size());
            checkRun(runs.get(0), "runone", "open", 1, true);
            checkRun(runs.get(1), "runtwo", "groomed", 2, true);
            checkLift(lifts.get(0),"liftone", true, 1, 1, 11);
            checkLift(lifts.get(1),"lifttwo", true, 2, 2, 22);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}