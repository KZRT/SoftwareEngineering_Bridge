package model.cell;

import model.Card;
import model.Direction;

import java.util.EnumMap;
import java.util.Map;

public class CardCell implements CellService {
    private final Map<Direction, CellService> connectedCells;
    private Card card;

    public CardCell(){
        connectedCells = new EnumMap<>(Direction.class);
    }

    public CardCell(Card card){
        connectedCells = new EnumMap<>(Direction.class);
        this.card = card;
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
        return card != null;
    }

    @Override
    public Card getCard(){
        Card tempCard = card;
        card = null;
        return tempCard;
    }

    @Override
    public void connectCell(Direction direction, CellService cell) {
        connectedCells.put(direction, cell);
    }

    public void setCard(Card card){
        this.card = card;
    }
}
