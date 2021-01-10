package com.game.monopoly.Client.view;

import com.game.monopoly.Client.view.LoginWindow;
import java.io.IOException;

import javax.swing.*;
import com.game.monopoly.Client.controller.LoginController;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        var controller = new LoginController(new LoginWindow(), null);
        
        controller.init();
        controller.start();
    }
}
