package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Position Test")
class PositionTest {

    private Position position;

    @BeforeEach
    void setUp() {
        position = new Position(5, 7);
    }

    @Test
    @DisplayName("Test getRow() returns correct row")
    void getRow() {
        assertEquals(5, position.getRow());
        
        Position position2 = new Position(0, 0);
        assertEquals(0, position2.getRow());
        
        Position position3 = new Position(9, 3);
        assertEquals(9, position3.getRow());
    }

    @Test
    @DisplayName("Test getColumn() returns correct column")
    void getColumn() {
        assertEquals(7, position.getColumn());
        
        Position position2 = new Position(0, 0);
        assertEquals(0, position2.getColumn());
        
        Position position3 = new Position(3, 9);
        assertEquals(9, position3.getColumn());
    }

    @Test
    @DisplayName("Test hashCode() consistency")
    void testHashCode() {
        Position position2 = new Position(5, 7);
        assertEquals(position.hashCode(), position2.hashCode(), "Positions with same coordinates should have same hashCode");
        
        Position position3 = new Position(4, 7);
        assertNotEquals(position.hashCode(), position3.hashCode(), "Positions with different coordinates should have different hashCode");
    }

    @Test
    @DisplayName("Test equals() with same object")
    void testEqualsSameObject() {
        assertTrue(position.equals(position));
    }

    @Test
    @DisplayName("Test equals() with equal positions")
    void testEqualsEqualPositions() {
        Position position2 = new Position(5, 7);
        assertTrue(position.equals(position2));
        assertTrue(position2.equals(position));
    }

    @Test
    @DisplayName("Test equals() with different positions")
    void testEqualsDifferentPositions() {
        Position position2 = new Position(5, 6);
        assertFalse(position.equals(position2));
        
        Position position3 = new Position(4, 7);
        assertFalse(position.equals(position3));
        
        Position position4 = new Position(3, 4);
        assertFalse(position.equals(position4));
    }

    @Test
    @DisplayName("Test equals() with null")
    void testEqualsNull() {
        assertFalse(position.equals(null));
    }

    @Test
    @DisplayName("Test equals() with different object type")
    void testEqualsDifferentType() {
        assertFalse(position.equals("Not a Position"));
        assertFalse(position.equals(5));
    }

    @Test
    @DisplayName("Test isAdjacentTo() with same position")
    void isAdjacentToSamePosition() {
        Position same = new Position(5, 7);
        assertTrue(position.isAdjacentTo(same));
    }

    @Test
    @DisplayName("Test isAdjacentTo() with horizontally adjacent positions")
    void isAdjacentToHorizontal() {
        Position left = new Position(5, 6);
        Position right = new Position(5, 8);
        
        assertTrue(position.isAdjacentTo(left));
        assertTrue(position.isAdjacentTo(right));
    }

    @Test
    @DisplayName("Test isAdjacentTo() with vertically adjacent positions")
    void isAdjacentToVertical() {
        Position above = new Position(4, 7);
        Position below = new Position(6, 7);
        
        assertTrue(position.isAdjacentTo(above));
        assertTrue(position.isAdjacentTo(below));
    }

    @Test
    @DisplayName("Test isAdjacentTo() with diagonally adjacent positions")
    void isAdjacentToDiagonal() {
        Position topLeft = new Position(4, 6);
        Position topRight = new Position(4, 8);
        Position bottomLeft = new Position(6, 6);
        Position bottomRight = new Position(6, 8);
        
        assertTrue(position.isAdjacentTo(topLeft));
        assertTrue(position.isAdjacentTo(topRight));
        assertTrue(position.isAdjacentTo(bottomLeft));
        assertTrue(position.isAdjacentTo(bottomRight));
    }

    @Test
    @DisplayName("Test isAdjacentTo() with non-adjacent positions")
    void isAdjacentToNonAdjacent() {
        Position farAway = new Position(8, 9);
        Position twoRowsAway = new Position(7, 7);
        Position twoColumnsAway = new Position(5, 9);
        
        assertFalse(position.isAdjacentTo(farAway));
        assertFalse(position.isAdjacentTo(twoRowsAway));
        assertFalse(position.isAdjacentTo(twoColumnsAway));
    }

