package view.mainView.GUI;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardPanel extends JPanel {
    private int maxCards;
    public CardPanel(){
        this.setLayout(new GridLayout(2, 4));
        maxCards = 4;
        this.setPreferredSize(new Dimension(100, 200));
    }

    public void updateCard(ArrayList<Card> cards){
        this.removeAll();
        if(cards.size() > (maxCards * 2)){
            this.setLayout(new GridLayout(maxCards, 4));
            maxCards *= 2;
            this.setPreferredSize(new Dimension(100, 50 * maxCards));
        }
        for (Card card: cards){
            CellPanel cellPanel = new CellPanel();
            switch (card){
                case BRIDGE -> {
                    cellPanel.setCellImage("Bridge");
                }
                case SAW -> {
                    cellPanel.setCellImage("Saw");
                }
                case PHILIPS_DRIVER -> {
                    cellPanel.setCellImage("Philips");
                }
                case HAMMER -> {
                    cellPanel.setCellImage("Hammer");
                }
                default -> {
                    cellPanel.setBackground(Color.GRAY);
                    cellPanel.add(new JLabel("Unknown"));
                }
            }
            cellPanel.setBorder();
            this.add(cellPanel);
        }

    }
}
