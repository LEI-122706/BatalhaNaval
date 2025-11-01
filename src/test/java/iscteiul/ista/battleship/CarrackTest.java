package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrack's test class (LEI-122676)")
class CarrackTest {

    private final Carrack n = new Carrack(Compass.NORTH, new Position(5, 5));
    private final Carrack e = new Carrack(Compass.EAST, new Position(5, 5));
    private final Carrack s = new Carrack(Compass.SOUTH, new Position(5, 5));
    private final Carrack w = new Carrack(Compass.WEST, new Position(5, 5));

    @Test
    void getSize() {
        assertEquals(3, n.getSize());
        assertEquals(3, e.getSize());
        assertEquals(3, s.getSize());
        assertEquals(3, w.getSize());
    }
}