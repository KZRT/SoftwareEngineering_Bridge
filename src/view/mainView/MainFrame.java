package view.mainView;

import controller.GameController;
import model.GameManager;
import model.Player;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private GameManager gameManager;
    private GameController gameController;
    private MapView mapView;
    private BottomPanel bottomPanel;
    private InventoryPanel inventoryPanel;
    private JLabel upperLabel;

    public MainFrame(GameManager gameManager, GameController gameController) {
        this.gameManager = gameManager;
        this.gameController = gameController;
        this.mapView = new MapView(gameManager);
        this.upperLabel = new JLabel();
        Player player = gameManager.getCurrentPlayer();
        this.inventoryPanel = new InventoryPanel(player.getCards());
        this.bottomPanel = new BottomPanel(gameController);
        this.setTitle("Bridge");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapView.printMap();
        Dimension preferredSize = mapView.getPreferredSize();
        preferredSize.height += bottomPanel.getPreferredSize().height;
        preferredSize.width += inventoryPanel.getPreferredSize().width;
        this.setPreferredSize(preferredSize);

        upperLabel.setText(player.getId() + ": " + gameManager.getMoves() + " moves");

        this.setLayout(new BorderLayout());
        this.add(upperLabel, BorderLayout.NORTH);
        this.add(mapView, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(inventoryPanel, BorderLayout.EAST);
        System.out.println(inventoryPanel.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void update() {
        mapView.printMap();
        Player player = gameManager.getCurrentPlayer();
        inventoryPanel.update(player.getCards());
        upperLabel.setText(player.getId() + ": " + gameManager.getMoves() + " moves");
    }

    public void alertMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
