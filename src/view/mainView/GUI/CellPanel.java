package view.mainView.GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CellPanel extends JPanel {
    Image image;

    public CellPanel() {
        super();
        this.setPreferredSize(new Dimension(50, 50));
        this.setLayout(new GridLayout(2, 2));
        this.setBackground(Color.ORANGE);
    }

    public void setCellSize(Dimension dimension) {
        this.setPreferredSize(dimension);
    }

    public void setCellColor(Color color) {
        this.setBackground(color);
    }

    public void setCellImage(String imageName) {
        image = new ImageIcon("./resources/images/" + imageName + ".png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void setBorder() {
        this.setBorder(new LineBorder(Color.BLACK));
    }

    public void setPlayer(int playerID) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.getHSBColor(playerID * 0.1f, 1.0f, 1.0f));
        playerPanel.add(new JLabel(playerID + ""));
        this.add(playerPanel);
    }

    public void deletePlayer() {
        this.removeAll();
    }

    public void deleteGraphics() {
        this.image = null;
    }
}
