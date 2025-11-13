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
    private Caravel caravel;

    @BeforeEach
    void setUp() {
        // happy-path instance used by most tests
        caravel = new Caravel(Compass.NORTH, new Position(4, 4));
    }

    @AfterEach
    void tearDown() {
        caravel = null;
    }

    // constructor1() - CC path 1: normal construction
    @Test
    void constructor1() {
        assertNotNull(caravel, "Error: expected non-null Caravel instance but got null");
    }

    // constructor2() - CC path 2: null bearing should throw NullPointerException
    @Test
    void constructor2() {
        AssertionError ex = assertThrows(AssertionError.class, () -> {
            new Caravel(null, new Position(1, 1));
        }, "Error: expected NullPointerException when constructing Caravel with null bearing");
        assertEquals(ex.getClass(), AssertionError.class);
    }

    // getSize() - CC = 1
    @Test
    void getSize() {
        int size = caravel.getSize();
        assertEquals(size, 2);
    }
}
