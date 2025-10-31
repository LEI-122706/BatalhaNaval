package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    private final Galleon g1 = new Galleon(Compass.NORTH, new Position(5,5));
    private final Galleon g2 = new Galleon(Compass.SOUTH, new Position(5,5));
    private final Galleon g3 = new Galleon(Compass.EAST, new Position(5,5));
    private final Galleon g4 = new Galleon(Compass.WEST, new Position(5,5));

    @Test
    void getSize() {
        assertEquals(5, g1.getSize());
        assertEquals(5, g2.getSize());
        assertEquals(5, g3.getSize());
        assertEquals(5, g4.getSize());

    }

}