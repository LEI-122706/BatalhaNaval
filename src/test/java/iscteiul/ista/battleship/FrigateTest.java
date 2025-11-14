package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Frigate's test class (LEI-122706)")
class FrigateTest {

    private static final int EXPECTED_SIZE = 4;

    private final Frigate f1 = new Frigate(Compass.NORTH, new Position(5,5));
    private final Frigate f2 = new Frigate(Compass.SOUTH, new Position(5,5));
    private final Frigate f3 = new Frigate(Compass.EAST, new Position(5,5));
    private final Frigate f4 = new Frigate(Compass.WEST, new Position(5,5));

    @Test
    @DisplayName("getSize returns the correct size for frigate")
    void getSize() {
        assertEquals(EXPECTED_SIZE, f1.getSize());
        assertEquals(EXPECTED_SIZE, f2.getSize());
        assertEquals(EXPECTED_SIZE, f3.getSize());
        assertEquals(EXPECTED_SIZE, f4.getSize());
    }

    @Test
    @DisplayName("Constructor throws exception for invalid bearing")
    void constructorThrowsForInvalidBearing() throws Exception {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Frigate(Compass.UNKNOWN, new Position(5, 5)));
        assertEquals("ERROR! invalid bearing for the frigate", exception.getMessage());
    }

}