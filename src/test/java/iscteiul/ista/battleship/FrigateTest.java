package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    private final Frigate f1 = new Frigate(Compass.NORTH, new Position(5,5));
    private final Frigate f2 = new Frigate(Compass.SOUTH, new Position(5,5));
    private final Frigate f3 = new Frigate(Compass.EAST, new Position(5,5));
    private final Frigate f4 = new Frigate(Compass.WEST, new Position(5,5));

    @Test
    void getSize() {
        assertEquals(4, f1.getSize());
        assertEquals(4, f2.getSize());
        assertEquals(4, f3.getSize());
        assertEquals(4, f4.getSize());
    }
}