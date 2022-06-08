package model.cell;

import model.Card;
import model.Direction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BridgeCell implements CellService {
    private final Map<Direction, CellService> connectedCells;
    private boolean isStartOfBridge;

    public BridgeCell(){
        connectedCells = new EnumMap<>(Direction.class);
    }

    public BridgeCell(boolean isStartOfBridge){
        connectedCells = new HashMap<>();
        this.isStartOfBridge = isStartOfBridge;
    }

    @Override
    public boolean canMove(Direction direction) {
        return connectedCells.get(direction) != null;
    }

    @Override
    public CellService move(Direction direction) {
        return connectedCells.get(direction);
    }

    @Override
    public boolean isBridge() {
        return isStartOfBridge;
    }

    @Override
    public CellService moveBridge() {
        return connectedCells.get(isStartOfBridge ? Direction.RIGHT : Direction.LEFT);
    }

    @Override
    public boolean isCard() {
        return false;
    }

    @Override
    public Card getCard(){
        throw new NullPointerException("No Card");
    }

    @Override
    public void connectCell(Direction direction, CellService cell) {
        connectedCells.put(direction, cell);
    }
}
