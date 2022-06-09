package model.cell;

import model.Card;
import model.Direction;

import java.util.EnumMap;
import java.util.Map;

public class StartCell implements CellService {
    private final Map<Direction, CellService> connectedCells;

    public StartCell() {
        connectedCells = new EnumMap<>(Direction.class);
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
        return false;
    }

    @Override
    public CellService moveBridge() {
        throw new NullPointerException("No Bridge");
    }

    @Override
    public boolean isCard() {
        return false;
    }

    @Override
    public Card getCard() {
        throw new NullPointerException("No Card");
    }

    @Override
    public void connectCell(Direction direction, CellService cell) {
        connectedCells.put(direction, cell);
    }
}
