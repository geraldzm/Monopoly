package main.java.Client.controller;


import java.io.IOException;

import main.java.Client.model.Game;
import main.java.Client.model.Utils;
import main.java.Client.view.GameWindow;

public class GameController{
    private GameWindow window;
    private Game game;
    
    public GameController(GameWindow window){
        this.window = window;
    }
    
    public void start(){
        window.setVisible(true);
        game.start();
    }

    public void init() {
        game = new Game();
        
        window.pack();
        window.gameContainer.add(game);
        
        try {
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
        } catch (IOException ex) {
            System.out.println("Imagen nula");
        }
    }

    public void close() {
        window.dispose();
    }
}
