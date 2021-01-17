package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.model.Objects.*;
import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.view.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CardsController implements IController, MouseListener{
    CardsWindow window;
    
    ArrayList<Integer> IDs;
    
    public CardsController(CardsWindow window){
        this.window = window;
        
        IDs = new ArrayList<>();
        
        init();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
    }
    
    @Override
    public void init(){        
        initCombobox();
        
        try {
            window.btnExit.addMouseListener(this);
            window.btnOpen.addMouseListener(this);

            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
            window.btnOpen.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnOpen.getWidth(), window.btnOpen.getHeight()));
        } catch (IOException ex) {
            System.out.println("ERR IMG NULA: " + ex.getMessage());
        }
        
    }
    
    private void initCombobox(){
        HashMap<Integer, Players> players = GameListener.getInstance().getPlayers();
        
        window.cbPlayers.removeAllItems();
        IDs.clear();
        
        for (int ID = 0; ID < 6; ID++) if (players.containsKey(ID)){
            IDs.add(ID);
            window.cbPlayers.addItem(players.get(ID).getName());
        }
    }
    
    @Override
    public void close(){
        window.setVisible(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(window.btnExit)){
            close();
        } else if (e.getSource().equals(window.btnOpen)){
            onBtnOpen();
        }
    }
    
    private void onBtnOpen(){
        HashMap<Integer, Players> players = GameListener.getInstance().getPlayers();
        FrameController controller = FrameController.getInstance();
        
        int ID = window.cbPlayers.getSelectedIndex();
        
        ID = IDs.get(ID);
        
        Players player = players.get(ID);
        
        controller.openCardsFromPlayer(player);
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
