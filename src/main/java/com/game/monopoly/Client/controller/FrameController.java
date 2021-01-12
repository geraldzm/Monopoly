package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.view.*;
import java.util.HashMap;

public class FrameController {
    private static FrameController frameController;
    
    HashMap<FramesID, IController> windows;
    IController currentWindow;
    
    private FrameController(){
        windows = new HashMap<>();
        
        windows.put(FramesID.LOGIN, new LoginController(new LoginWindow()));
        windows.put(FramesID.GAME, new GameController(new GameWindow()));
        windows.put(FramesID.CARDS, null); // Nececesito el merge con la rama de Niko
        windows.put(FramesID.TABLE, new CardsController(new CardsWindow()));
        windows.put(FramesID.DICEORDER, new OrderController(new OrderWindow()));
    }
    
    public static FrameController getInstance(){
        if (frameController == null){
            frameController = new FrameController();
        }
        
        return frameController;
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
