package controller;

public interface GameController {
    void moveUp();

    void moveDown();

    void moveLeft();

    void moveRight();

    void rollDice();

    void restTurn();

    void startGame(int playerCount, String inputFileName, boolean isGUI);
}
