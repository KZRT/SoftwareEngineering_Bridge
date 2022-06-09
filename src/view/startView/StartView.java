package view.startView;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StartView extends JFrame {
    private final GameController gameController;

    private final JPanel centerPanel;
    private final JPanel bottomPanel;
    private final JComboBox<String> mapFileComboBox;
    private final JComboBox<Integer> playerCountComboBox;

    private final JButton GUIButton;
    private final JButton CUIButton;
    private final JButton exitButton;


    public StartView(GameController gameController) {
        super();
        this.gameController = gameController;

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(Color.ORANGE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setPreferredSize(new Dimension(600, 400));
        JLabel titleLabel = new JLabel("Bridge Game");
        titleLabel.setBounds(150, 50, 300, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        centerPanel.add(titleLabel);

        JLabel mapLabel = new JLabel("Map File:");
        mapLabel.setBounds(100, 150, 200, 50);
        mapLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(mapLabel);
        mapFileComboBox = new JComboBox<>();
        try (Stream<Path> paths = Files.walk(Paths.get("./resources/maps"))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                mapFileComboBox.addItem(filePath.getFileName().toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapFileComboBox.setBounds(300, 150, 200, 50);
        mapFileComboBox.setSelectedItem("default.map");
        centerPanel.add(mapFileComboBox);

        JLabel playerCountLabel = new JLabel("Player Count:");
        playerCountLabel.setBounds(100, 250, 200, 50);
        playerCountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(playerCountLabel);
        playerCountComboBox = new JComboBox<>();
        for (int i = 2; i < 5; i++) playerCountComboBox.addItem(i);
        playerCountComboBox.setBounds(300, 250, 200, 50);
        centerPanel.add(playerCountComboBox);

        this.add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();

        GUIButton = new JButton("Start as GUI");
        CUIButton = new JButton("Start as CUI");
        exitButton = new JButton("Exit");
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.setPreferredSize(new Dimension(600, 100));
        bottomPanel.add(GUIButton);
        bottomPanel.add(CUIButton);
        bottomPanel.add(exitButton);

        GUIButton.addActionListener(e -> {
            gameController.startGame(playerCountComboBox.getItemAt(playerCountComboBox.getSelectedIndex()), mapFileComboBox.getItemAt(mapFileComboBox.getSelectedIndex()), true);
            this.dispose();
        });

        CUIButton.addActionListener(e -> {
            gameController.startGame(playerCountComboBox.getItemAt(playerCountComboBox.getSelectedIndex()), mapFileComboBox.getItemAt(mapFileComboBox.getSelectedIndex()), false);
            this.dispose();
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        this.add(bottomPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }
}
