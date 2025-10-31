package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    private final Frigate f = new Frigate(Compass.NORTH, new Position(5,5));

    @Test
    void getSize() {
        assertEquals(4, f.getSize());
    }
}