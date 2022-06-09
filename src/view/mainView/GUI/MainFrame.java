package view.mainView.GUI;

import controller.GameController;
import model.GameManager;
import model.Player;
import view.mainView.MainView;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements MainView {
    private final GameManager gameManager;
    private final GameController gameController;
    private final MapPanel mapPanel;
    private final BottomPanel bottomPanel;
    private final InventoryPanel inventoryPanel;
    private final JLabel upperLabel;

    public MainFrame(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        gameManager.registerObserver(this);

        this.mapPanel = new MapPanel(gameManager);
        this.upperLabel = new JLabel();
        upperLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.inventoryPanel = new InventoryPanel(gameManager.getPlayers());
        this.bottomPanel = new BottomPanel(gameController);
        this.setTitle("Bridge");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapPanel.printMap();
        Dimension preferredSize = mapPanel.getPreferredSize();
        preferredSize.height += bottomPanel.getPreferredSize().height;
        preferredSize.width += inventoryPanel.getPreferredSize().width;
        this.setPreferredSize(preferredSize);

        this.setLayout(new BorderLayout());
        this.add(upperLabel, BorderLayout.NORTH);
        this.add(mapPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(inventoryPanel, BorderLayout.EAST);
        this.setLocationRelativeTo(null);
        update();
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void update() {
        mapPanel.printMap();
        Player player = gameManager.getCurrentPlayer();
        inventoryPanel.update(gameManager.getPlayers());
        upperLabel.setText("Player " + player.getId() + ": " + gameManager.getMoves() + " moves / Score: " + player.getCardScore());
        this.repaint();
    }

    @Override
    public void alertMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
