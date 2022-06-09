package main;

import controller.GameController;
import controller.GameControllerImpl;
import model.GameManager;
import model.GameManagerImpl;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = GameManagerImpl.getInstance();
        GameController gameController = GameControllerImpl.getInstance(gameManager);
    }
}
