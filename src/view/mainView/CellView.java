package view.mainView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CellView extends JPanel {
    Image image;
    public CellView(){
        super();
        this.setPreferredSize(new Dimension(50, 50));
        this.setLayout(new GridLayout(2, 2));
        this.setBackground(Color.ORANGE);
    }

    public void setCellSize(Dimension dimension){
        this.setPreferredSize(dimension);
    }

    public void setCellColor(Color color){
        this.setBackground(color);
    }

    public void setCellImage(String imageName){
        image = new ImageIcon("./src/view/resources/" + imageName + ".png").getImage();
        System.out.println(image);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void setBorder() {
        this.setBorder(new LineBorder(Color.BLACK));
    }

    public void setPlayer(int playerID){
        JLabel playerLabel = new JLabel();
        playerLabel.setText(String.valueOf(playerID));
        playerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        playerLabel.setPreferredSize(new Dimension(25, 25));
        playerLabel.setForeground(Color.getHSBColor(playerID * 0.1f, 1.0f, 1.0f));
        this.add(playerLabel);
    }

    public void deletePlayer(){
        this.removeAll();
    }
}
