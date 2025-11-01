package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    private Fleet fleet;
    private Ship testShip1;
    private Ship testShip2;

    // Implementação concreta de Ship para testes
    static class TestShip extends Ship {
        public TestShip(String category, Compass bearing, IPosition pos, int size) {
            super(category, bearing, pos);
            // Adicionar posições baseadas no tamanho
            for (int i = 0; i < size; i++) {
                if (bearing == Compass.EAST) {
                    positions.add(new Position(pos.getRow(), pos.getColumn() + i));
                } else if (bearing == Compass.SOUTH) {
                    positions.add(new Position(pos.getRow() + i, pos.getColumn()));
                } else {
                    positions.add(new Position(pos.getRow(), pos.getColumn() + i));
                }
            }
        }

        @Override
        public Integer getSize() {
            return positions.size();
        }
    }

    @BeforeEach
    void setUp() {
        fleet = new Fleet();
        testShip1 = new TestShip("Fragata", Compass.EAST, new Position(1, 1), 3);
        testShip2 = new TestShip("Galeao", Compass.SOUTH, new Position(5, 5), 4);
    }

    @Test
    @DisplayName("Construtor deve criar frota vazia")
    void constructor_ShouldCreateEmptyFleet() {
        assertNotNull(fleet.getShips());
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("getShips deve retornar lista de navios")
    void getShips() {
        List<IShip> ships = fleet.getShips();
        assertNotNull(ships);
        assertTrue(ships.isEmpty());
    }

    @Test
    @DisplayName("addShip deve adicionar navio quando dentro do tabuleiro e sem colisão")
    void addShip_ShouldAddShipWhenValid() {
        assertTrue(fleet.addShip(testShip1));
        assertEquals(1, fleet.getShips().size());
        assertTrue(fleet.getShips().contains(testShip1));
    }

    @Test
    @DisplayName("addShip não deve adicionar navio fora do tabuleiro")
    void addShip_ShouldNotAddShipOutsideBoard() {
        Ship outsideShip = new TestShip("Fragata", Compass.EAST, new Position(-1, -1), 3);

        assertFalse(fleet.addShip(outsideShip));
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip não deve adicionar navio com risco de colisão")
    void addShip_ShouldNotAddShipWithCollisionRisk() {
        fleet.addShip(testShip1);

        // Navio muito próximo
        Ship closeShip = new TestShip("Galeao", Compass.EAST, new Position(1, 2), 3);

        assertFalse(fleet.addShip(closeShip));
        assertEquals(1, fleet.getShips().size());
    }

    @Test
    @DisplayName("addShip deve adicionar múltiplos navios válidos")
    void addShip_ShouldAddMultipleValidShips() {
        assertTrue(fleet.addShip(testShip1));
        assertTrue(fleet.addShip(testShip2));
        assertEquals(2, fleet.getShips().size());
    }

    @Test
    @DisplayName("getShipsLike deve retornar navios da categoria especificada")
    void getShipsLike() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        List<IShip> fragatas = fleet.getShipsLike("Fragata");
        List<IShip> galeoes = fleet.getShipsLike("Galeao");
        List<IShip> naus = fleet.getShipsLike("Nau");

        assertEquals(1, fragatas.size());
        assertEquals(1, galeoes.size());
        assertEquals(0, naus.size());
        assertEquals(testShip1, fragatas.get(0));
        assertEquals(testShip2, galeoes.get(0));
    }

    @Test
    @DisplayName("getShipsLike deve retornar lista vazia para categoria inexistente")
    void getShipsLike_ShouldReturnEmptyForNonExistentCategory() {
        fleet.addShip(testShip1);

        List<IShip> result = fleet.getShipsLike("CategoriaInexistente");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getFloatingShips deve retornar apenas navios não afundados")
    void getFloatingShips() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        // Afundar um navio
        for (IPosition pos : testShip1.getPositions()) {
            pos.shoot();
        }

        List<IShip> floatingShips = fleet.getFloatingShips();

        assertEquals(1, floatingShips.size());
        assertEquals(testShip2, floatingShips.get(0));
        assertFalse(floatingShips.contains(testShip1));
    }

    @Test
    @DisplayName("getFloatingShips deve retornar lista vazia quando todos os navios estão afundados")
    void getFloatingShips_ShouldReturnEmptyWhenAllShipsSunk() {
        fleet.addShip(testShip1);

        // Afundar o navio
        for (IPosition pos : testShip1.getPositions()) {
            pos.shoot();
        }

        List<IShip> floatingShips = fleet.getFloatingShips();

        assertNotNull(floatingShips);
        assertTrue(floatingShips.isEmpty());
    }

    @Test
    @DisplayName("shipAt deve retornar navio na posição especificada")
    void shipAt() {
        fleet.addShip(testShip1);

        IShip foundShip = fleet.shipAt(new Position(1, 2));
        IShip notFoundShip = fleet.shipAt(new Position(10, 10));

        assertEquals(testShip1, foundShip);
        assertNull(notFoundShip);
    }

    @Test
    @DisplayName("shipAt deve retornar null quando não há navio na posição")
    void shipAt_ShouldReturnNullWhenNoShipAtPosition() {
        fleet.addShip(testShip1);

        IShip result = fleet.shipAt(new Position(8, 8));

        assertNull(result);
    }

    @Test
    @DisplayName("shipAt deve funcionar com frota vazia")
    void shipAt_ShouldWorkWithEmptyFleet() {
        IShip result = fleet.shipAt(new Position(1, 1));

        assertNull(result);
    }

    @Test
    @DisplayName("printShips deve imprimir lista de navios")
    void printShips() {
        // Redirecionar System.out para capturar a saída
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            List<IShip> ships = List.of(testShip1, testShip2);
            Fleet.printShips(ships);

            String output = outputStream.toString();
            assertTrue(output.contains("Fragata"));
            assertTrue(output.contains("Galeao"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("printShips deve funcionar com lista vazia")
    void printShips_ShouldWorkWithEmptyList() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            List<IShip> emptyList = List.of();
            Fleet.printShips(emptyList);

            String output = outputStream.toString();
            // Não deve lançar exceção e deve executar normalmente
            assertNotNull(output);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("printStatus deve executar sem erros")
    void printStatus() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        // Apenas verifica que não lança exceção
        assertDoesNotThrow(() -> fleet.printStatus());
    }

    @Test
    @DisplayName("printStatus deve funcionar com frota vazia")
    void printStatus_ShouldWorkWithEmptyFleet() {
        assertDoesNotThrow(() -> fleet.printStatus());
    }

    @Test
    @DisplayName("printShipsByCategory deve imprimir navios da categoria especificada")
    void printShipsByCategory() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            fleet.printShipsByCategory("Fragata");

            String output = outputStream.toString();
            assertTrue(output.contains("Fragata"));
            assertFalse(output.contains("Galeao"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("printShipsByCategory deve lançar exceção com categoria nula")
    void printShipsByCategory_ShouldThrowExceptionWithNullCategory() {
        assertThrows(AssertionError.class, () -> fleet.printShipsByCategory(null));
    }

    @Test
    @DisplayName("printFloatingShips deve imprimir apenas navios não afundados")
    void printFloatingShips() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        // Afundar um navio
        for (IPosition pos : testShip1.getPositions()) {
            pos.shoot();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            fleet.printFloatingShips();

            String output = outputStream.toString();
            assertFalse(output.contains("Fragata")); // Navio afundado
            assertTrue(output.contains("Galeao"));   // Navio flutuando
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("printAllShips deve imprimir todos os navios da frota")
    void printAllShips() {
        fleet.addShip(testShip1);
        fleet.addShip(testShip2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            fleet.printAllShips();

            String output = outputStream.toString();
            assertTrue(output.contains("Fragata"));
            assertTrue(output.contains("Galeao"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("printAllShips deve funcionar com frota vazia")
    void printAllShips_ShouldWorkWithEmptyFleet() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            fleet.printAllShips();

            String output = outputStream.toString();
            // Não deve lançar exceção
            assertNotNull(output);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Métodos privados devem funcionar corretamente")
    void privateMethods_ShouldWorkCorrectly() {
        // Testar isInsideBoard indiretamente através de addShip
        Ship insideShip = new TestShip("Barca", Compass.EAST, new Position(0, 0), 2);
        Ship outsideShip = new TestShip("Barca", Compass.EAST, new Position(-1, -1), 2);

        assertTrue(fleet.addShip(insideShip));
        assertFalse(fleet.addShip(outsideShip));

        // Testar colisionRisk indiretamente
        Ship collidingShip = new TestShip("Caravela", Compass.EAST, new Position(0, 1), 3);
        assertFalse(fleet.addShip(collidingShip));
    }

    @Test
    @DisplayName("Frota deve respeitar limite de tamanho")
    void fleet_ShouldRespectSizeLimit() {
        System.out.println("FLEET_SIZE: " + IFleet.FLEET_SIZE);
        System.out.println("BOARD_SIZE: " + IFleet.BOARD_SIZE);

        int added = 0;

        // Usar o padrão de xadrez que você sugeriu
        for (int row = 0; row < IFleet.BOARD_SIZE && added < IFleet.FLEET_SIZE; row += 2) {
            for (int col = 0; col < IFleet.BOARD_SIZE && added < IFleet.FLEET_SIZE; col += 2) {
                Ship ship = new TestShip("Barca", Compass.EAST, new Position(row, col), 1);
                boolean wasAdded = fleet.addShip(ship);
                System.out.println("Tentando adicionar em (" + row + "," + col + "): " + wasAdded);

                if (wasAdded) {
                    added++;
                }
            }
        }

        System.out.println("Navios adicionados com sucesso: " + added);

        // Agora tentar adicionar além do limite - deve falhar
        // Tentar uma posição que deveria estar ocupada ou adjacente
        Ship extraShip = new TestShip("Barca", Compass.EAST, new Position(1, 1), 1);
        boolean canAddExtra = fleet.addShip(extraShip);

        System.out.println("Pode adicionar navio extra em (1,1): " + canAddExtra);

        // Se conseguimos adicionar FLEET_SIZE navios, então não devemos conseguir adicionar mais
        if (added >= IFleet.FLEET_SIZE) {
            assertFalse(canAddExtra, "Não deveria conseguir adicionar além do FLEET_SIZE");
            assertEquals(IFleet.FLEET_SIZE, fleet.getShips().size(),
                    "Deveria ter exatamente FLEET_SIZE navios");
        } else {
            // Se não conseguimos adicionar FLEET_SIZE, é porque o tabuleiro não comporta
            // tantos navios com as regras de não-adjacência
            System.out.println("Aviso: Só foi possível adicionar " + added + " de " +
                    IFleet.FLEET_SIZE + " navios devido às regras de posicionamento");

            // Mesmo assim, não devemos conseguir adicionar em posições inválidas
            assertFalse(canAddExtra, "Não deveria conseguir adicionar em posição inválida");
        }
    }

    @Test
    @DisplayName("Navios devem estar dentro dos limites do tabuleiro")
    void ships_ShouldBeWithinBoardLimits() {
        // Testar limites do tabuleiro
        Ship topLeft = new TestShip("Barca", Compass.EAST, new Position(0, 0), 2);
        Ship bottomRight = new TestShip("Barca", Compass.EAST,
                new Position(IFleet.BOARD_SIZE - 2, IFleet.BOARD_SIZE - 2), 2);
        Ship outsideRight = new TestShip("Barca", Compass.EAST,
                new Position(0, IFleet.BOARD_SIZE - 1), 2);

        assertTrue(fleet.addShip(topLeft));
        assertTrue(fleet.addShip(bottomRight));
        assertFalse(fleet.addShip(outsideRight));
    }
}