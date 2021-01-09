package main.java.Client.view;


import main.java.Client.controller.GameController;

import javax.swing.*;

public class MainWindow extends JFrame {
    public static void main(String args[]) {
        var window = new GameWindow();
        
        var controller = new GameController(window);
        
        controller.start();
    }
}
