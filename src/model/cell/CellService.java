package model.cell;

import model.Card;
import model.Direction;

public interface CellService {
    boolean canMove(Direction direction);

    CellService move(Direction direction);

    boolean isBridge();

    CellService moveBridge();

    boolean isCard();

    Card getCard();

    void connectCell(Direction direction, CellService cell);
}