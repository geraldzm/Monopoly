package com.game.monopoly.Client.controller;


import java.io.IOException;

import com.game.monopoly.Client.model.Game;
import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.GameWindow;

public class GameController implements IController{
    private GameWindow window;
    private WindowController controller;
    private Game game;
    
    public GameController(GameWindow window, WindowController controller){
        this.window = window;
        this.controller = controller;
    }
    
    @Override
    public void start(){
        window.setVisible(true);
        game.start();
    }

    @Override
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

    @Override
    public void close() {
        window.dispose();
    }
}
