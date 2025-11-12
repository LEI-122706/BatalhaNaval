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
    private Carrack carrack;

    @BeforeEach
    void setUp() {
        carrack = new Carrack(Compass.EAST, new Position(2, 2));
    }

    @AfterEach
    void tearDown() {
        carrack = null;
    }

    // constructor1() - CC path 1: normal construction
    @Test
    void constructor1() {
        assertNotNull(carrack, "Error: expected non-null Carrack instance but got null");
    }

    // constructor2() - CC path 2: null bearing should throw NullPointerException
    @Test
    void constructor2() {
        assertThrows(NullPointerException.class, () -> {
            new Carrack(null, new Position(0, 0));
        }, "Error: expected NullPointerException when constructing Carrack with null bearing");
    }

    // getSize() - CC = 1
    @Test
    void getSize() {
        int size = carrack.getSize();
        assertTrue(size > 0, "Error: expected positive size but got " + size);
    }
}
