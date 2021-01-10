package main.java.Client.view;

import java.io.IOException;

import javax.swing.*;
import main.java.Client.controller.LoginController;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        var controller = new LoginController(new LoginWindow(), null);
        
        controller.init();
        controller.start();
    }
}
