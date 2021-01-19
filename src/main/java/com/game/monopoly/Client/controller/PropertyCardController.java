package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;

import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.model.Objects.Player;
import com.game.monopoly.Client.model.Objects.Players;
import com.game.monopoly.Client.view.*;
import com.game.monopoly.common.Comunication.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;


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

                property.sell.setVisible(false);

            } else if (e.getSource().equals(property.buy)){
                System.out.println("Comprando propiedad");
                getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYPROPERTY));

                property.buy.setVisible(false);

            } else if (e.getSource().equals(property.mortgage)){
                System.out.println("Comprando mortgage");

                property.mortgage.setVisible(false);

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
    }

    // Metodo para comprar hotel
    private void buyHotel() throws IOException {

        Players tmp = GameListener.getInstance().getPlayers().get(0);

        if (tmp.getHouses().get(property.getId()).getAmountHouse() != 4){
            JOptionPane.showMessageDialog(property, "Usted aun no tiene 4 casas...");
            return;
        }

        if (tmp.getHotel().get(property.getId()) != null && Player.getInstance().getHotel().get(property.getId()).getAmountHouse() == 1){
            JOptionPane.showMessageDialog(property, "Usted ya tiene un hotel en esta propiedad");
            return;
        }

        System.out.println("Comprando hotel");
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOTEL));
    }

    // Metodo para vender casa
    private void sellHouse() throws IOException {
        Players tmp = GameListener.getInstance().getPlayers().get(0);

        if (!tmp.getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (!Player.getInstance().getHouses().containsKey(property.getId())){
            JOptionPane.showMessageDialog(property, "No tienes casas en esta propiedad...");
            return;
        }

        System.out.println("vendiendo casa");

        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOUSE));
    }

    // Metodo para comprar casa
    private void buyHouse() throws IOException {
        Players tmp = GameListener.getInstance().getPlayers().get(0);

        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (tmp.getHouses().get(property.getId()) != null && tmp.getHouses().get(property.getId()).getAmountHouse() == 4){
            JOptionPane.showMessageDialog(property, "Ya tienes 4 casas en esta propiedad...");
            return;
        }

        System.out.println("Comprando casa");
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.BUYHOUSE));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
  }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
}
