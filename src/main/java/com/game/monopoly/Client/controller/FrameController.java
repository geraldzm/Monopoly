package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.model.Objects.*;
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
    
    // Abre las cartas de un jugador
    public void openCardsFromPlayer(Players player){
        int[] cards = player.getCardsArray();

        CardWindowType type = (player.getID() == Player.getInstance().getID()) ? CardWindowType.FRIEND : CardWindowType.ENEMY;
        CardsScrollWindow window = new CardsScrollWindow(cards, type);
        
        window.setVisible(true);
    }
    
    // Abre una nueva ventana sin sustituir a la principal
    public void openNewWindow(FramesID frame){
        IController controller = generateWindow(frame);

        controller.init();
        controller.start();
    }
    
    // Genera una nueva instancia de una ventana
    public static IController generateWindow(FramesID frame){
        IController controller = null;
        
        switch(frame){
            case LOGIN:
                controller = new LoginController(new LoginWindow());
                break;
            case GAME:
                controller = new GameController(new GameWindow());
                break;
            case CARDS:
                break;
            case TABLE:
                controller = new CardsController(new CardsWindow());
                break;
            case DICEORDER:
                controller = new OrderController(new OrderWindow());
                break;
        }

        return controller;
    }
}
