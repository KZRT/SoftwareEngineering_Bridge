package view.mainView;

import model.*;
import model.cell.BridgeCell;
import model.cell.CellService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapView extends JPanel {
    private class Index {
        private final int row;
        private final int col;

        public Index(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return row + " " + col;
        }
    }
    private static MapView mapView;
    private GameManager gameManager;
    public static final int CELL_SIZE = 50;
    private HashMap<CellService, Index> cellIndexMap;
    private ArrayList<Index> bridgeIndexes;
    private ArrayList<Index> playerIndexes;
    private int minRow, minCol, maxRow, maxCol;
    private Dimension mapSize;
    private ArrayList<ArrayList<CellView>> gridCellViews;

    public MapView(GameManager gameManager) {
        this.gameManager = gameManager;
        this.cellIndexMap = new HashMap<>();
        this.bridgeIndexes = new ArrayList<>();
        this.gridCellViews = new ArrayList<>();
        ArrayList<CellService> cells = gameManager.getBridgeMap();
        maxRow = 0;
        maxCol = 0;
        minRow = 0;
        minCol = 0;
        int currentRow = 1, currentCol = 1;
        Direction previousDirection = null;
        for(CellService cell: cells){
            Direction tempDirection = null;
            if(cell.getClass().getSimpleName().equals("EndCell")){
                tempDirection = previousDirection;
            } else if(cell.getClass().getSimpleName().equals("StartCell")){
                for(Direction d : Direction.values()){
                    if(cell.canMove(d)){
                        previousDirection = d;
                    }
                }
                cellIndexMap.put(cell, new Index(currentRow, currentCol));
                System.out.println(currentRow + " " + currentCol);
                continue;
            }else {
                for (Direction d : Direction.values()){
                    if(cell.canMove(d)){
                        if(previousDirection == d.opposite()){
                        } else if(cell instanceof BridgeCell && (d == Direction.LEFT || d == Direction.RIGHT)){
                        } else tempDirection = d;
                    }
                }
            }
            if(tempDirection == null){
                System.out.println("tempDirection is null");
                continue;
            }
            switch (previousDirection){
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
            System.out.println(currentRow + " " + currentCol);
        }
        System.out.println(minRow + " " + minCol + " " + maxRow + " " + maxCol);
        this.setLayout(new GridLayout(maxRow - minRow + 2, maxCol - minCol + 2));
        this.mapSize = new Dimension((maxCol - minCol + 2) * CELL_SIZE, (maxRow - minRow + 2) * CELL_SIZE);
        this.setPreferredSize(mapSize);

        for(int i = 0; i < maxRow - minRow + 2; i++){
            ArrayList<CellView> row = new ArrayList<>();
            for(int j = 0; j < maxCol - minCol + 2; j++){
                CellView cellView = new CellView();
                cellView.setCellColor(Color.WHITE);
                this.add(cellView);
                row.add(cellView);
            }
            gridCellViews.add(row);
        }
    }

    public Dimension getMapSize(){
        return mapSize;
    }

    public void printMap() {
        for(CellService cell : gameManager.getBridgeMap()){
            Index currentCellIndex = cellIndexMap.get(cell);
            CellView cellView = gridCellViews.get(currentCellIndex.row - minRow).get(currentCellIndex.col - minCol);
            cellView.deletePlayer();
            cellView.setBorder();
            switch (cell.getClass().getSimpleName()) {
                case "StartCell" -> {
                    cellView.setCellSize(new Dimension(100, 100));
                    cellView.setCellColor(Color.BLACK);
                }
                case "BridgeCell" -> {
                    if(cell.isBridge()){
                        cellView.setCellImage("Bridge");
                        Index connectedBridge = cellIndexMap.get(cell.moveBridge());
                        for(int col = currentCellIndex.col + 1; col < connectedBridge.col; col++){
                            bridgeIndexes.add(new Index(currentCellIndex.row, col));
                        }
                    }
                }
                case "CardCell" -> {
                    if(cell.isCard()){
                        switch (cell.getCard()){
                            case PHILIPS_DRIVER -> {
                                cellView.setCellImage("Philips");
                            }
                            case SAW -> {
                                cellView.setCellImage("Saw");
                            }
                            case HAMMER -> {
                                cellView.setCellImage("Hammer");
                            }
                        }
                    }
                    cellView.setCellColor(Color.cyan);
                }
                case "EndCell" -> {
                    cellView.setCellSize(new Dimension(100, 100));
                    cellView.setCellColor(Color.GREEN);
                }
                default -> {
                    cellView.setCellColor(Color.YELLOW);
                }
            }
            for(Player player: gameManager.getPlayers()){
                if(player.getCurrentCell() == cell){
                    cellView.setPlayer(player.getId());
                }
            }
        }
        for(Index index : bridgeIndexes){
            CellView cellView = gridCellViews.get(index.getRow() - minRow).get(index.getCol() - minCol);
            cellView.setBorder();
            cellView.setCellColor(Color.BLUE);
        }
        this.repaint();
    }
}
