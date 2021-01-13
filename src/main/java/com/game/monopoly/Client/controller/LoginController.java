package com.game.monopoly.Client.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.LoginWindow;
import javax.swing.*;

import com.game.monopoly.Client.model.Player;

public class LoginController implements IController, MouseListener {
    private LoginWindow window;
    
    public LoginController(LoginWindow window){
        this.window = window;
        
        init();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
    }

    @Override
    public void init() {
        try {
            window.btnPlay.addMouseListener(this);
            window.btnExit.addMouseListener(this);
            
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnPlay.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnPlay.getWidth(), window.btnPlay.getHeight()));
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
            window.lbMonopoly.setIcon(Utils.getComponentIcon("monopoly_logo.png", window.lbMonopoly.getWidth(), window.lbMonopoly.getHeight()));
        } catch (IOException ex) {
            System.out.println("ERR IMG NULA: " + ex.getMessage());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(window.btnPlay)){
            playButton();

        } else if (e.getSource().equals(window.btnExit)){
            close();

        }
    }

    class MomentPlayer{
        public int id;
        public String name;
    }
    
    // Evento del boton jugar
    private void playButton() {
        if (!isAValidField(window.tfUserName.getText())){
            JOptionPane.showMessageDialog(window, "El nombre de usuario no puede contener ',', estar vacio o empezar con espacios en blanco");
            
            return;
        }
        
        GameListener listener = GameListener.getInstance();
        Player player = Player.getInstance();
        
        player.setName(window.tfUserName.getText());
        listener.setWindow(window);
        
        try {
            listener.setListener();
        } catch (IOException ex) {
            System.out.println("LISTENER ERROR: " + ex.getMessage());
        }
    }
    
    private boolean isAValidField(String field){
        return !field.contains(",") && !field.isEmpty() && !field.startsWith(" ");
    }

    @Override
    public void close() {
        window.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
