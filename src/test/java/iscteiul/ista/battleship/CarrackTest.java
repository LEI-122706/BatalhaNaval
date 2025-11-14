package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for class Carrack.
 * Author: LEI-122676
 * Date: 2025-11-12 12:00
 * Cyclomatic Complexity:
 * - constructor: 2   (path: valid creation, path: null bearing -> exception)
 * - getSize(): 1
 */

@DisplayName("Carrack's test class (LEI-122676)")
class CarrackTest {
    private Carrack carrackN;
    private Carrack carrackS;
    private Carrack carrackE;
    private Carrack carrackW;

    @BeforeEach
    void setUp() {
        carrackN = new Carrack(Compass.NORTH, new Position(5, 5));
        carrackS = new Carrack(Compass.SOUTH, new Position(5, 5));
        carrackE = new Carrack(Compass.EAST, new Position(5, 5));
        carrackW = new Carrack(Compass.WEST, new Position(5, 5));
    }

    @AfterEach
    void tearDown() {
        carrackN = null;
        carrackS = null;
        carrackE = null;
        carrackW = null;
    }

    // constructor1() - CC path 1: normal construction
    @Test
    void constructor1() {
        assertNotNull(carrackN, "Error: expected non-null Carrack instance but got null");
        assertNotNull(carrackS, "Error: expected non-null Carrack instance but got null");
        assertNotNull(carrackE, "Error: expected non-null Carrack instance but got null");
        assertNotNull(carrackW, "Error: expected non-null Carrack instance but got null");
    }

    // constructor2() - CC path 2: null bearing should throw NullPointerException
    @Test
    void constructor2() {
        AssertionError ex = assertThrows(AssertionError.class, () -> {
            new Carrack(null, new Position(1, 1));
        }, "Error: expected NullPointerException when constructing Caravel with null bearing");
        assertEquals(ex.getClass(), AssertionError.class);
    }

    @Test
    void constructorThrowsForInvalidBearing() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Carrack(Compass.UNKNOWN, new Position(5, 5)));
        assertEquals("ERROR! invalid bearing for the carrack", exception.getMessage());
    }

    // getSize() - CC = 1
    @Test
    void getSize() {
        assertEquals(3, carrackN.getSize());
        assertEquals(3, carrackS.getSize());
        assertEquals(3, carrackW.getSize());
        assertEquals(3, carrackE.getSize());
    }
}
