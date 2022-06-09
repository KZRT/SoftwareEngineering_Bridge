package view.mainView.CUI;

import controller.GameController;
import model.Card;
import model.Direction;
import model.GameManager;
import model.Player;
import model.cell.BridgeCell;
import model.cell.CardCell;
import model.cell.CellService;
import view.mainView.Index;
import view.mainView.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConsoleView extends Thread implements MainView {
    private static ConsoleView consoleView;
    int minCol, minRow, maxCol, maxRow;
    private final GameManager gameManager;
    private final GameController gameController;
    private final ArrayList<ArrayList<CellService>> mapGrid;
    private int playerCount;

    private ConsoleView(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        HashMap<CellService, Index> cellIndexMap = new HashMap<>();
        gameManager.registerObserver(this);

        ArrayList<CellService> cells = gameManager.getBridgeMap();
        maxRow = 0;
        maxCol = 0;
        minRow = 0;
        minCol = 0;
        int currentRow = 1, currentCol = 1;
        Direction previousDirection = null;
        for (CellService cell : cells) {
            Direction tempDirection = null;
            if (cell.getClass().getSimpleName().equals("EndCell")) {
                tempDirection = previousDirection;
            } else if (cell.getClass().getSimpleName().equals("StartCell")) {
                for (Direction d : Direction.values()) {
                    if (cell.canMove(d)) {
                        previousDirection = d;
                    }
                }
                cellIndexMap.put(cell, new Index(currentRow, currentCol));
                continue;
            } else {
                for (Direction d : Direction.values()) {
                    if (cell.canMove(d)) {
                        if (previousDirection == d.opposite()) {
                        } else if (cell instanceof BridgeCell && (d == Direction.LEFT || d == Direction.RIGHT)) {
                        } else tempDirection = d;
                    }
                }
            }
            if (tempDirection == null) {
                if (cell.getClass().getSimpleName().equals("BridgeCell")) {
                    if (cell.isBridge() && cell.canMove(Direction.LEFT)) {
                        tempDirection = Direction.LEFT;
                    } else if (!cell.isBridge() && cell.canMove(Direction.RIGHT)) {
                        tempDirection = Direction.RIGHT;
                    } else {
                        tempDirection = previousDirection;
                    }
                } else {
                    continue;
                }
            }
            switch (previousDirection) {
                case LEFT -> {
                    currentCol--;
                    minCol = Math.min(minCol, currentCol);
                }
                case RIGHT -> {
                    currentCol++;
                    maxCol = Math.max(maxCol, currentCol);
                }
                case UP -> {
                    currentRow--;
                    minRow = Math.min(minRow, currentRow);
                }
                case DOWN -> {
                    currentRow++;
                    maxRow = Math.max(maxRow, currentRow);
                }
            }
            previousDirection = tempDirection;
            cellIndexMap.put(cell, new Index(currentRow, currentCol));
        }

        mapGrid = new ArrayList<>();
        for (int i = 0; i < maxRow - minRow + 2; i++) {
            ArrayList<CellService> row = new ArrayList<>();
            for (int j = 0; j < maxCol - minRow + 2; j++) {
                row.add(null);
            }
            mapGrid.add(row);
        }

        for (CellService cell : cellIndexMap.keySet()) {
            Index index = cellIndexMap.get(cell);
            mapGrid.get(index.getRow() - minRow).set(index.getCol() - minCol, cell);
        }

        maxCol = maxCol - minCol + 1;
        maxRow = maxRow - minRow + 1;

        this.start();
    }

    public synchronized static ConsoleView getInstance(GameManager gameManager, GameController gameController) {
        if (consoleView == null) {
            consoleView = new ConsoleView(gameManager, gameController);
        }
        return consoleView;
    }

    @Override
    public void run() {
        update();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            switch (command) {
                case "ROLL", "roll" -> gameController.rollDice();

                case "REST", "rest" -> gameController.restTurn();

                case "U", "u" -> gameController.moveUp();

                case "D", "d" -> gameController.moveDown();

                case "L", "l" -> gameController.moveLeft();

                case "R", "r" -> gameController.moveRight();

                default -> System.out.println("Invalid command");
            }
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                if (mapGrid.get(i).get(j) != null) {
                    CellService cell = mapGrid.get(i).get(j);
                    int playerFlag = 0;
                    for (Player player : gameManager.getPlayers()) {
                        if (player.getCurrentCell() == cell) {
                            if (playerFlag != 0) System.out.print(player.getId());
                            else System.out.print("P" + player.getId());
                            playerFlag++;
                        }
                    }
                    if (playerFlag != 0) {
                        for (int k = playerFlag; k < 4; k++) System.out.print(" ");
                        continue;
                    }
                    switch (cell.getClass().getSimpleName()) {
                        case "BridgeCell" -> {
                            if (cell.isBridge()) {
                                System.out.print("  B  ");
                            } else {
                                System.out.print("  b  ");
                            }
                        }
                        case "EndCell" -> System.out.print("  E  ");

                        case "StartCell" -> System.out.print("  S  ");

                        case "NormalCell" -> System.out.print("  N  ");

                        case "CardCell" -> {
                            if (cell.isCard()) {
                                switch (cell.getCard()) {
                                    case PHILIPS_DRIVER -> System.out.print("  P  ");

                                    case HAMMER -> System.out.print("  H  ");

                                    case SAW -> System.out.print("  S  ");

                                    default -> System.out.print("  C  ");

                                }
                                ((CardCell) cell).setCard(true);
                            } else System.out.print("  C  ");
                        }
                        default -> System.out.print("  C  ");

                    }
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println();
        }
        System.out.println("Player's Inventory: ");
        for (Player player : gameManager.getPlayers()) {
            System.out.print("Player " + player.getId() + " Cards: ");
            for (Card card : player.getCards()) {
                System.out.print(card + " ");
            }
            System.out.println("| Score: " + player.getCardScore());
        }
        System.out.println("Player " + gameManager.getCurrentPlayer().getId() + "'s turn, Move:  " + gameManager.getMoves());
        System.out.print("Enter command (ROLL for roll, REST for rest turn, U D L R for move): ");
    }

    @Override
    public void alertMessage(String message) {
        System.out.println(message);
    }
}