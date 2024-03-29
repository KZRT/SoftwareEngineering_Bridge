package model;

import model.cell.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BridgeMap {
    private final ArrayList<CellService> mapArrayList;
    private final HashMap<Integer, CellService> bridgeMap;
    private CellService previousCell;
    private Direction previousDirection;
    private int yIndex;

    public BridgeMap() {
        bridgeMap = new HashMap<>();
        mapArrayList = new ArrayList<>();
        yIndex = 0;
    }

    public boolean makeCell(String cellLine) {
        CellService currentCell;
        Direction currentDirection;
        String[] splitLine = cellLine.split(" ");
        StringBuilder computedLine = new StringBuilder();
        for (String s : splitLine) computedLine.append(s);
        char[] line = computedLine.toString().toCharArray();

        if (mapArrayList.isEmpty()) {
            currentCell = new StartCell();
            mapArrayList.add(currentCell);
            previousCell = currentCell;
            previousDirection = Direction.getDirectionByChar(line[1]);
            return true;
        }

        if (line.length > 1) {
            currentDirection = Direction.getDirectionByChar(line[1]);
            if (previousDirection.opposite() != currentDirection) return false;
            switch (currentDirection) {
                case UP -> yIndex--;
                case DOWN -> yIndex++;
            }
        } else {
            currentDirection = previousDirection;
        }

        switch (line[0]) {
            case 'C' -> {
                currentCell = new Cell();
                mapArrayList.add(currentCell);
            }
            case 'B' -> {
                currentCell = new BridgeCell(true);
                mapArrayList.add(currentCell);
                bridgeMap.put(yIndex, currentCell);
            }
            case 'b' -> {
                if (bridgeMap.isEmpty()) return false;
                currentCell = new BridgeCell(false);
                mapArrayList.add(currentCell);
                currentCell.connectCell(Direction.LEFT, bridgeMap.get(yIndex));
                bridgeMap.get(yIndex).connectCell(Direction.RIGHT, currentCell);
                bridgeMap.remove(yIndex);
            }
            case 'H' -> {
                currentCell = new CardCell(Card.HAMMER);
                mapArrayList.add(currentCell);
            }
            case 'S' -> {
                currentCell = new CardCell(Card.SAW);
                mapArrayList.add(currentCell);
            }
            case 'P' -> {
                currentCell = new CardCell(Card.PHILIPS_DRIVER);
                mapArrayList.add(currentCell);
            }
            case 'E' -> {
                currentCell = new EndCell();
                mapArrayList.add(currentCell);
                previousCell.connectCell(previousDirection, currentCell);
                return true;
            }
            default -> {
                return false;
            }
        }

        currentCell.connectCell(currentDirection, previousCell);
        previousCell.connectCell(previousDirection, currentCell);
        previousCell = currentCell;
        previousDirection = Direction.getDirectionByChar(line[2]);
        return true;
    }

    public ArrayList<CellService> getMapArrayList() {
        return mapArrayList;
    }

    public CellService getStartCell() {
        return mapArrayList.get(0);
    }
}
