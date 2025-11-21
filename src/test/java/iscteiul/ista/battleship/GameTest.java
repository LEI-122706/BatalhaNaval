package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private final Fleet f = new Fleet();
    private final Game g = new Game(f);

    private final int SIZE = Fleet.BOARD_SIZE;

    private final Position valid1 = new Position(0, 0);
    private final Position valid2 = new Position(SIZE, SIZE);
    private final Position invalid1 = new Position(-1, 0);
    private final Position invalid2 = new Position(SIZE + 1, 0);

    @Test
    void constructorAndGettersTest() {
        assertNotNull(g, "Game instance should be created");
        assertTrue(g.getShots().isEmpty(), "Initial shots list should be empty");
        assertEquals(0, g.getInvalidShots());
        assertEquals(0, g.getRepeatedShots());
        assertEquals(0, g.getHits());
        assertEquals(0, g.getSunkShips());
    }

    @Test
    void getRemainingShipsTest() {
        List<IShip> floatingShips = f.getFloatingShips();
        assertEquals(floatingShips.size(), g.getRemainingShips());
    }

    @Test
    void fireValidShotsTest() {
        assertNull(g.fire(valid1));
        assertEquals(1, g.getShots().size());
        assertEquals(0, g.getInvalidShots());

        assertNull(g.fire(valid2));
        assertEquals(2, g.getShots().size());
        assertEquals(0, g.getInvalidShots());
    }

    @Test
    void fireInvalidShotsTest() {
        g.fire(invalid1);
        g.fire(invalid2);
        assertEquals(0, g.getShots().size());
        assertEquals(2, g.getInvalidShots());
    }

    @Test
    void validShotTest() {
        assertEquals(0, g.getShots().size());
        assertEquals(0, g.getInvalidShots());

        Position colNeg = new Position(0, -1);
        Position colTooBig = new Position(0, SIZE + 1);
        Position bothTooBig = new Position(SIZE + 1, SIZE + 1);
        Position bothNegative = new Position(-1, -1);
        Position rowNeg = new Position(-1, 5);
        Position rowTooBig = new Position(SIZE + 1, 5);

        g.fire(colNeg);
        g.fire(colTooBig);
        g.fire(bothTooBig);
        g.fire(bothNegative);
        g.fire(rowNeg);
        g.fire(rowTooBig);

        assertEquals(0, g.getShots().size());
        assertEquals(6, g.getInvalidShots());
    }

    @Test
    void repeatedShotTest() {
        Fleet fleet = new Fleet();
        Game game = new Game(fleet);

        Position pos = new Position(5, 5);

        game.fire(pos);
        assertEquals(1, game.getShots().size());
        assertEquals(0, game.getRepeatedShots());

        game.fire(pos);
        assertEquals(1, game.getShots().size());
        assertEquals(1, game.getRepeatedShots());

        game.fire(pos);
        assertEquals(1, game.getShots().size());
        assertEquals(2, game.getRepeatedShots());
    }

    @Test
    void fireHitTest() {
        Fleet fleet = new Fleet();
        Position shipPos = new Position(3, 3);

        Ship ship = Ship.buildShip("barca", Compass.NORTH, shipPos);
        fleet.addShip(ship);

        Game game = new Game(fleet);

        IShip result = game.fire(shipPos);
        assertNotNull(result);
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
    }

    @Test
    void fireSinkTest() {
        Fleet fleet = new Fleet();
        Position shipPos = new Position(2, 2);

        Ship ship = Ship.buildShip("caravela", Compass.EAST, shipPos);
        fleet.addShip(ship);

        Game game = new Game(fleet);

        List<IPosition> shipPositions = ship.getPositions();

        IShip result1 = game.fire(shipPositions.get(0));
        assertNull(result1);
        assertEquals(1, game.getHits());
        assertEquals(0, game.getSunkShips());

        IShip result2 = game.fire(shipPositions.get(1));
        assertNotNull(result2);
        assertEquals(ship, result2);
        assertEquals(2, game.getHits());
        assertEquals(1, game.getSunkShips());
    }

    @Test
    void fireMissTest() {
        Fleet fleet = new Fleet();
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(5, 5));
        fleet.addShip(ship);

        Game game = new Game(fleet);

        IShip result = game.fire(new Position(7, 7));

        assertNull(result);
        assertEquals(0, game.getHits());
        assertEquals(1, game.getShots().size());
    }

    @Test
    void printValidShotsTest() {
        g.fire(new Position(0, 0));
        g.fire(new Position(5, 5));

        assertDoesNotThrow(() -> g.printValidShots());
    }

    @Test
    void printFleetTest() {
        assertDoesNotThrow(() -> g.printFleet());
    }

    /**
     * ⭐ NEW TEST — forces coverage of:
     * for (IShip s : fleet.getShips())
     *     shipPositions.addAll(s.getPositions());
     */
    @Test
    void printFleetWithShipsTest() {
        Fleet fleet = new Fleet();
        Ship ship = Ship.buildShip("caravela", Compass.EAST, new Position(2, 2));
        fleet.addShip(ship);

        Game game = new Game(fleet);

        assertDoesNotThrow(game::printFleet);
    }

    @Test
    void printBoardTest() {
        List<IPosition> positions = new ArrayList<>();
        positions.add(new Position(0, 0));
        positions.add(new Position(1, 1));
        positions.add(new Position(2, 2));

        assertDoesNotThrow(() -> g.printBoard(positions, '*'));
        assertDoesNotThrow(() -> g.printBoard(positions, 'O'));
    }

    @Test
    void printBoardEmptyTest() {
        List<IPosition> emptyPositions = new ArrayList<>();
        assertDoesNotThrow(() -> g.printBoard(emptyPositions, '.'));
    }

    @Test
    void multipleHitsAndSinksTest() {
        Fleet fleet = new Fleet();

        Ship ship1 = Ship.buildShip("barca", Compass.NORTH, new Position(1, 1));
        Ship ship2 = Ship.buildShip("barca", Compass.NORTH, new Position(3, 3));
        fleet.addShip(ship1);
        fleet.addShip(ship2);

        Game game = new Game(fleet);

        IShip result1 = game.fire(ship1.getPositions().get(0));
        assertNotNull(result1);
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());

        IShip result2 = game.fire(ship2.getPositions().get(0));
        assertNotNull(result2);
        assertEquals(2, game.getHits());
        assertEquals(2, game.getSunkShips());
    }

    @Test
    void boundaryValidShotsTest() {
        Game game = new Game(new Fleet());

        game.fire(new Position(0, 0));
        game.fire(new Position(0, SIZE));
        game.fire(new Position(SIZE, 0));
        game.fire(new Position(SIZE, SIZE));

        assertEquals(4, game.getShots().size());
        assertEquals(0, game.getInvalidShots());
    }

    @Test
    void mixedShotsScenarioTest() {
        Fleet fleet = new Fleet();
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(4, 4));
        fleet.addShip(ship);

        Game game = new Game(fleet);

        game.fire(new Position(0, 0));
        game.fire(new Position(-1, 0));
        game.fire(ship.getPositions().get(0));
        game.fire(new Position(0, 0));
        game.fire(new Position(SIZE + 1, 5));

        assertEquals(2, game.getShots().size());
        assertEquals(2, game.getInvalidShots());
        assertEquals(1, game.getRepeatedShots());
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
    }

    @Test
    void gettersAfterMultipleOperationsTest() {
        Fleet fleet = new Fleet();
        Game game = new Game(fleet);

        game.fire(new Position(1, 1));
        game.fire(new Position(2, 2));
        game.fire(new Position(3, 3));

        assertEquals(3, game.getShots().size());
        assertEquals(3, game.getShots().get(2).getRow());
        assertEquals(0, game.getInvalidShots());
        assertEquals(0, game.getRepeatedShots());
        assertEquals(0, game.getHits());
        assertEquals(0, game.getSunkShips());
    }

    @Test
    void stillFloatingTest() {
        Fleet fleet = new Fleet();
        Ship ship = Ship.buildShip("nau", Compass.EAST, new Position(5, 5));
        fleet.addShip(ship);

        Game game = new Game(fleet);

        assertNull(game.fire(ship.getPositions().get(0)));
        assertTrue(ship.stillFloating());

        assertNull(game.fire(ship.getPositions().get(1)));
        assertTrue(ship.stillFloating());

        assertNotNull(game.fire(ship.getPositions().get(2)));
        assertFalse(ship.stillFloating());
    }

    @Test
    void fireAllShipTypesTest() {
        Fleet fleet = new Fleet();

        Ship barca = Ship.buildShip("barca", Compass.NORTH, new Position(0, 0));
        Ship caravela = Ship.buildShip("caravela", Compass.EAST, new Position(2, 0));
        Ship nau = Ship.buildShip("nau", Compass.SOUTH, new Position(4, 0));
        Ship fragata = Ship.buildShip("fragata", Compass.WEST, new Position(6, 0));
        Ship galeao = Ship.buildShip("galeao", Compass.NORTH, new Position(0, 3));

        assertNotNull(barca);
        assertNotNull(caravela);
        assertNotNull(nau);
        assertNotNull(fragata);
        assertNotNull(galeao);

        fleet.addShip(barca);

        Game game = new Game(fleet);

        IShip result = game.fire(barca.getPositions().get(0));
        assertNotNull(result);
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
    }

    @Test
    void buildShipInvalidTypeTest() {
        Ship invalidShip = Ship.buildShip("invalid_type", Compass.NORTH, new Position(0, 0));
        assertNull(invalidShip);
    }
}

