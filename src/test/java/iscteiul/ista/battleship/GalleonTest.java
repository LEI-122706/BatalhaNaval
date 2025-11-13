package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Galleon's test class (LEI-122706)")
class GalleonTest {

    private static final int EXPECTED_SIZE = 5;

    private final Galleon g1 = new Galleon(Compass.NORTH, new Position(5,5));
    private final Galleon g2 = new Galleon(Compass.SOUTH, new Position(5,5));
    private final Galleon g3 = new Galleon(Compass.EAST, new Position(5,5));
    private final Galleon g4 = new Galleon(Compass.WEST, new Position(5,5));

    @Test
    @DisplayName("getSize returns the correct size for galleon")
    void getSize() {
        assertEquals(EXPECTED_SIZE, g1.getSize());
        assertEquals(EXPECTED_SIZE, g2.getSize());
        assertEquals(EXPECTED_SIZE, g3.getSize());
        assertEquals(EXPECTED_SIZE, g4.getSize());
    }

    @Test
    @DisplayName("fillNorth creates T shaped position for NORTH bearing")
    void fillNorth(){
        assertTrue(g1.getPositions().contains(new Position(5,5)));
        assertTrue(g1.getPositions().contains(new Position(5,6)));
        assertTrue(g1.getPositions().contains(new Position(5,7)));
        assertTrue(g1.getPositions().contains(new Position(6,6)));
        assertTrue(g1.getPositions().contains(new Position(7,6)));
    }

    @Test
    @DisplayName("fillSouth creates T shaped position for SOUTH bearing")
    void fillSouth(){
        assertTrue(g2.getPositions().contains(new Position(5,5)));
        assertTrue(g2.getPositions().contains(new Position(6,5)));
        assertTrue(g2.getPositions().contains(new Position(7,4)));
        assertTrue(g2.getPositions().contains(new Position(7,5)));
        assertTrue(g2.getPositions().contains(new Position(7,6)));
    }

    @Test
    @DisplayName("fillWest creates T shaped position for WEST bearing")
    void fillWest(){
        assertTrue(g4.getPositions().contains(new Position(5,5)));
        assertTrue(g4.getPositions().contains(new Position(6,5)));
        assertTrue(g4.getPositions().contains(new Position(6,6)));
        assertTrue(g4.getPositions().contains(new Position(6,7)));
        assertTrue(g4.getPositions().contains(new Position(7,5)));
    }

    @Test
    @DisplayName("fillEast creates T shaped position for EAST bearing")
    void fillEast(){
        assertTrue(g3.getPositions().contains(new Position(5,5)));
        assertTrue(g3.getPositions().contains(new Position(6,3)));
        assertTrue(g3.getPositions().contains(new Position(6,4)));
        assertTrue(g3.getPositions().contains(new Position(6,5)));
        assertTrue(g3.getPositions().contains(new Position(7,5)));
    }

    @Test
    @DisplayName("Constructor throws exception for null bearing")
    void constructorThrowsForNullBearing() {
        Throwable exception = assertThrows(Throwable.class, () ->
                new Galleon(null, new Position(5, 5))
        );
        assertTrue(exception instanceof NullPointerException || exception instanceof AssertionError);
        if (exception.getMessage() != null) {
            assertEquals("ERROR! invalid bearing for the galleon", exception.getMessage());
        }
    }

    @Test
    @DisplayName("Constructor throws exception for invalid bearing")
    void constructorThrowsForInvalidBearing(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Galleon(Compass.UNKNOWN, new Position(5, 5)));
        assertEquals("ERROR! invalid bearing for the galleon", exception.getMessage());
    }

    @Test
    @DisplayName("All fill methods result in exactly SIZE positions")
    void allFillMethodsSizeCheck() {
        assertEquals(EXPECTED_SIZE, g1.getPositions().size());
        assertEquals(EXPECTED_SIZE, g2.getPositions().size());
        assertEquals(EXPECTED_SIZE, g3.getPositions().size());
        assertEquals(EXPECTED_SIZE, g4.getPositions().size());
    }

}