    @Test
    @DisplayName("Test occupy() sets position as occupied")
    void occupy() {
        assertFalse(position.isOccupied(), "Position should not be occupied initially");
        position.occupy();
        assertTrue(position.isOccupied(), "Position should be occupied after occupy()");
    }

    @Test
    @DisplayName("Test shoot() sets position as hit")
    void shoot() {
        assertFalse(position.isHit(), "Position should not be hit initially");
        position.shoot();
        assertTrue(position.isHit(), "Position should be hit after shoot()");
    }

    @Test
    @DisplayName("Test isOccupied() initial state")
    void isOccupiedInitialState() {
        Position newPosition = new Position(3, 4);
        assertFalse(newPosition.isOccupied(), "New position should not be occupied");
    }

    @Test
    @DisplayName("Test isOccupied() after occupation")
    void isOccupiedAfterOccupation() {
        position.occupy();
        assertTrue(position.isOccupied());
    }

    @Test
    @DisplayName("Test isHit() initial state")
    void isHitInitialState() {
        Position newPosition = new Position(3, 4);
        assertFalse(newPosition.isHit(), "New position should not be hit");
    }

    @Test
    @DisplayName("Test isHit() after shooting")
    void isHitAfterShooting() {
        position.shoot();
        assertTrue(position.isHit());
    }

    @Test
    @DisplayName("Test toString() returns correct format")
    void testToString() {
        assertEquals("Linha = 5 Coluna = 7", position.toString());
        
        Position position2 = new Position(0, 0);
        assertEquals("Linha = 0 Coluna = 0", position2.toString());
        
        Position position3 = new Position(9, 9);
        assertEquals("Linha = 9 Coluna = 9", position3.toString());
    }

    @Test
    @DisplayName("Test position state independence (occupy and shoot)")
    void testStateIndependence() {
        assertFalse(position.isOccupied());
        assertFalse(position.isHit());
        
        position.occupy();
        assertTrue(position.isOccupied());
        assertFalse(position.isHit(), "Occupying should not affect hit status");
        
        position.shoot();
        assertTrue(position.isOccupied(), "Shooting should not affect occupied status");
        assertTrue(position.isHit());
    }

    @Test
    @DisplayName("Test position can be shot then occupied")
    void testShootThenOccupy() {
        position.shoot();
        assertTrue(position.isHit());
        assertFalse(position.isOccupied());
        
        position.occupy();
        assertTrue(position.isHit());
        assertTrue(position.isOccupied());
    }

    @Test
    @DisplayName("Test constructor with negative coordinates")
    void testConstructorNegativeCoordinates() {
        Position negativePosition = new Position(-1, -5);
        assertEquals(-1, negativePosition.getRow());
        assertEquals(-5, negativePosition.getColumn());
    }

    @Test
    @DisplayName("Test equals() symmetry")
    void testEqualsSymmetry() {
        Position position2 = new Position(5, 7);
        assertTrue(position.equals(position2));
        assertTrue(position2.equals(position));
    }

    @Test
    @DisplayName("Test equals() transitivity")
    void testEqualsTransitivity() {
        Position position2 = new Position(5, 7);
        Position position3 = new Position(5, 7);
        
        assertTrue(position.equals(position2));
        assertTrue(position2.equals(position3));
        assertTrue(position.equals(position3));
    }

    @Test
    @DisplayName("Test isAdjacentTo() boundary cases")
    void testIsAdjacentToBoundary() {
        Position origin = new Position(0, 0);
        
        // Adjacent to origin
        assertTrue(origin.isAdjacentTo(new Position(0, 0)));
        assertTrue(origin.isAdjacentTo(new Position(0, 1)));
        assertTrue(origin.isAdjacentTo(new Position(1, 0)));
        assertTrue(origin.isAdjacentTo(new Position(1, 1)));
        
        // Not adjacent to origin
        assertFalse(origin.isAdjacentTo(new Position(2, 2)));
        assertFalse(origin.isAdjacentTo(new Position(0, 2)));
        assertFalse(origin.isAdjacentTo(new Position(2, 0)));
    }
}