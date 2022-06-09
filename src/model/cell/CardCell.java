package model.cell;

import model.Card;
import model.Direction;

import java.util.EnumMap;
import java.util.Map;

public class CardCell implements CellService {
    private final Map<Direction, CellService> connectedCells;
    private Card card;
    private boolean hasCard;

    public CardCell(){
        connectedCells = new EnumMap<>(Direction.class);
        this.hasCard = false;
    }

    public CardCell(Card card){
        connectedCells = new EnumMap<>(Direction.class);
        this.card = card;
        this.hasCard = true;
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
        return hasCard;
    }

    @Override
    public Card getCard(){
        this.hasCard = false;
        return card;
    }

    @Override
    public void connectCell(Direction direction, CellService cell) {
        connectedCells.put(direction, cell);
    }

    public void setCard(boolean hasCard){
        this.hasCard = hasCard;
    }
}
