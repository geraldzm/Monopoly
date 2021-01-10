package main.java.Client.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import main.java.Client.model.Utils;
import main.java.Client.view.LoginWindow;
import javax.swing.*;

import static main.java.Client.controller.ServerCommunication.getServerCommunication;
import main.java.common.Comunication.IDMessage;
import main.java.common.Comunication.Listener;


public class LoginController implements MouseListener {
    LoginWindow window;
    
    public LoginController(LoginWindow window){
        this.window = window;
        
        init();
    }
    
    public void start(){
        window.setVisible(true);
    }

    public void init() {
        System.out.println("Esta");
        try {
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnPlay.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnPlay.getWidth(), window.btnPlay.getHeight()));
            window.btnPlay.addMouseListener(this);
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
            window.lbMonopoly.setIcon(Utils.getComponentIcon("monopoly_logo.png", window.lbMonopoly.getWidth(), window.lbMonopoly.getHeight()));
        } catch (IOException ex) {
            System.out.println("Imagen nula");
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(window.btnPlay)){
            playButton();

        } else if (e.getSource().equals(window.btnExit)){
            window.dispose();

        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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

    public void close() {
        window.dispose();
    }
}
