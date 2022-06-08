package model;

import model.cell.CellService;

import java.util.ArrayList;

public class GameManager {
    private static GameManager gameManager;
    private ArrayList<Player> players;
    private final MapFileManager mapFileManager;
    private BridgeMap bridgeMap;
    private int playerTurn;
    private int countPlayerWon;
    private Dice dice;

    private Direction previousDirection;
    private boolean diceRolled;
    private Player currentPlayer;
    private int currentPlayerRoll;

    private GameManager() {
        this.players = new ArrayList<>();
        this.mapFileManager = new MapFileManager();
        playerTurn = 0;
        this.dice = new Dice();
    }

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

    public void initialize(int playerCount, String inputFileName) {
        this.mapFileManager.setInputMapFileName(inputFileName);
        initialize(playerCount);
    }

    public void initialize(int playerCount, String inputFileName, String outputFileName) {
        this.mapFileManager.setInputMapFileName(inputFileName);
        this.mapFileManager.setOutputMapFileName(outputFileName);
        initialize(playerCount);
    }

    public boolean rollDice() throws IllegalStateException {
        if (diceRolled) {
            throw new IllegalStateException("Dice has already been rolled");
        }
        diceRolled = true;
        this.currentPlayerRoll = dice.roll() - getCurrentPlayer().getBridgeCount();
        currentPlayerRoll = 10;
        return currentPlayerRoll > 0;
    }

    public boolean restTurn() throws IllegalStateException {
        if (diceRolled) {
            throw new IllegalStateException("Dice has already been rolled");
        }
        if (!currentPlayer.hasBridge()) {
            throw new IllegalStateException("Player has no bridge");
        }
        currentPlayer.getCards().remove(Card.BRIDGE);
        endTurn();
        return true;

    }

    public boolean movePlayer(char movement) throws IllegalArgumentException, IllegalStateException {
        CellService currentCell = currentPlayer.getCurrentCell();
        Direction direction = Direction.getDirectionByChar(movement);

        if (!currentCell.canMove(direction)) {
            throw new IllegalArgumentException("Invalid Move");
        }

        if (currentCell.isBridge() && movement == 'R') {
            currentCell = currentCell.moveBridge();
            currentPlayer.setCard(Card.BRIDGE);
        } else if (countPlayerWon > 0 && direction == previousDirection.opposite()) {
            throw new IllegalStateException("Cannot move in opposite direction when player has won");
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
        return currentPlayerRoll > 0;
    }

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
        return false;
    }

    public ArrayList<CellService> getBridgeMap() {
        return bridgeMap.getMapArrayList();
    }

    public int getMoves() {
        return currentPlayerRoll;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

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

    public synchronized static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }
        return gameManager;
    }
}
