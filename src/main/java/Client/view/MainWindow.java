package main.java.Client.view;

import java.io.IOException;

import javax.swing.*;
import main.java.Client.controller.EWindows;
import main.java.Client.controller.LoginController;
import main.java.Client.controller.WindowController;

public class MainWindow extends JFrame {
    public static void main(String args[]) throws IOException {
        /*var controller = new WindowController();
        
        controller.open(EWindows.LOGIN);*/
        
        var controller = new LoginController(new LoginWindow(), null);
        controller.init();
        controller.start();
    }
}
