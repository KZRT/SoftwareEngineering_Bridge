package view.mainView;

import model.*;
import model.cell.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView extends Thread{
    private static ConsoleView consoleView;
    private GameManager gameManager;
    private int playerCount;

    ConsoleView(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String inputFileName;

        while (true) {
            System.out.println("Enter the number of players: ");
            playerCount = scanner.nextInt();
            if (playerCount > 1 && playerCount < 5) {
                break;
            }
            System.out.println("Invalid number of players. Please try again.");
        }

        System.out.println("Enter the name of input file in .map format (Enter for default): ");
        inputFileName = scanner.nextLine();
        if (inputFileName.equals("")) {
            inputFileName = "default.map";
        }
        System.out.println("Enter the name of output file in .map format (Enter for default): ");
        String outputFileName = scanner.nextLine();
        if (outputFileName.equals("")) {
            outputFileName = "output.map";
        }
        System.out.println(playerCount + " " + inputFileName + " " + outputFileName);
        gameManager.initialize(playerCount, inputFileName, outputFileName);
    }}
/*
        while (true) {
            printMap();
            int movementCount = gameManager.rollDice() - gameManager.getCurrentPlayer().getBridgeCount();
            while (true){
                System.out.println("Enter the movement of player " + (gameManager.getPlayerTurn() + 1) + ", Moves = "+ movementCount + ": ");
                String movement = scanner.nextLine();
                if (movement.length() == movementCount) {
                    break;
                }
                char[] movements = movement.toCharArray();
                StringBuilder validMovement = new StringBuilder();
                for (char m : movements) {
                    if(m == ' ') continue;
                    validMovement.append(m + " ");
                    if (!gameManager.movePlayer(m)) {
                        System.out.println("Invalid movement. Please try again.");
                        System.out.println("Movement Done :" + validMovement);
                        break;
                    }
                    movementCount--;
                }
                if (movementCount <= 0) {
                    break;
                }
            }
            gameManager.endTurn();
        }
    }
    private void printMap(){
        ArrayList<CellService> mapArrayList = gameManager.getBridgeMap();
        ArrayList<Player> players = gameManager.getPlayers();
        Direction previousDirection = null;
        for (CellService cell : mapArrayList) {
            StringBuilder line = new StringBuilder();

            if (cell instanceof StartCell) {
                line.append("S ");
                for (Direction d : Direction.values())
                    if (cell.canMove(d)) {
                        line.append(d.getFileChar());
                        previousDirection = d;
                        break;
                    }
            } else if (cell instanceof EndCell) {
                line = new StringBuilder("E");
            } else {
                if (cell instanceof CardCell) {
                    switch (cell.getCard()) {
                        case PHILIPS_DRIVER -> line.append("P ");
                        case HAMMER -> line.append("H ");
                        case SAW -> line.append("S ");
                    }
                } else if (cell instanceof BridgeCell) {
                    line.append(cell.isBridge() ? "B " : "b ");
                } else line.append("C ");

                Direction tempDirection = null;
                for (Direction d : Direction.values()) {
                    if (cell.canMove(d)) {
                        if (previousDirection == d.opposite()) {
                            line.append(d.getFileChar()).append(" ");
                        } else if (cell instanceof BridgeCell && (d == Direction.LEFT || d == Direction.RIGHT)) {
                        } else tempDirection = d;
                    }
                }
                if(tempDirection != null) {
                    line.append(tempDirection.getFileChar());
                }
                previousDirection = tempDirection;
            }

            for(Player player : players){
                if(player.getCurrentCell() == cell){
                    line.append(" P").append(player.getId()).append(" (");
                    for(Card card: player.getCards()){
                        switch(card){
                            case PHILIPS_DRIVER -> line.append(" P");
                            case HAMMER -> line.append(" H");
                            case SAW -> line.append(" S");
                            case BRIDGE -> line.append(" B");
                        }
                    }
                    line.append(")");
                }
            }
            System.out.println(line);
            line.delete(0, line.length());
        }
        System.out.println("Done");
    }
    public synchronized static ConsoleView getInstance(GameManager gameManager) {
        if (consoleView == null) {
            consoleView = new ConsoleView(gameManager);
        }
        return consoleView;
    }
}*/
