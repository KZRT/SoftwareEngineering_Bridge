package controller;

import model.GameManager;
import view.mainView.CUI.ConsoleView;
import view.mainView.GUI.MainFrame;
import view.mainView.MainView;

public class SimpleMainViewFactory {
    public static MainView createMainView(GameManager gameManager, GameController gameController, String type) {
        return switch (type) {
            case "CUI" -> ConsoleView.getInstance(gameManager, gameController);
            case "GUI" -> new MainFrame(gameManager, gameController);
            default -> null;
        };
    }
}
