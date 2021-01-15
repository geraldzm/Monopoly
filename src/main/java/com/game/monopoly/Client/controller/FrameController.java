package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.view.*;
import java.util.*;

public class FrameController {
    private static FrameController frameController;
    private HashMap<FramesID, IController> windows;
    private IController currentWindow;
    
    private FrameController(){
        windows = new HashMap<>();
        
        windows.put(FramesID.LOGIN, new LoginController(new LoginWindow()));
        windows.put(FramesID.GAME, new GameController(new GameWindow()));
        windows.put(FramesID.TABLE, new CardsController(new CardsWindow()));
        windows.put(FramesID.DICEORDER, new OrderController(new OrderWindow()));
    }
    
    public static FrameController getInstance(){
        if (frameController == null){
            frameController = new FrameController();
        }
        
        return frameController;
    }

    public IController getWindow(FramesID frame){
        return windows.get(frame);
    }
    // Cambia de ventana principal
    public void openWindow(FramesID frame){
        if (currentWindow == null){
            currentWindow = windows.get(frame);
        }
        
        currentWindow.close();
        
        windows.get(frame).init();
        windows.get(frame).start();
        
        currentWindow = windows.get(frame);
    }
    
    // Abre una nueva ventana sin sustituir a la principal
    public void openNewWindow(FramesID frame){
        windows.get(frame).init();
        windows.get(frame).start();
    }
    
    
}
