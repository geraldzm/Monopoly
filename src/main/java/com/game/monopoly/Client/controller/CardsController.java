package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.CardsWindow;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class CardsController implements IController, MouseListener{
    CardsWindow window;
    
    public CardsController(CardsWindow window){
        this.window = window;
        
        init();
    }
    
    @Override
    public void start(){
        window.setVisible(true);
    }
    
    @Override
    public void init(){        
        try {
            window.btnExit.addMouseListener(this);
            
            window.background.setIcon(Utils.getComponentIcon("AppBG.png", window.background.getWidth(), window.background.getHeight()));
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
        } catch (IOException ex) {
            System.out.println("ERR IMG NULA: " + ex.getMessage());
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
