package view.mainView;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JPanel {
    public InventoryPanel(ArrayList<Card> cards) {
        super();
        this.update(cards);
    }

    public void update(ArrayList<Card> cards){
        this.removeAll();
        this.setPreferredSize(new Dimension(100, 200 * cards.size()));
        this.setLayout(new GridLayout(cards.size(), 1));
        this.setBackground(Color.ORANGE);
        for (Card card: cards){
            JPanel cardPanel = new JPanel();
            cardPanel.setPreferredSize(new Dimension(100, 200));
            switch (card){
                case BRIDGE -> {
                    cardPanel.setBackground(Color.BLUE);
                    cardPanel.add(new JLabel("Bridge"));
                }
                case SAW -> {
                    cardPanel.setBackground(Color.RED);
                    cardPanel.add(new JLabel("Saw"));
                }
                case PHILIPS_DRIVER -> {
                    cardPanel.setBackground(Color.GREEN);
                    cardPanel.add(new JLabel("Philips Driver"));
                }
                case HAMMER -> {
                    cardPanel.setBackground(Color.YELLOW);
                    cardPanel.add(new JLabel("Hammer"));
                }
                default -> {
                    cardPanel.setBackground(Color.GRAY);
                    cardPanel.add(new JLabel("Unknown"));
                }
            }
            System.out.println(card);
            this.add(cardPanel);
        }
    }
}
