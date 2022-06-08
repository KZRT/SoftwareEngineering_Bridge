package controller;

import model.GameManager;
import view.EndFrame;
import view.mainView.MainFrame;
import view.startView.StartView;

import java.util.ArrayList;

public class GameController {
    private final GameManager gameManager;
    private StartView startView;
    private MainFrame mainFrame;

    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
        this.startView = new StartView(this);
    }

    public void moveUp() {
        if(gameManager.getMoves() == 0){
            mainFrame.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('U')) {
                if(gameManager.endTurn()){
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    mainFrame.setVisible(false);
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
            mainFrame.update();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }
    }

    public void moveDown() {
        if(gameManager.getMoves() == 0){
            mainFrame.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('D')) {
                if(gameManager.endTurn()){
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    mainFrame.setVisible(false);
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
            mainFrame.update();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }
    }

    public void moveLeft() {
        if(gameManager.getMoves() == 0){
            mainFrame.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('L')) {
                if(gameManager.endTurn()){
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    mainFrame.setVisible(false);
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
            mainFrame.update();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }
    }

    public void moveRight() {
        if(gameManager.getMoves() == 0){
            mainFrame.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('R')) {
                if(gameManager.endTurn()){
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    mainFrame.setVisible(false);
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
            mainFrame.update();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }
    }

    public void rollDice() {
        System.out.println("Rolling dice");
        try {
            if (!gameManager.rollDice()) {
                mainFrame.alertMessage("You have rolled 0 moves. Rest your turn.");
                gameManager.endTurn();
            }
            mainFrame.update();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }

    }

    public void restTurn() {
        try {
            if (!gameManager.restTurn()) {
                mainFrame.alertMessage("You have no bridge. Cannot rest turn.");
            }
            mainFrame.update();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            mainFrame.alertMessage(e.getMessage());
        }
    }

    public void startGame() {
        gameManager.initialize(4, "default.map", "output.map");
        startView.setVisible(false);
        mainFrame = new MainFrame(gameManager, this);
    }
}
