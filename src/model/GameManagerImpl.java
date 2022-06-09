package model;

import model.cell.CellService;
import view.mainView.MainView;

import java.util.ArrayList;

public class GameManagerImpl implements GameManager {
    private static GameManagerImpl gameManager;
    private final MapFileManager mapFileManager;
    private final ArrayList<Player> players;
    private BridgeMap bridgeMap;
    private int playerTurn;
    private int countPlayerWon;
    private final Dice dice;
    private Direction previousDirection;
    private boolean diceRolled;
    private Player currentPlayer;
    private int currentPlayerRoll;
    private final ArrayList<MainView> mainViews;

    private GameManagerImpl() {
        this.players = new ArrayList<>();
        this.mapFileManager = new MapFileManager();
        this.mainViews = new ArrayList<>();
        playerTurn = 0;
        this.dice = new Dice();
    }

    public synchronized static GameManagerImpl getInstance() {
        if (gameManager == null) {
            gameManager = new GameManagerImpl();
        }
        return gameManager;
    }

    @Override
    public void initialize(int playerCount) {
        this.mapFileManager.evaluateFile();
        this.bridgeMap = mapFileManager.getBridgeMap();

        if (playerCount < 2 || playerCount > 4) {
            throw new IllegalArgumentException("Player Count must be between 2 and 4");
        }

        for (int i = 0; i < playerCount; i++) {
            Player player = new Player(i);
            player.setCurrentCell(bridgeMap.getStartCell());
            players.add(player);
        }

        playerTurn = 0;
        currentPlayer = players.get(0);
        diceRolled = false;
        countPlayerWon = 0;
    }

    @Override
    public void initialize(int playerCount, String inputFileName) {
        this.mapFileManager.setInputMapFileName(inputFileName);
        initialize(playerCount);
    }

    @Override
    public void initialize(int playerCount, String inputFileName, String outputFileName) {
        this.mapFileManager.setInputMapFileName(inputFileName);
        this.mapFileManager.setOutputMapFileName(outputFileName);
        initialize(playerCount);
    }

    @Override
    public boolean rollDice() throws IllegalStateException {
        if (diceRolled) {
            throw new IllegalStateException("Dice has already been rolled");
        }
        diceRolled = true;
        this.currentPlayerRoll = dice.roll() - getCurrentPlayer().getBridgeCount();
        currentPlayerRoll = 10;
        notifyObservers();
        return currentPlayerRoll > 0;
    }

    @Override
    public boolean restTurn() throws IllegalStateException {
        if (diceRolled) {
            throw new IllegalStateException("Dice has already been rolled");
        }
        if (!currentPlayer.hasBridge()) {
            throw new IllegalStateException("Player has no bridge");
        }
        currentPlayer.getCards().remove(Card.BRIDGE);
        endTurn();
        notifyObservers();
        return true;

    }

    @Override
    public boolean movePlayer(char movement) throws IllegalArgumentException {
        CellService currentCell = currentPlayer.getCurrentCell();
        Direction direction = Direction.getDirectionByChar(movement);

        if (!currentCell.canMove(direction)) {
            throw new IllegalArgumentException("Invalid Move");
        }

        if (currentCell.isBridge() && movement == 'R') {
            currentCell = currentCell.moveBridge();
            currentPlayer.setCard(Card.BRIDGE);
        } else if (countPlayerWon > 0) {
            CellService tempCell = currentCell.move(direction);
            if (bridgeMap.getMapArrayList().indexOf(tempCell) < bridgeMap.getMapArrayList().indexOf(currentCell)) {
                throw new IllegalArgumentException("Cannot move in opposite direction when player has won");
            }
            currentCell = tempCell;
        } else {
            currentCell = currentCell.move(direction);
        }

        currentPlayer.setCurrentCell(currentCell);
        currentPlayerRoll--;

        if (currentCell.getClass().getSimpleName().equals("EndCell")) {
            currentPlayer.setWon(countPlayerWon++);
            return false;
        }
        previousDirection = direction;
        notifyObservers();
        return currentPlayerRoll > 0;
    }

    @Override
    public boolean endTurn() {
        if (countPlayerWon == players.size() - 1) {
            return true;
        }
        Player player = players.get(playerTurn);
        CellService currentCell = player.getCurrentCell();
        if (currentCell.isCard()) {
            Card card = currentCell.getCard();
            player.setCard(card);
        }
        currentPlayerRoll = 0;
        playerTurn = (playerTurn + 1) % players.size();
        currentPlayer = players.get(playerTurn);
        while (currentPlayer.hasWon()) {
            playerTurn = (playerTurn + 1) % players.size();
            currentPlayer = players.get(playerTurn);
        }
        diceRolled = false;
        notifyObservers();
        return false;
    }

    @Override
    public ArrayList<CellService> getBridgeMap() {
        return bridgeMap.getMapArrayList();
    }

    @Override
    public int getMoves() {
        return currentPlayerRoll;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Integer> endGame() {
        ArrayList<Integer> playerScores = new ArrayList<>();
        for (Player player : players) {
            playerScores.add(player.getCardScore());
        }
        return playerScores;
    }

    @Override
    public void registerObserver(MainView mainView) {
        mainViews.add(mainView);
    }

    @Override
    public void removeObserver(MainView mainView) {
        mainViews.remove(mainView);
    }

    private void notifyObservers() {
        for (MainView mainView : mainViews) {
            mainView.update();
        }
    }
}
