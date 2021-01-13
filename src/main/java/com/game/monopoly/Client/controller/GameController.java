package com.game.monopoly.Client.controller;


import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.view.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameController implements IController, MouseListener{
    private GameWindow window;
    private Game game;
    private final Stack<String> globalMsg;
    
    public GameController(GameWindow window){
        this.window = window;
        
        globalMsg = new Stack<>();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
        game.start();
    }

    @Override
    public void init() {
        game = new Game();
        
        window.pack();
        window.gameContainer.add(game);
        
        globalMsg.add("Dinero: $0");
        
        try {
            window.btnSend.addMouseListener(this);
            window.btnCards.addMouseListener(this);
            
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnCards.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnCards.getWidth(), window.btnCards.getHeight()));
            window.btnSend.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnSend.getWidth(), window.btnSend.getHeight()));
        
            // Activamos el chat
            GameListener.getInstance().setChatListener();
        } catch (IOException ex) {
            System.out.println("Imagen nula");
        }
    }

    @Override
    public void close() {
        window.setVisible(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(window.btnCards)){
            onBtnCardsClicked();
        
        } else if (e.getSource().equals(window.btnSend)){
            onBtnSendClicked();
            
        }
    }
    
    // Permite agregar un mensaje a la ventana de chat
    public void addChatMsg(String msg){
        String currentChat = window.taChat.getText();
        
        currentChat += "\n" + msg;
        
        window.taChat.setText(currentChat);
    }
    
    // Permite activar la animacion de los dados dice debe estar entre 1-6
    public void triggerDiceAnimation(int[] dice){
        game.dice1.setAnimation(dice[0]);
        game.dice2.setAnimation(dice[1]);
        
        // Activar mensaje global
    }
    
    // Agregamos un mensaje momentaneo
    public void triggerGlobalMsg(String msg){
        String fullMsg = "";
        
        globalMsg.add(msg);
        
        for (int i = 0; i < globalMsg.size(); i++){
            if (i != globalMsg.size() - 1)
                fullMsg += globalMsg.get(i) + ", ";
            else
                fullMsg += globalMsg.get(i);
        }
        
        window.lbGeneralInfo.setText(fullMsg);
        
        // Borramos el ultimo mensaje recibido
        new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                String fullMsg = "";

                globalMsg.pop();

                for (int i = 0; i < globalMsg.size(); i++){
                    fullMsg += globalMsg.get(i);
                }

                window.lbGeneralInfo.setText(fullMsg);
            }
        }, 
        5000);
    }
    
    // Evento cuando se clickea el boton de abrir propiedades
    private void onBtnCardsClicked(){
        var controller = new CardsController(new CardsWindow());
        
        controller.init();
        controller.start();
    }
    
    // Evento para enviar mensajes
    private void onBtnSendClicked(){
        String msg = window.tfChat.getText();
        
        if (!msg.isEmpty() && !msg.startsWith(" ")){
            try {
                ServerCommunication server = getServerCommunication();
                
                server.sendMessageChat(msg);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            
            window.tfChat.setText("");
        }else {
            triggerGlobalMsg("El mensaje esta vacio...");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
