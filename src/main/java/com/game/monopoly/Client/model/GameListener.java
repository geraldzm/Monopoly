package com.game.monopoly.Client.model;

import com.game.monopoly.Client.controller.FrameController;
import com.game.monopoly.Client.controller.FramesID;
import com.game.monopoly.Client.controller.ServerCommunication;
import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import static com.game.monopoly.common.Comunication.IDMessage.*;
import com.game.monopoly.common.Comunication.Listener;
import com.game.monopoly.common.Comunication.Message;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameListener {
    private static GameListener listener;
    private JFrame window;
    private int amountPlayers;
    
    private GameListener(){
        
    }
    
    // Singleton para el Listener
    public static GameListener getInstance(){
        if (listener == null){
            listener = new GameListener();
        }
        
        return listener;
    }
    
    private void askAmountPlayers(){
        int amount = -1;
        
        while(true){
            try{
                amount = Integer.parseInt(JOptionPane.showInputDialog(window, "Cuantas personas van a jugar? (incluyendote)"));
            }catch(NumberFormatException ex){
                amount = -1;
            }
            
            if (amount < 0 || amount > 6)
                JOptionPane.showMessageDialog(window, "Introduzca un numero valido...");
            else
                break;
        }
        
        amountPlayers = amount;
    }
    
    public Listener setListener() throws IOException{
        ServerCommunication server = getServerCommunication();
        server.removeReceiverFilter();
        
        Player player = Player.getInstance();
        
        Listener gameListener = msg -> {
              switch(msg.getIdMessage()){
                  case ADMIN -> {
                      askAmountPlayers();
                      
                      server.sendInt(amountPlayers, RESPONSE);
                      
                      System.out.println("Servidor: Este usuario ahora es administrador");
                  }
                  case REJECTED -> { 
                      System.out.println("Servidor: Se ha rechazado tu peticion de unirse...");
                  }
                  case ACCEPTED -> {
                      System.out.println("Servidor: Se ha aceptado tu peticion de unirse!");
                  }
                  case ID -> {
                      System.out.println("Servidor: Se ha recibido la ID: " + msg.getNumber());
                      player.setID(msg.getNumber());
                      server.sendMessage(DONE);
                  }

                  case NAME -> {
                      System.out.println("Local: Enviando nombre de usuario al servidor...");
                      server.sendMessage(new Message(player.getID(), player.getName(), RESPONSE));
                  }

                  case NAMES -> {

                  }

                  case STARTED -> {
                      System.out.println("Servidor: El juego puede iniciar...");
                      FrameController controller = FrameController.getInstance();
                      
                      controller.openWindow(FramesID.GAME);

                      server.sendMessage(DONE);
                  }
              }
        };  
            
            
        server.setListener(gameListener);
        
        return gameListener;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }
    
    
}
