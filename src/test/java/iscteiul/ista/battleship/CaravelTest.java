package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Caravel.
 * Author: LEI-122676
 * Date: 2025-11-12 12:00
 * Cyclomatic Complexity:
 * - constructor: 2   (path: valid creation, path: null bearing -> exception)
 * - getSize(): 1
 */

@DisplayName("Caravel's test class (LEI-122676)")
class CaravelTest {
    private Caravel caravelN;
    private Caravel caravelE;
    private Caravel caravelS;
    private Caravel caravelW;

    @BeforeEach
    void setUp() {
        // happy-path instance used by most tests
        caravelN = new Caravel(Compass.NORTH, new Position(4, 4));
        caravelE = new Caravel(Compass.EAST, new Position(4, 4));
        caravelS = new Caravel(Compass.SOUTH, new Position(4, 4));
        caravelW = new Caravel(Compass.WEST, new Position(4, 4));
    }

    @AfterEach
    void tearDown() {
        caravelN = null;
        caravelE = null;
        caravelS = null;
        caravelW = null;
    }

    // constructor1() - CC path 1: normal construction
    @Test
    void constructor1() {
        assertNotNull(caravelN, "Error: expected non-null Caravel instance but got null");
        assertNotNull(caravelE, "Error: expected non-null Caravel instance but got null");
        assertNotNull(caravelW, "Error: expected non-null Caravel instance but got null");
        assertNotNull(caravelS, "Error: expected non-null Caravel instance but got null");
    }

    // constructor2() - CC path 2: null bearing should throw NullPointerException
    @Test
    void constructor2() {
        AssertionError ex = assertThrows(AssertionError.class, () -> {
            new Caravel(null, new Position(1, 1));
        }, "Error: expected NullPointerException when constructing Caravel with null bearing");
        assertEquals(AssertionError.class, ex.getClass());
    }

    @Test
    void constructorThrowsForInvalidBearing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Caravel(Compass.UNKNOWN, new Position(5, 5)));
        assertEquals("ERROR! invalid bearing for the caravel", exception.getMessage());
    }

    // getSize() - CC = 1
    @Test
    void getSize() {
        assertEquals(2, caravelN.getSize());
        assertEquals(2, caravelE.getSize());
        assertEquals(2, caravelS.getSize());
        assertEquals(2, caravelW.getSize());
    }
}
