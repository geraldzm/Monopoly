package main.java.Client.controller;


import main.java.Client.model.Game;
import main.java.Client.view.GameWindow;

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
