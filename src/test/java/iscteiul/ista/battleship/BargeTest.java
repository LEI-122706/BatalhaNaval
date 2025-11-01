package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Barge's test class (LEI-122676)")
class BargeTest {

    private final Barge n = new Barge(Compass.NORTH, new Position(5, 5));
    private final Barge e = new Barge(Compass.EAST, new Position(5, 5));
    private final Barge s = new Barge(Compass.SOUTH, new Position(5, 5));
    private final Barge w = new Barge(Compass.WEST, new Position(5, 5));

    @Test
    void getSize() {
        assertEquals(1, n.getSize());
        assertEquals(1, e.getSize());
        assertEquals(1, s.getSize());
        assertEquals(1, w.getSize());
    }
}