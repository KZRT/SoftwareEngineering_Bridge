package view.mainView.GUI;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private final ArrowButtonPanel arrowButtonPanel;
    private final JButton rollButton;
    private final JButton restButton;

    public BottomPanel(GameController gameController) {
        super();
        this.setLayout(new GridLayout(1, 3));
        this.setPreferredSize(new Dimension(800, 100));
        this.setBackground(Color.ORANGE);

        arrowButtonPanel = new ArrowButtonPanel(gameController);
        rollButton = new JButton("Roll");
        restButton = new JButton("Rest");

        rollButton.addActionListener(e -> gameController.rollDice());
        restButton.addActionListener(e -> gameController.restTurn());

        this.add(arrowButtonPanel);
        this.add(rollButton);
        this.add(restButton);
    }
}
