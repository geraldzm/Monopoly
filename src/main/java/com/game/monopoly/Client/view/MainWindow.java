package com.game.monopoly.Client.view;

import java.io.IOException;

import javax.swing.*;
import com.game.monopoly.Client.controller.*;
import com.game.monopoly.Client.model.CardWindowType;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        //var controller = new LoginController(new LoginWindow());
        //var controller = new GameController(new GameWindow());
        //var controller = new CardsController(new CardsWindow());
        //var controller = new OrderController(new OrderWindow());


       /* CardsScrollWindow cardsScrollWindow = new CardsScrollWindow(new int[]{1}, CardWindowType.ENEMY);
        cardsScrollWindow.setVisible(true);*/

        CardWindow cardWindow = new CardWindow(40, CardWindowType.FRIEND);
        cardWindow.setVisible(true);
        
      //  controller.init();
       // controller.start();
    }
}
