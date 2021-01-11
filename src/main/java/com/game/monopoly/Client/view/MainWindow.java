package com.game.monopoly.Client.view;

import java.io.IOException;

import javax.swing.*;
import com.game.monopoly.Client.controller.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        //var controller = new LoginController(new LoginWindow(), null);
        //var controller = new GameController(new GameWindow());
        var controller = new CardsController(new CardsWindow());
       
        controller.init();
        controller.start();
    }
}
