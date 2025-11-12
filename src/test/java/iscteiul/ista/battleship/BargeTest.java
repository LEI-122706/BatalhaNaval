package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Barge.
 * Author: LEI-122676
 * Date: 2025-11-12 12:00
 * Cyclomatic Complexity:
 * - constructor: 1
 * - getSize(): 1
 */

@DisplayName("Barge's test class (LEI-122676)")
class BargeTest {
    private Barge barge;

    @BeforeEach
    void setUp() {
        // instantiate the concrete class under test
        barge = new Barge(Compass.NORTH, new Position(5, 5));
    }

    @AfterEach
    void tearDown() {
        // clean up after each test
        barge = null;
    }

    // constructor() - CC = 1
    @Test
    void constructor() {
        assertAll(
                () -> assertNotNull(barge, "Error: expected non-null Barge instance but got null"),
                () -> assertEquals(1, barge.getPositions().size(), "Error: expected 1 position but got " + barge.getPositions().size())
        );
    }

    // getSize() - CC = 1
    @Test
    void getSize() {
        int size = barge.getSize();
        assertEquals(1, size, "Error: expected size 1 but got " + size);
    }
}
