package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndFrame extends JFrame {
    ArrayList<Integer> playerScores;

    public EndFrame(ArrayList<Integer> playerScores) {
        this.playerScores = playerScores;
        int maxScore = playerScores.stream().max(Integer::compare).get();
        this.setTitle("Game Over");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        JLabel label = new JLabel("Game Over");
        label.setBounds(100, 100, 200, 50);
        add(label);

        for (int i = 0; i < playerScores.size(); i++) {
            JLabel playerLabel = new JLabel("Player " + i + ": " + playerScores.get(i));
            playerLabel.setBounds(100, 150 + (i * 50), 200, 50);
            add(playerLabel);
            if (playerScores.get(i) == maxScore) {
                JLabel wonLabel = new JLabel("Won!");
                wonLabel.setForeground(Color.RED);
                wonLabel.setBounds(300, 150 + (i * 50), 100, 50);
                add(wonLabel);
            }
        }

        this.setVisible(true);
    }
}
