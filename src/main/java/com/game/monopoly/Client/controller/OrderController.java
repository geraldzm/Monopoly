package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.Client.model.*;
import com.game.monopoly.Client.view.*;
import static com.game.monopoly.common.Comunication.IDMessage.DONE;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class OrderController implements IController, MouseListener{
    private OrderWindow window;
    private int n = 0;
    
    JLabel[] players = new JLabel[6];
    JLabel[] results = new JLabel[6];
    
    private int[] dices;
    private String[] playerList;
    
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
        initData();
        
        try {
            window.btnExit.addMouseListener(this);
            
            window.btnExit.setIcon(Utils.getComponentIcon("ButtonsBG.png", window.btnExit.getWidth(), window.btnExit.getHeight()));
        } catch (IOException ex) {
            System.out.println("ERR IMG NULA: " + ex.getMessage());
        }
    }
    
    private void initData(){

        for (int i = 0; i < 6; i++){

            if (i < playerList.length){
                players[i].setText(playerList[i]);
                results[i].setText("Dices " + dices[i*3] + " + " + dices[i*3+1] + " =" + dices[i*3+2]);
            } else{
                players[i].setText("");
                results[i].setText("");
            }
        }
    }

    @Override
    public void close() {
        try {
            System.out.println("Se preciona el close: " + (++n));

            getServerCommunication().sendMessage(DONE);

        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        
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

    public int[] getDices() {
        return dices;
    }

    public void setDices(int[] dices) {
        this.dices = dices;
    }

    public String[] getPlayers() {
        return playerList;
    }

    public void setPlayers(String[] players) {
        this.playerList = players;
    }
}
