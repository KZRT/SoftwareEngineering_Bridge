package model;

import model.cell.CellService;
import view.mainView.MainView;

import java.util.ArrayList;
import java.util.Iterator;

public interface GameManager {
    void registerObserver(MainView mainView);

    void removeObserver(MainView mainView);

    void initialize(int playerCount);

    void initialize(int playerCount, String inputFileName);

    void initialize(int playerCount, String inputFileName, String outputFileName);

    boolean rollDice();

    boolean restTurn();

    boolean movePlayer(char direction);

    ArrayList<Integer> endGame();

    int getMoves();

    Player getCurrentPlayer();

    boolean endTurn();

    Iterator<CellService> getBridgeMapIterator();

    ArrayList<Player> getPlayers();

}
