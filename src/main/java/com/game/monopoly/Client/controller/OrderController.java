package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.model.Utils;
import com.game.monopoly.Client.view.OrderWindow;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JLabel;

public class OrderController implements IController, MouseListener{
    private OrderWindow window;
    
    JLabel[] players = new JLabel[6];
    JLabel[] results = new JLabel[6];
    
    public OrderController(OrderWindow window){
        this.window = window;
    }

    @Override
    public void start() {
        window.pack();
        window.setVisible(true);
    }
    
    @Override
    public void init() {
        initArray();
        
        try {
            window.btnExit.addMouseListener(this);
            
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
        } catch (IOException ex) {
            System.out.println("ERR IMG NULA: " + ex.getMessage());
        }
    }

    @Override
    public void close() {
        // TODO: Enviar el DONE al server
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
    
    // Inicializa el array de labels
    private void initArray(){
        players[0] = window.lbPlayer0;
        players[1] = window.lbPlayer1;
        players[2] = window.lbPlayer2;
        players[3] = window.lbPlayer3;
        players[4] = window.lbPlayer4;
        players[5] = window.lbPlayer5;
        
        results[0] = window.lbRst0;
        results[1] = window.lbRst1;
        results[2] = window.lbRst2;
        results[3] = window.lbRst3;
        results[4] = window.lbRst4;
        results[5] = window.lbRst5;
    }
}
