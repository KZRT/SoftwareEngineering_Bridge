package view.mainView.GUI;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ArrowButtonPanel extends JPanel {
    private final JButton upButton;
    private final JButton downButton;
    private final JButton leftButton;
    private final JButton rightButton;
    private final GridBagConstraints gridBagConstraints;

    public ArrowButtonPanel(GameController gameController) {
        super();
        this.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1.;
        gridBagConstraints.weighty = 1.;
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        upButton = new JButton("Up");
        downButton = new JButton("Down");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");

        upButton.addActionListener(e -> gameController.moveUp());

        downButton.addActionListener(e -> gameController.moveDown());

        leftButton.addActionListener(e -> gameController.moveLeft());

        rightButton.addActionListener(e -> gameController.moveRight());

        gridBagAdd(upButton, 1, 0);
        gridBagAdd(downButton, 1, 1);
        gridBagAdd(leftButton, 0, 1);
        gridBagAdd(rightButton, 2, 1);
    }

    private void gridBagAdd(JComponent jComponent, int x, int y) {
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        this.add(jComponent, gridBagConstraints);
    }
}
