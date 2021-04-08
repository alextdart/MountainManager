package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MountainManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MountainManagerTest {

    private MountainManager testMM;

    @BeforeEach
    void runBefore() {
        testMM = new MountainManager();
    }

    @Test
    void testTryParseValid() {
        assertEquals(testMM.tryParse("4"), 4);
    }

    @Test
    void testTryParseInvalid() {
        assertEquals(testMM.tryParse("four"), -1);
    }
}