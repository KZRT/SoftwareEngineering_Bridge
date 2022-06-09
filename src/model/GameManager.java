package model;

import model.cell.CellService;
import view.mainView.MainView;

import java.util.ArrayList;

public interface GameManager {
    public void registerObserver(MainView mainView);
    public void removeObserver(MainView mainView);
    public void initialize(int playerCount);
    public void initialize(int playerCount, String inputFileName);
    public void initialize(int playerCount, String inputFileName, String outputFileName);
    public boolean rollDice();
    public boolean restTurn();
    public boolean movePlayer(char direction);
    public ArrayList<Integer> endGame();
    public int getMoves();
    public Player getCurrentPlayer();
    public boolean endTurn();
    public ArrayList<CellService> getBridgeMap();
    public ArrayList<Player> getPlayers();

}
