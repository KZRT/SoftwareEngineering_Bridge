package view.startView;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class StartView extends JFrame {
    private GameController gameController;
    private JButton startButton;
    private JButton exitButton;

    public StartView(GameController gameController) {
        super();
        this.gameController = gameController;
        this.setLayout(new GridLayout(2, 1));
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.ORANGE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("Start");
        exitButton = new JButton("Exit");

        startButton.addActionListener(e -> gameController.startGame());
        exitButton.addActionListener(e -> System.out.println("Exit"));

        this.add(startButton);
        this.add(exitButton);
        this.pack();
        this.setVisible(true);
    }
}
