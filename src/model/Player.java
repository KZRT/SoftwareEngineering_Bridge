package model;

import model.cell.CellService;

import java.util.ArrayList;

public class Player{
    private final int id;
    private CellService currentCell;
    private final ArrayList<Card> cards;
    private boolean hasWon;
    private int score;

    public Player(int id){
        this.id = id;
        this.hasWon = false;
        this.cards = new ArrayList<>();
        this.score = 0;
    }

    public int getId(){
        return id;
    }

    public CellService getCurrentCell(){
        return currentCell;
    }

    public void setCurrentCell(CellService currentCell){
        this.currentCell = currentCell;
    }

    public int getCardScore(){
        for(Card card : cards){
            score += card.getValue();
        }
        return score;
    }

    public void setCard(Card card){
        this.cards.add(card);
    }

    public boolean hasWon(){
        return hasWon;
    }

    public void setWon(int countPlayerWon){
        switch (countPlayerWon){
            case 0->{
                score += 7;
            }
            case 1->{
                score += 3;
            }
            case 2->{
                score += 1;
            }
            default -> {
                score += 0;
            }
        }
        this.hasWon = true;
    }

    public int getBridgeCount(){
        int bridgeCount = 0;
        for(Card card : cards){
            if(card == Card.BRIDGE){
                bridgeCount++;
            }
        }
        return bridgeCount;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean hasBridge(){
        for(Card card : cards){
            if(card == Card.BRIDGE){
                return true;
            }
        }
        return false;
    }
}
