/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.monopoly.Client.controller;

import com.game.monopoly.Client.view.PropertyCard;
import com.game.monopoly.common.Comunication.IDMessage;
import com.game.monopoly.common.Comunication.Message;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;

/**
 *
 * @author fgm_o
 */
public class PropertyCardController implements IController, MouseListener {

    private PropertyCard property;
    
    public PropertyCardController(PropertyCard property){
        this.property = property;
    }
    
    
    @Override
    public void init() {
        property.sell.addMouseListener(this);
        property.buy.addMouseListener(this);
        property.mortgage.addMouseListener(this);
    }

    @Override
    public void start() {
        property.setVisible(true);
    }

    @Override
    public void close() {
        property.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource().equals(property.sell)){
            
        }
        if (e.getSource().equals(property.buy)){
            try {
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYPROPERTY));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource().equals(property.mortgage)){
        
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
  }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
