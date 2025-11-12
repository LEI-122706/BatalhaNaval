// src/test/java/iscteiul/ista/battleship/ShipTest.java
package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for abstract class Ship.
 * Author: LEI-122676
 * Date: 2025-11-12 12:00
 * Cyclomatic Complexity:
 * - constructor: 2   (path: valid creation, path: null bearing -> exception)
 * - getName(): 1
 * - getBearing(): 1
 * - getPositions(): 1
 */
class ShipTest {
    private Ship ship;

    @BeforeEach
    void setUp() {
        // instantiate a concrete subclass to exercise Ship behavior
        ship = new Barge(Compass.NORTH, new Position(3, 3));
    }

    @AfterEach
    void tearDown() {
        ship = null;
    }

    // constructor1() - CC path 1: normal construction
    @Test
    void constructor1() {
        assertNotNull(ship, "Error: expected non-null Ship instance but got null");
    }

    // constructor2() - CC path 2: null bearing should throw NullPointerException
    @Test
    void constructor2() {
        assertThrows(NullPointerException.class, () -> {
            new Barge(null, new Position(0, 0));
        }, "Error: expected NullPointerException when constructing Ship (via Barge) with null bearing");
    }



    // getBearing() - CC = 1
    @Test
    void getBearing() {
        Compass bearing = ship.getBearing();
        assertNotNull(bearing, "Error: expected non-null bearing but got null");
    }

    // getPositions() - CC = 1
    @Test
    void getPositions() {
        assertAll(
                () -> assertNotNull(ship.getPositions(), "Error: expected non-null positions list but got null"),
                () -> assertFalse(ship.getPositions().isEmpty(), "Error: expected positions not empty for initialized ship but was empty")
        );
    }
}
