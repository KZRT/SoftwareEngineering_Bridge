import controller.GameController;
import model.GameManager;

public class Main {
    public static void main(String[] args){
        GameManager gameManager = GameManager.getInstance();
        GameController gameController = new GameController(gameManager);
    }
}
