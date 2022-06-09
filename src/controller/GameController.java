package controller;

public interface GameController {
    public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
    public void rollDice();
    public void restTurn();
    public void startGame(int playerCount, String inputFileName, boolean isGUI);
}
