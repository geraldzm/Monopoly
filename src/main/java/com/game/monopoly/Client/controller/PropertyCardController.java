/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import com.game.monopoly.Client.view.*;
import com.game.monopoly.common.Comunication.*;
import java.awt.event.*;
import java.io.*;

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
        if (property.sell != null)
            property.sell.addMouseListener(this);

        if (property.buy != null)
            property.buy.addMouseListener(this);

        if (property.mortgage != null)
            property.mortgage.addMouseListener(this);

        if (property.sellHotel != null)
            property.sellHotel.addMouseListener(this);

        if (property.sellHouse != null)
            property.sellHouse.addMouseListener(this);

        if (property.buyHotel != null)
            property.buyHotel.addMouseListener(this);

        if (property.buyHouse != null)
            property.buyHouse.addMouseListener(this);

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
        System.out.println("Boton de carta");

        try {
            if (e.getSource().equals(property.sell)){
                System.out.println("Vendiendo propiedad");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLPROPERTY));

            } else if (e.getSource().equals(property.buy)){
                System.out.println("Comprando propiedad");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYPROPERTY));
                
            } else if (e.getSource().equals(property.mortgage)){
                System.out.println("Comprando mortgage");

            } else if (e.getSource().equals(property.buyHotel)){
                System.out.println("Comprando hotel");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOTEL));
                close();

            } else if (e.getSource().equals(property.sellHotel)){
                System.out.println("vendiendo hotel");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOTEL));
                close();

            } else if (e.getSource().equals(property.buyHouse)){
                System.out.println("Comprando casa");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOUSE));
                close();

            } else if (e.getSource().equals(property.sellHouse)){
                System.out.println("vendiendo casa");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOUSE));
                close();

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
  }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
