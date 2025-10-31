package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Compass Test")
class CompassTest {

    @Test
    @DisplayName("Test toString() returns correct character representation")
    void testToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @DisplayName("Get Direction Test")
    @Test
    void getDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    @DisplayName("Test toString() consistency with getDirection()")
    void testToString1() {
        for (Compass compass : Compass.values()) {
            assertEquals(String.valueOf(compass.getDirection()), compass.toString());
        }
    }

    @Test
    @DisplayName("Test charToCompass() conversion for valid directions")
    void charToCompass() {
        assertEquals(Compass.NORTH, Compass.charToCompass('n'));
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
        assertEquals(Compass.EAST, Compass.charToCompass('e'));
        assertEquals(Compass.WEST, Compass.charToCompass('o'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('u'));
    }

    @Test
    @DisplayName("Test charToCompass() returns UNKNOWN for invalid characters")
    void testCharToCompassInvalidCharacters() {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('N'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('a'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
    }

    @Test
    @DisplayName("Test values() returns all enum constants")
    void values() {
        Compass[] compassValues = Compass.values();
        assertNotNull(compassValues);
        assertEquals(5, compassValues.length);
        assertEquals(Compass.NORTH, compassValues[0]);
        assertEquals(Compass.SOUTH, compassValues[1]);
        assertEquals(Compass.EAST, compassValues[2]);
        assertEquals(Compass.WEST, compassValues[3]);
        assertEquals(Compass.UNKNOWN, compassValues[4]);
    }

    @Test
    @DisplayName("Test valueOf() returns correct enum constant")
    void valueOf() {
        assertEquals(Compass.NORTH, Compass.valueOf("NORTH"));
        assertEquals(Compass.SOUTH, Compass.valueOf("SOUTH"));
        assertEquals(Compass.EAST, Compass.valueOf("EAST"));
        assertEquals(Compass.WEST, Compass.valueOf("WEST"));
        assertEquals(Compass.UNKNOWN, Compass.valueOf("UNKNOWN"));
    }

    @Test
    @DisplayName("Test valueOf() throws exception for invalid name")
    void testValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Compass.valueOf("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> Compass.valueOf("north"));
        assertThrows(IllegalArgumentException.class, () -> Compass.valueOf(""));
    }

    @Test
    @DisplayName("Test valueOf() throws exception for null")
    void testValueOfNull() {
        assertThrows(NullPointerException.class, () -> Compass.valueOf(null));
    }

    @Test
    @DisplayName("Test each compass direction has unique character")
    void testUniqueDirectionCharacters() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());

        // Verify no duplicates
        assertNotEquals(Compass.NORTH.getDirection(), Compass.SOUTH.getDirection());
        assertNotEquals(Compass.NORTH.getDirection(), Compass.EAST.getDirection());
        assertNotEquals(Compass.NORTH.getDirection(), Compass.WEST.getDirection());
        assertNotEquals(Compass.SOUTH.getDirection(), Compass.EAST.getDirection());
        assertNotEquals(Compass.SOUTH.getDirection(), Compass.WEST.getDirection());
        assertNotEquals(Compass.EAST.getDirection(), Compass.WEST.getDirection());
    }

    @Test
    @DisplayName("Test round-trip conversion: char -> Compass -> char")
    void testRoundTripConversion() {
        char[] validChars = {'n', 's', 'e', 'o', 'u'};

        for (char ch : validChars) {
            Compass compass = Compass.charToCompass(ch);
            assertEquals(ch, compass.getDirection());
        }
    }
}