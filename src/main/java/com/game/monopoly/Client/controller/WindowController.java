package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.controller.IController;
import com.game.monopoly.Client.controller.LoginController;
import com.game.monopoly.Client.controller.EWindows;
import com.game.monopoly.Client.controller.EWindows;
import com.game.monopoly.Client.controller.GameController;
import com.game.monopoly.Client.controller.GameController;
import com.game.monopoly.Client.controller.IController;
import com.game.monopoly.Client.controller.LoginController;
import java.util.HashMap;
import com.game.monopoly.Client.view.GameWindow;
import com.game.monopoly.Client.view.LoginWindow;

public class WindowController {
    HashMap<EWindows, IController> windows;
    
    public WindowController(){
        LoginController login = new LoginController(new LoginWindow(), this);
        GameController game = new GameController(new GameWindow(), this);
        
        windows.put(EWindows.LOGIN, login);
        windows.put(EWindows.GAME, game);
    }
    
    public void open(EWindows window){
        IController controller = windows.get(window);
        
        controller.init();
        controller.start();
    }
}
