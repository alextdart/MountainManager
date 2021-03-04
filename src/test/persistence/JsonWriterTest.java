package persistence;

import model.Lift;
import model.SkiResort;
import model.SkiRun;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    // borrowed structure from JsonDemo

    @Test
    void testWriterInvalidFile() {
        try {
            SkiResort sr = new SkiResort("Dart Mountain");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySkiResort() {
        try {
            SkiResort sr = new SkiResort("Dart Mountain");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySkiResort.json");
            writer.open();
            writer.write(sr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySkiResort.json");
            sr = reader.read();
            assertEquals("Dart Mountain", sr.getName());
            assertEquals(0, sr.getNumOfLifts());
            assertEquals(0, sr.getNumOfRuns());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSkiResort() {
        try {
            SkiResort sr = new SkiResort("Dart Mountain");
            sr.addRun("testrun");
            sr.addLift("testlift", 3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSkiResort.json");
            writer.open();
            writer.write(sr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSkiResort.json");
            sr = reader.read();
            assertEquals("Dart Mountain", sr.getName());
            List<Lift> lifts = sr.viewAllLifts();
            List<SkiRun> runs = sr.viewAllRuns();
            assertEquals(1, lifts.size());
            assertEquals(1, runs.size());
            checkRun(runs.get(0), "testrun", "closed", 1, false);
            checkLift(lifts.get(0),"testlift", false, 1, 3, 0);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}