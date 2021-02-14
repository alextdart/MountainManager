package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkiResortTest {

    private SkiResort testResort;

    @BeforeEach
    void runBefore() {
        testResort = new SkiResort("Test Resort");
        testResort.addRun("run1");
        testResort.addRun("run2");
        testResort.addLift("big lift", 6);
        testResort.addLift("small lift", 3);
    }

    @Test
    void testGetName() {
        assertEquals(testResort.getName(), "Test Resort");
    }

    @Test
    void testGetNumRuns() {
        assertEquals(testResort.getNumOfRuns(), 2);
    }

    @Test
    void testGetNumLifts() {
        assertEquals(testResort.getNumOfLifts(), 2);
    }

    @Test
    void testGetLiftLineEstimate() {
        testResort.updateLiftLine(1, 100);
        testResort.updateLiftLine(2, 100);
        assertEquals(testResort.getLiftLineEstimate(1), "8 minutes");
        assertEquals(testResort.getLiftLineEstimate(2), "16 minutes");
        testResort.updateLiftLine(2, 25);
        assertEquals(testResort.getLiftLineEstimate(2), "4 minutes");
    }

    @Test
    void testAddRun() {
        assertEquals(testResort.getNumOfRuns(), 2);
        testResort.addRun("run3");
        assertEquals(testResort.getNumOfRuns(), 3);
    }

    @Test
    void testAddLift() {
        assertEquals(testResort.getNumOfLifts(), 2);
        testResort.addLift("mediumlift", 4);
        assertEquals(testResort.getNumOfLifts(), 3);
    }

    @Test
    void testOpenAndCloseRuns() {
        assertEquals(testResort.openRun(1, "powder"), 1);
        assertEquals(testResort.openRun(2, "groomed"), 1);
        assertEquals(testResort.closeRun(2), 1);
    }

    @Test
    void testOpenAndCloseLifts() {
        assertEquals(testResort.openLift(1), 1);
        assertEquals(testResort.openLift(2), 1);
        assertEquals(testResort.closeLift(2), 1);
    }
}