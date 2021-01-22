package com.game.monopoly.Client.controller;

import static com.game.monopoly.Client.controller.ServerCommunication.getServerCommunication;
import static com.game.monopoly.common.Comunication.IDMessage.PAYJAILTAXES;
import static com.game.monopoly.common.Comunication.IDMessage.USEJAILCARD;

import com.game.monopoly.Client.model.CardFactory;
import com.game.monopoly.Client.model.Objects.Houses;
import com.game.monopoly.Client.model.Objects.Player;
import com.game.monopoly.Client.model.Objects.Players;
import com.game.monopoly.Client.view.*;
import com.game.monopoly.common.Comunication.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;


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

        if (property.payTaxes != null)
            property.payTaxes.addMouseListener(this);

        if (property.freeJail != null)
            property.freeJail.addMouseListener(this);
    }

    @Override
    public void start() {
        property.setVisible(true);
    }

    @Override
    public void close() {
        property.getParent().getParent().setVisible(false);
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
                sellCard();
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
            } else if (e.getSource().equals(property.payTaxes)){
                payTaxes();
            } else if (e.getSource().equals(property.freeJail)){
                getOutJail();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sellCard() throws IOException {
        Players admin = GameListener.getInstance().getPlayers().get(0);

        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta propiedad no es tuya...");

            return;
        }

        if (admin.getHouses().containsKey(property.getId()) && admin.getHouses().get(property.getId()).getAmountHouse() != 0){
            JOptionPane.showMessageDialog(property, "Usted tiene una casa en esta propiedad...");

            return;
        }

        if (admin.getHotel().containsKey(property.getId())  && admin.getHotel().get(property.getId()).getAmountHouse() != 0){
            JOptionPane.showMessageDialog(property, "Usted tiene un hotel en esta propiedad...");

            return;
        }

        if (Player.getInstance().hasBoughtSet(property.getColor()) && Players.hasColorInSet(property.getColor())){
            JOptionPane.showMessageDialog(property, "Usted tiene casas en otras propiedades del mismo tipo de color...");

            return;
        }

        System.out.println("Vendiendo propiedad");

        Player.getInstance().getCards().remove(property.getId());
        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLPROPERTY));

        property.sell.setVisible(false);

        close();
    }

    // Metodo si tiene la carta para salir gratis de la carcel
    private void getOutJail() throws IOException {
        if (!Player.getInstance().isInJail()){
            JOptionPane.showMessageDialog(property, "Usted no esta en la carcel...");

            return;
        }

        if (!Player.getInstance().isJailFree()){
            JOptionPane.showMessageDialog(property, "Usted no tiene la carta para salir gratis de la carcel...");

            return;
        }

        // Enviar al server salir gratis de la carcel
        Player.getInstance().removeJailFree();
        Player.getInstance().setInJail(false);
        getServerCommunication().sendMessage(new Message(USEJAILCARD));

        JOptionPane.showMessageDialog(property, "Has salido de la carcel...");
    }

    // Metodo para pagar y salir de la carcel
    private void payTaxes() throws IOException{
        if (!Player.getInstance().isInJail()){
            JOptionPane.showMessageDialog(property, "Usted no esta en la carcel...");

            return;
        }

        // Enviar al server la consulta para pagar los $50
        Player.getInstance().setInJail(false);
        getServerCommunication().sendMessage(new Message(PAYJAILTAXES));
        JOptionPane.showMessageDialog(property, "Has salido de la carcel...");
    }

    // Metodo para vender hotel
    private void sellHotel() throws IOException {
        Players tmp = GameListener.getInstance().getPlayers().get(0);

        System.out.println("vendiendo hotel");

        boolean esFerrocarril = property.getId() % 5 == 0;

        if (esFerrocarril){
            JOptionPane.showMessageDialog(property, "En esta propiedad no se pueden vender casas...");
            return;
        }

        var hotel = tmp.getHotel();

        System.out.println("Contiene la key: " + hotel.containsKey(property.getId()));

        if (hotel.containsKey(property.getId())) {
            System.out.println("Cantidad de hotel: " + hotel.get(property.getId()).getAmountHouse());

            return;
        }

        if (!hotel.containsKey(property.getId()) || hotel.get(property.getId()).getAmountHouse() == 0){
            JOptionPane.showMessageDialog(property, "Usted no tiene hoteles en esta propiedad...");

            return;
        }

        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOTEL));
    }

    // Metodo para comprar hotel
    private void buyHotel() throws IOException {
        Players tmp = GameListener.getInstance().getPlayers().get(0);

        boolean esFerrocarril = property.getId() % 5 == 0;

        System.out.println(1);
        if (esFerrocarril){
            JOptionPane.showMessageDialog(property, "En esta propiedad no se pueden vender casas...");

            return;
        }

        System.out.println(2);
        if (((PropertyCard) CardFactory.getCard(property.getId())).getHouseAmount() != 4){
            JOptionPane.showMessageDialog(property, "Usted aun no tiene 4 casas...");

            return;
        }

        System.out.println(3);
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

        boolean esFerrocarril = property.getId() % 5 == 0;

        if (esFerrocarril){
            JOptionPane.showMessageDialog(property, "En esta propiedad no se puede comprar casas...");
            return;
        }

        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (!tmp.getHouses().containsKey(property.getId()) || tmp.getHouses().get(property.getId()).getAmountHouse() == 0){
            JOptionPane.showMessageDialog(property, "No tienes casas en esta propiedad...");
            return;
        }

        System.out.println("vendiendo casa");

        getServerCommunication().sendMessage(new Message(property.getId(), IDMessage.SELLHOUSE));
    }

    // Metodo para comprar casa
    private void buyHouse() throws IOException {
        Players tmp = GameListener.getInstance().getPlayers().get(0);

        boolean esFerrocarril = property.getId() % 5 == 0;

        if (esFerrocarril){
            JOptionPane.showMessageDialog(property, "En esta propiedad no se pueden vender casas...");
            return;
        }

        if (!Player.getInstance().getCards().contains(property.getId())){
            JOptionPane.showMessageDialog(property, "Esta carta aun no es tuya...");
            return;
        }

        if (!Player.getInstance().hasBoughtSet(property.getColor())){
            JOptionPane.showMessageDialog(property, "Tienes que comprar todo el set de color para comprar casas...");
            return;
        }

        if (tmp.getHouses().get(property.getId()) != null && tmp.getHouses().get(property.getId()).getAmountHouse() == 4){
            JOptionPane.showMessageDialog(property, "Ya tienes 4 casas en esta propiedad...");
            return;
        }

        if (tmp.getHotel().get(property.getId()) != null && tmp.getHotel().get(property.getId()).getAmountHouse() != 0){
            JOptionPane.showMessageDialog(property, "Usted tiene un hotel en esta propiedad...");
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
