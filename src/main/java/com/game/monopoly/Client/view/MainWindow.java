package com.game.monopoly.Client.view;

import com.game.monopoly.Client.controller.*;
import com.game.monopoly.Client.model.CardWindowType;

import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
       /* FrameController controller = FrameController.getInstance();
        controller.openWindow(FramesID.GAME);
        */
       
       // CardsScrollWindow cardsScrollWindow = new CardsScrollWindow(new int[]{1,3,4,5,6,8,9,11,12,13,14,15,16,18,19,21,23,24,25,26,27,28,29,31,32,34,35,37,38,39}, CardWindowType.FRIEND);
        CardsScrollWindow cardsScrollWindow = new CardsScrollWindow(new int[]{1}, CardWindowType.FRIEND);
        cardsScrollWindow.setVisible(true);
        
       
        CardWindow cardWindow = new CardWindow(1, CardWindowType.ENEMY);
        cardWindow.setVisible(true);
    }
}
