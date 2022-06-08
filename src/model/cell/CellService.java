package model.cell;

import model.Card;
import model.Direction;

public interface CellService {
    public boolean canMove(Direction direction);
    public CellService move(Direction direction);
    public boolean isBridge();
    public CellService moveBridge();
    public boolean isCard();
    public Card getCard();
    public void connectCell(Direction direction, CellService cell);
}