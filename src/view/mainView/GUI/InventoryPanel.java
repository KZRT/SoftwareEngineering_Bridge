package view.mainView.GUI;

import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryPanel extends JPanel {
    private final ArrayList<CardPanel> cardViews;

    public InventoryPanel(ArrayList<Player> players) {
        super();
        cardViews = new ArrayList<>();
        this.setLayout(new GridLayout(players.size() * 2, 1));
        this.setPreferredSize(new Dimension(100, 800));
        for (Player player : players) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BorderLayout());
            playerPanel.setPreferredSize(new Dimension(100, 200));
            playerPanel.add(new JLabel("Player " + player.getId()), BorderLayout.NORTH);

            CardPanel cardPanel = new CardPanel();
            cardPanel.updateCard(player.getCards());
            cardViews.add(cardPanel);
            playerPanel.add(cardPanel, BorderLayout.CENTER);
            playerPanel.add(cardPanel);
            this.add(playerPanel);
        }


    }

    public void update(ArrayList<Player> players) {
        for (CardPanel cardPanel : cardViews) {
            cardPanel.updateCard(players.get(cardViews.indexOf(cardPanel)).getCards());
        }

    }
}
