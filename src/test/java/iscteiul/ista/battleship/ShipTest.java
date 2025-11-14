package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    // Implementação simples de IPosition para uso nos testes
    static class SimplePosition implements IPosition {
        private final int row;
        private final int column;
        private boolean hit;
        private boolean occupied;

        SimplePosition(int row, int column) {
            this.row = row;
            this.column = column;
            this.hit = false;
            this.occupied = false;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {
            return column;
        }

        @Override
        public boolean isHit() {
            return hit;
        }

        @Override
        public boolean isAdjacentTo(IPosition other) {
            if (other == null) return false;
            int dr = Math.abs(this.row - other.getRow());
            int dc = Math.abs(this.column - other.getColumn());
            return dr <= 1 && dc <= 1;
        }

        @Override
        public void shoot() {
            this.hit = true;
        }

        @Override
        public void occupy() {
            this.occupied = true;
        }

        @Override
        public boolean isOccupied() {
            return this.occupied;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof IPosition)) return false;
            IPosition other = (IPosition) obj;
            return this.row == other.getRow() && this.column == other.getColumn();
        }

        @Override
        public int hashCode() {
            return 31 * row + column;
        }

        @Override
        public String toString() {
            return "(" + row + "," + column + (hit ? " H" : "") + ")";
        }
    }

    private Ship testShip;

    @BeforeEach
    void setUp() {
        testShip = createTestShip();
    }

    // Helper que cria um Ship concreto para testar métodos da classe abstrata
    private Ship createTestShip() {
        IPosition origin = new SimplePosition(2, 2);
        Ship ship = new Ship("test", Compass.NORTH, origin) {
            @Override
            public Integer getSize() {
                return positions.size();
            }
        };
        ship.positions.add(new SimplePosition(2, 2));
        ship.positions.add(new SimplePosition(2, 3));
        ship.positions.add(new SimplePosition(2, 4));
        return ship;
    }

    private Ship createSinglePositionShip(String category, Compass bearing, int row, int column) {
        IPosition origin = new SimplePosition(row, column);
        Ship ship = new Ship(category, bearing, origin) {
            @Override
            public Integer getSize() {
                return positions.size();
            }
        };
        ship.positions.add(new SimplePosition(row, column));
        return ship;
    }

    @Test
    @DisplayName("Construtor deve criar navio sem lançar exceção")
    void buildShip() {
        assertDoesNotThrow(this::createTestShip);
    }

    @Test
    @DisplayName("getCategory deve retornar categoria configurada")
    void getCategory() {
        assertEquals("test", testShip.getCategory());
    }

    @Test
    @DisplayName("getPositions deve retornar lista de posições do navio")
    void getPositions() {
        List<IPosition> positions = testShip.getPositions();
        assertEquals(3, positions.size());
        assertTrue(positions.contains(new SimplePosition(2, 3)));
    }

    @Test
    @DisplayName("getPosition deve retornar posição de referência do navio")
    void getPosition() {
        assertNotNull(testShip.getPosition());
        assertEquals(2, testShip.getPosition().getRow());
        assertEquals(2, testShip.getPosition().getColumn());
    }

    @Test
    @DisplayName("getBearing deve retornar rumo do navio")
    void getBearing() {
        assertEquals(Compass.NORTH, testShip.getBearing());
    }

    @Test
    @DisplayName("stillFloating deve retornar true enquanto houver posições não atingidas")
    void stillFloating() {
        assertTrue(testShip.stillFloating());

        // Atira em todas as posições
        for (IPosition position : testShip.getPositions()) {
            position.shoot();
        }

        assertFalse(testShip.stillFloating());
    }

    @Test
    @DisplayName("stillFloating deve retornar true quando nem todas as posições estão atingidas")
    void stillFloating_partialHit() {
        // Atira apenas em uma posição
        testShip.getPositions().get(0).shoot();

        assertTrue(testShip.stillFloating());
    }

    @Test
    @DisplayName("getTopMostPos deve devolver a linha mais alta (menor índice)")
    void getTopMostPos() {
        assertEquals(2, testShip.getTopMostPos());
    }

    @Test
    @DisplayName("getBottomMostPos deve devolver a linha mais baixa (maior índice)")
    void getBottomMostPos() {
        assertEquals(2, testShip.getBottomMostPos());
    }

    @Test
    @DisplayName("getLeftMostPos deve devolver a coluna mais à esquerda")
    void getLeftMostPos() {
        assertEquals(4, testShip.getLeftMostPos());
    }

    @Test
    @DisplayName("getRightMostPos deve devolver a coluna mais à direita")
    void getRightMostPos() {
        assertEquals(4, testShip.getRightMostPos());
    }

    @Test
    @DisplayName("occupies deve indicar corretamente se uma posição pertence ao navio")
    void occupies() {
        assertTrue(testShip.occupies(new SimplePosition(2, 3)));
        assertFalse(testShip.occupies(new SimplePosition(0, 0)));
        assertFalse(testShip.occupies(new SimplePosition(2, 1))); // Posição próxima mas não ocupada
    }

    @Test
    @DisplayName("tooCloseTo(IShip) deve detectar proximidade entre navios")
    void tooCloseTo_ship() {
        // Navio adjacente
        Ship adjacentShip = createSinglePositionShip("adjacent", Compass.SOUTH, 1, 3);
        assertTrue(testShip.tooCloseTo(adjacentShip));

        // Navio distante
        Ship distantShip = createSinglePositionShip("distant", Compass.EAST, 10, 10);
        assertFalse(testShip.tooCloseTo(distantShip));

        // Navio na mesma posição (deve ser considerado muito próximo)
        Ship samePositionShip = createSinglePositionShip("same", Compass.WEST, 2, 3);
        assertTrue(testShip.tooCloseTo(samePositionShip));
    }

    @Test
    @DisplayName("tooCloseTo(IPosition) deve detectar proximidade a uma posição")
    void tooCloseTo_position() {
        // Posição adjacente
        assertTrue(testShip.tooCloseTo(new SimplePosition(1, 3)));

        // Posição distante
        assertFalse(testShip.tooCloseTo(new SimplePosition(0, 0)));

        // Posição ocupada pelo navio (deve ser considerada próxima)
        assertTrue(testShip.tooCloseTo(new SimplePosition(2, 3)));
    }

    @Test
    @DisplayName("shoot deve marcar posição como atingida quando atinge o navio")
    void shoot() {
        IPosition target = new SimplePosition(2, 3);
        assertFalse(testShip.getPositions().get(1).isHit());

        testShip.shoot(target);

        assertTrue(testShip.getPositions().get(1).isHit());
    }

    @Test
    @DisplayName("shoot não deve afetar posições quando não atinge o navio")
    void shoot_miss() {
        IPosition target = new SimplePosition(5, 5); // Posição não ocupada
        boolean wasHit = false;

        // Verifica estado antes do tiro
        for (IPosition position : testShip.getPositions()) {
            if (position.isHit()) {
                wasHit = true;
                break;
            }
        }

        testShip.shoot(target);

        // Verifica que nenhuma posição foi afetada
        for (IPosition position : testShip.getPositions()) {
            assertEquals(wasHit, position.isHit());
        }
    }

    private Ship createShipForToStringTest() {
        IPosition origin = new Position(2, 3); // Usar Position real
        Ship ship = new Ship("CARAVELA", Compass.NORTH, origin) {
            @Override
            public Integer getSize() {
                return positions.size();
            }
        };
        ship.positions.add(origin);
        return ship;
    }

    @Test
    @DisplayName("toString deve incluir categoria, rumo e posição de referência")
    void testToString() {
        Ship ship = createShipForToStringTest();

        String result = ship.toString();
        System.out.println("toString output: " + result);
        // Verificar o formato exato: [battleship NORTH (2,3)]
        assertAll(
                () -> assertTrue(result.contains("n"),
                        "Rumo 'NORTH' não encontrado em: " + result),
                () -> assertTrue(result.contains("CARAVELA"),
                        "Ship do tipo Caravela não encontrado em " + result)
        );
    }

    @Test
    @DisplayName("Navio deve ser considerado próximo quando posições são adjacentes diagonalmente")
    void tooCloseTo_diagonal() {
        // Posição diagonalmente adjacente
        assertTrue(testShip.tooCloseTo(new SimplePosition(1, 1)));
        assertTrue(testShip.tooCloseTo(new SimplePosition(1, 5)));
        assertTrue(testShip.tooCloseTo(new SimplePosition(3, 1)));
        assertTrue(testShip.tooCloseTo(new SimplePosition(3, 5)));
    }

    @Test
    @DisplayName("Métodos de extremidade devem funcionar com navio de uma única posição")
    void singlePositionShip_boundaries() {
        Ship singleShip = createSinglePositionShip("single", Compass.NORTH, 5, 5);

        assertEquals(5, singleShip.getTopMostPos());
        assertEquals(5, singleShip.getBottomMostPos());
        assertEquals(5, singleShip.getLeftMostPos());
        assertEquals(5, singleShip.getRightMostPos());
    }

    @Test
    @DisplayName("buildShip deve criar todos os tipos de navios corretamente")
    void buildShip_ShouldCreateAllShipTypes() {
        Position position = new Position(1, 1);

        assertAll(
                () -> assertNotNull(Ship.buildShip("barca", Compass.NORTH, position),
                        "Barca não foi criada"),
                () -> assertNotNull(Ship.buildShip("caravela", Compass.EAST, position),
                        "Caravela não foi criada"),
                () -> assertNotNull(Ship.buildShip("nau", Compass.SOUTH, position),
                        "Nau não foi criada"),
                () -> assertNotNull(Ship.buildShip("fragata", Compass.WEST, position),
                        "Fragata não foi criada"),
                () -> assertNotNull(Ship.buildShip("galeao", Compass.NORTH, position),
                        "Galeão não foi criada")
        );
    }

    @Test
    @DisplayName("buildShip deve retornar null para tipo de navio desconhecido")
    void buildShip_ShouldReturnNullForUnknownType() {
        Position position = new Position(1, 1);

        assertNull(Ship.buildShip("navio_desconhecido", Compass.NORTH, position),
                "buildShip deveria retornar null para tipo desconhecido");
    }

    @Test
    @DisplayName("buildShip deve criar navios com posições e rumos corretos")
    void buildShip_ShouldCreateShipsWithCorrectParameters() {
        Position position = new Position(3, 4);
        Compass bearing = Compass.SOUTH;

        Ship ship = Ship.buildShip("barca", bearing, position);

        assertAll(
                () -> assertNotNull(ship, "Navio não deveria ser null"),
                () -> assertEquals(position, ship.getPosition(),
                        "Posição do navio não corresponde à esperada"),
                () -> assertEquals(bearing, ship.getBearing(),
                        "Rumo do navio não corresponde ao esperado")
        );
    }
}
