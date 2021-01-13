package com.game.monopoly.Client.controller;


import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.view.*;
import java.awt.event.*;
import java.io.*;

public class GameController implements IController, MouseListener{
    private GameWindow window;
    private Game game;
    
    public GameController(GameWindow window){
        this.window = window;
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
        
        try {
            window.btnSend.addMouseListener(this);
            window.btnCards.addMouseListener(this);
            
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnCards.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnCards.getWidth(), window.btnCards.getHeight()));
            window.btnSend.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnSend.getWidth(), window.btnSend.getHeight()));
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
    
    // Evento cuando se clickea el boton de abrir propiedades
    private void onBtnCardsClicked(){
        var controller = new CardsController(new CardsWindow());
        
        controller.init();
        controller.start();
    }
    
    // Evento para enviar mensajes
    private void onBtnSendClicked(){
        
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
