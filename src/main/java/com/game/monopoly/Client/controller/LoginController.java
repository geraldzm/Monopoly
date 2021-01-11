package com.game.monopoly.Client.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.LoginWindow;
import javax.swing.*;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.common.Comunication.IDMessage;
import com.game.monopoly.common.Comunication.Listener;


public class LoginController implements IController, MouseListener {
    LoginWindow window;
    
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
    
    // Evento del boton jugar
    private void playButton(){
        try{
            ServerCommunication server = getServerCommunication();
            server.removeReceiverFilter();
            
            Listener connecting = msg -> {
              switch(msg.getIdMessage()){
                  case ADMIN -> {
                      int amount = Integer.parseInt(JOptionPane.showInputDialog(window, "Soy admin"));
                      
                      server.sendInt(amount, IDMessage.RESPONSE);
                      
                      System.out.println("Soy admin");
                  }
                  case REJECTED -> { 
                      System.out.println("Lo siento mijo hoy no :c");
                  }
                  case ACCEPTED -> {
                      System.out.println("Que empiecen los juegos del hambre");
                  }
                  case STARTED ->{
                      // Redireccionar al juego
                      System.out.println("EL juego comienza");
                      server.sendMessage(IDMessage.DONE);
                  }
              }  
            };
            
            Listener chat = msg -> {
                System.out.println(msg.getString());
            };
            
            server.setListener(connecting);

        }catch(IOException ex){
            System.out.println("F: " + ex.getMessage());
        }
        
    }

    @Override
    public void close() {
        window.dispose();
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
