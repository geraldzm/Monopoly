package main.java.Client.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import main.java.Client.model.Utils;
import main.java.Client.view.LoginWindow;
import Client.controller.IController;
import java.awt.event.ActionListener;

public class LoginController implements IController, ActionListener{
    WindowController controller;
    LoginWindow window;
    
    public LoginController(LoginWindow window, WindowController controller){
        this.window = window;
        this.controller = controller;
        
        init();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
    }

    @Override
    public void init() {
        try {
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnPlay.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnPlay.getWidth(), window.btnPlay.getHeight()));
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
            window.lbMonopoly.setIcon(Utils.getComponentIcon("monopoly_logo.png", window.lbMonopoly.getWidth(), window.lbMonopoly.getHeight()));
        } catch (IOException ex) {
            System.out.println("Imagen nula");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(window.btnPlay)){
            playButton();
        
        } else if (e.getSource().equals(window.btnExit)){
            window.dispose();
            
        } 
    }
    
    // Evento del boton jugar
    private void playButton(){
    }

    @Override
    public void close() {
        window.dispose();
    }
}
