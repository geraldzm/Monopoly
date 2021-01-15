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


        CardsScrollWindow cardsScrollWindow = new CardsScrollWindow(new int[]{1,3,4,5,6,8,9,11,12,13,14,15,16,18,19,21,23,24,25,26,27,28,29,31,32,34,35,37,38,39}, CardWindowType.FRIEND);
        cardsScrollWindow.setVisible(true);

/*
        for (int i = 42; i < 74; i++) {
            CardWindow cardWindow = new CardWindow(i, CardWindowType.ENEMY);
            cardWindow.setVisible(true);
        }


        CardWindow cardWindow = new CardWindow(67, CardWindowType.ENEMY);
        cardWindow.setVisible(true); 
*/
        
      // controller.init();
      // controller.start();
    }
}
