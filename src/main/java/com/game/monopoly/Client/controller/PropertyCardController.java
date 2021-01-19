/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;

import com.game.monopoly.Client.model.Objects.Player;
import com.game.monopoly.Client.view.*;
import com.game.monopoly.common.Comunication.*;

import javax.swing.*;
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
                buyHotel();
            } else if (e.getSource().equals(property.sellHotel)){
                sellHotel();
            } else if (e.getSource().equals(property.buyHouse)){
                buyHouse();
            } else if (e.getSource().equals(property.sellHouse)){
                sellHouse();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metodo para vender hotel
    private void sellHotel() throws IOException {
        System.out.println("vendiendo hotel");
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOTEL));
        close();
    }

    // Metodo para comprar hotel
    private void buyHotel() throws IOException {
        if (Player.getInstance().getHouses().get(property.getId()).getAmountHouse() != 4){
            JOptionPane.showMessageDialog(property, "Usted aun no tiene 4 casas...");
            return;
        }

        if (Player.getInstance().getHotel().get(property.getId()) != null && Player.getInstance().getHotel().get(property.getId()).getAmountHouse() == 1){
            JOptionPane.showMessageDialog(property, "Usted ya tiene un hotel en esta propiedad");
            return;
        }

        System.out.println("Comprando hotel");
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOTEL));
        close();
    }

    // Metodo para vender casa
    private void sellHouse() throws IOException {
        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (!Player.getInstance().getHouses().containsKey(property.getId())){
            JOptionPane.showMessageDialog(property, "No tienes casas en esta propiedad...");
            return;
        }

        System.out.println("vendiendo casa");

        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOUSE));

        close();
    }

    // Metodo para comprar casa
    private void buyHouse() throws IOException {
        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (Player.getInstance().getHouses().get(property.getId()) != null && Player.getInstance().getHouses().get(property.getId()).getAmountHouse() == 4){
            JOptionPane.showMessageDialog(property, "Ya tienes 4 casas en esta propiedad...");
            return;
        }

        System.out.println("Comprando casa");
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOUSE));
        close();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
  }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
