package Client.view;

import Client.controller.GameController;
import Client.model.CardFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class MainWindow {
    public static void main(String args[]){
//        var window = new GameWindow();    
//        var controller = new GameController(window);
//        controller.start();

        CardsWindow cartaswindow;
        CardWindow cartawindow;
        try {
            CardFactory cardProperty = new CardFactory();
            cartaswindow = new CardsWindow(cardProperty,new int []{1,3,6,8,9,11,13,14});
            //cartawindow = new CardWindow(cardProperty,1);
            cartaswindow.Init();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
