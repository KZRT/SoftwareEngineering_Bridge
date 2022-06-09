package model.cell;

import model.Card;
import model.Direction;

public class EndCell implements CellService {

    public EndCell() {
    }

    @Override
    public boolean canMove(Direction direction) {
        return false;
    }

    @Override
    public CellService move(Direction direction) {
        throw new NullPointerException("Cannot move on End Cell");
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
        throw new NullPointerException("End Cell cannot connect");
    }
}
