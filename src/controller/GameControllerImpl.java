package controller;

import model.GameManager;
import view.EndFrame;
import view.mainView.MainView;
import view.startView.StartView;

import java.util.ArrayList;

public class GameControllerImpl implements GameController {
    private static GameControllerImpl gameController;
    private final GameManager gameManager;
    private final StartView startView;
    private MainView mainView;
    private MainView test;
//    private MainView test2;

    private GameControllerImpl(GameManager gameManager) {
        this.gameManager = gameManager;
        this.startView = new StartView(this);
    }

    @Override
    public void moveUp() {
        if (gameManager.getMoves() == 0) {
            mainView.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('U')) {
                if (gameManager.endTurn()) {
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
        } catch (IllegalArgumentException e) {
            mainView.alertMessage(e.getMessage());
        }
    }

    @Override
    public void moveDown() {
        if (gameManager.getMoves() == 0) {
            mainView.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('D')) {
                if (gameManager.endTurn()) {
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
        } catch (IllegalArgumentException e) {
            mainView.alertMessage(e.getMessage());
        }
    }

    @Override
    public void moveLeft() {
        if (gameManager.getMoves() == 0) {
            mainView.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('L')) {
                if (gameManager.endTurn()) {
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
        } catch (IllegalArgumentException e) {
            mainView.alertMessage(e.getMessage());
        }
    }

    @Override
    public void moveRight() {
        if (gameManager.getMoves() == 0) {
            mainView.alertMessage("Roll Dice First!");
            return;
        }
        try {
            if (!gameManager.movePlayer('R')) {
                if (gameManager.endTurn()) {
                    ArrayList<Integer> playerScores = gameManager.endGame();
                    EndFrame endFrame = new EndFrame(playerScores);
                }
            }
        } catch (IllegalArgumentException e) {
            mainView.alertMessage(e.getMessage());
        }
    }

    @Override
    public void rollDice() {
        try {
            if (!gameManager.rollDice()) {
                mainView.alertMessage("You have rolled 0 moves. Rest your turn.");
                gameManager.endTurn();
            }
        } catch (IllegalStateException e) {
            mainView.alertMessage(e.getMessage());
        }

    }

    @Override
    public void restTurn() {
        try {
            if (!gameManager.restTurn()) {
                mainView.alertMessage("You have no bridge. Cannot rest turn.");
            }
        } catch (IllegalStateException e) {
            mainView.alertMessage(e.getMessage());
        }
    }

    @Override
    public void startGame(int playerCount, String inputFileName, boolean isGUI) {
        gameManager.initialize(playerCount, "./resources/maps/" + inputFileName);
        startView.dispose();
        if (isGUI) {
            mainView = SimpleMainViewFactory.createMainView(gameManager, this, "GUI");
        } else {
            mainView = SimpleMainViewFactory.createMainView(gameManager, this, "CUI");
        }
        test = SimpleMainViewFactory.createMainView(gameManager, this, "CUI");
//        test2 = SimpleMainViewFactory.createMainView(gameManager, this, "GUI");
    }

    public synchronized static GameControllerImpl getInstance(GameManager gameManager) {
        if (gameController == null) {
            gameController = new GameControllerImpl(gameManager);
        }
        return gameController;
    }
}
