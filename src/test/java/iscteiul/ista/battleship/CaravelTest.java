package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Caravel's test class (LEI-122676)")
class CaravelTest {

    private final Caravel n = new Caravel(Compass.NORTH, new Position(5, 5));
    private final Caravel e = new Caravel(Compass.EAST, new Position(5, 5));
    private final Caravel s = new Caravel(Compass.SOUTH, new Position(5, 5));
    private final Caravel w = new Caravel(Compass.WEST, new Position(5, 5));

    @Test
    void getSize() {
        assertEquals(2, n.getSize());
        assertEquals(2, e.getSize());
        assertEquals(2, s.getSize());
        assertEquals(2, w.getSize());
    }
}