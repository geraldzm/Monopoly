package main.java.Client.controller;

import Client.controller.IController;
import java.util.HashMap;
import main.java.Client.view.GameWindow;
import main.java.Client.view.LoginWindow;

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
