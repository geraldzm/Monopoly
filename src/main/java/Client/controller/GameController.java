package Client.controller;

import Client.model.Game;
import Client.view.GameWindow;

public class GameController {
    private GameWindow window;
    private Game game;
    
    public GameController(GameWindow window){
        this.window = window;
        this.game = new Game();
        
        window.gameContainer.add(game);
    }
    
    public void start(){
        window.setVisible(true);
        game.start();
    }
}
