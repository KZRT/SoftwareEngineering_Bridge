package view.mainView.GUI;

import model.Direction;
import model.GameManager;
import model.Player;
import model.cell.BridgeCell;
import model.cell.CardCell;
import model.cell.CellService;
import view.mainView.Index;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MapPanel extends JPanel {
    public static final int CELL_SIZE = 50;
    private final GameManager gameManager;
    private final HashMap<CellService, Index> cellIndexMap;
    private final ArrayList<Index> bridgeIndexes;
    private int minRow, minCol, maxRow, maxCol;
    private final Dimension mapSize;
    private final ArrayList<ArrayList<CellPanel>> gridCellViews;

    public MapPanel(GameManager gameManager) {
        this.gameManager = gameManager;
        this.cellIndexMap = new HashMap<>();
        this.bridgeIndexes = new ArrayList<>();
        this.gridCellViews = new ArrayList<>();
        Iterator<CellService> cells = gameManager.getBridgeMapIterator();
        maxRow = 0;
        maxCol = 0;
        minRow = 0;
        minCol = 0;
        int currentRow = 1, currentCol = 1;
        Direction previousDirection = null;
        while (cells.hasNext()) {
            CellService cell = cells.next();
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
        this.setLayout(new GridLayout(maxRow - minRow + 2, maxCol - minCol + 2));
        this.mapSize = new Dimension((maxCol - minCol + 2) * CELL_SIZE, (maxRow - minRow + 2) * CELL_SIZE);
        this.setPreferredSize(mapSize);

        for (int i = 0; i < maxRow - minRow + 2; i++) {
            ArrayList<CellPanel> row = new ArrayList<>();
            for (int j = 0; j < maxCol - minCol + 2; j++) {
                CellPanel cellPanel = new CellPanel();
                cellPanel.setCellColor(Color.WHITE);
                this.add(cellPanel);
                row.add(cellPanel);
            }
            gridCellViews.add(row);
        }
    }

    public void printMap() {
        Iterator<CellService> cells = gameManager.getBridgeMapIterator();
        while (cells.hasNext()) {
            CellService cell = cells.next();
            Index currentCellIndex = cellIndexMap.get(cell);
            CellPanel cellPanel = gridCellViews.get(currentCellIndex.getRow() - minRow).get(currentCellIndex.getCol() - minCol);
            cellPanel.deletePlayer();
            cellPanel.setBorder();
            switch (cell.getClass().getSimpleName()) {
                case "StartCell" -> {
                    cellPanel.setCellSize(new Dimension(100, 100));
                    cellPanel.setCellColor(Color.YELLOW);
                    cellPanel.add(new JLabel("S"));
                }
                case "BridgeCell" -> {
                    if (cell.isBridge()) {
                        cellPanel.setCellImage("Bridge");
                        Index connectedBridge = cellIndexMap.get(cell.moveBridge());
                        for (int col = currentCellIndex.getCol() + 1; col < connectedBridge.getCol(); col++) {
                            bridgeIndexes.add(new Index(currentCellIndex.getRow(), col));
                        }
                    } else {
                        cellPanel.setCellColor(new Color(0xfae39a));
                    }
                }
                case "CardCell" -> {
                    if (cell.isCard()) {
                        switch (cell.getCard()) {
                            case PHILIPS_DRIVER -> {
                                cellPanel.setCellImage("Philips");
                            }
                            case SAW -> {
                                cellPanel.setCellImage("Saw");
                            }
                            case HAMMER -> {
                                cellPanel.setCellImage("Hammer");
                            }
                        }
                        ((CardCell) cell).setCard(true);
                    } else {
                        cellPanel.deleteGraphics();
                        cellPanel.setCellColor(new Color(0xfae39a));
                    }
                }
                case "EndCell" -> {
                    cellPanel.setCellSize(new Dimension(100, 100));
                    cellPanel.setCellColor(Color.GREEN);
                    cellPanel.add(new JLabel("END"));
                }
                default -> {
                    cellPanel.setCellColor(new Color(0xfae39a));
                }
            }
            for (Player player : gameManager.getPlayers()) {
                if (player.getCurrentCell() == cell) {
                    cellPanel.setPlayer(player.getId());
                }
            }
        }
        for (Index index : bridgeIndexes) {
            CellPanel cellPanel = gridCellViews.get(index.getRow() - minRow).get(index.getCol() - minCol);
            cellPanel.setBorder();
            cellPanel.setCellColor(Color.WHITE);
            cellPanel.setCellImage("BridgeConnect");
        }
        this.repaint();
    }
}
